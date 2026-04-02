package com.bxb.hamrahi_app.factories.authFactory;

import com.bxb.hamrahi_app.exception.InvalidAuthenticationRequestException;
import com.bxb.hamrahi_app.kafkaEvent.WhatsappEvent;
import com.bxb.hamrahi_app.request.AuthenticationRequest;
import com.bxb.hamrahi_app.response.AuthenticationResponse;
import com.bxb.hamrahi_app.util.AuthenticationRequestType;
import com.bxb.hamrahi_app.util.OtpGenerator;
import com.bxb.hamrahi_app.validations.AuthValidations;
import com.bxb.hamrahi_app.validations.UserValidations;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@Log4j2
@RequiredArgsConstructor
public class SendOtpService implements AuthenticationOperation {

    private static final String OTP_TOPIC = "whatsapp-otp-topic";

    private static final String OTP_RATE_KEY = "otp:rate:";

    private static final int OTP_LIMIT = 3;

    private static final int WINDOW_MINUTES = 10;

    private static final String OTP_KEY = "otp:";

    private static final int EXPIRY_TIME = 5;

    /**
     * Utility for generating OTPs.
     */
    private final OtpGenerator otpGenerator;

    /**
     * Kafka template for sending authentication-related events.
     */
    private final KafkaTemplate<String, WhatsappEvent> whatsappEventTemplate;

    /**
     * Validation component for user-related operations.
     */
    private final UserValidations userValidations;


    private final AuthValidations authValidations;

    private final RedisTemplate<String, String> redisTemplate;



    @Override
    public AuthenticationRequestType getAuthenticationRequestType() {
        return AuthenticationRequestType.SEND_OTP;
    }

    @Override
    public AuthenticationResponse perform(AuthenticationRequest request) {
        try {
            log.info("Received request to send OTP to phone number: {}",
                    request.getPhoneNumber());

            String phoneNumber = userValidations
                    .validateAndNormalizePhoneNumber(request.getPhoneNumber());
            log.info("Validated and normalized phone number: {}",
                    phoneNumber);

            authValidations.validateOtpRequestLimit(
                    phoneNumber,
                    OTP_LIMIT,
                    WINDOW_MINUTES,
                    OTP_RATE_KEY
            );
            log.info("Otp limit validation passed successfully.");

            String otp = otpGenerator.generateOtp();
            log.info(
                    "Generated OTP: {} for user: {}", otp,
                    phoneNumber);
            redisTemplate.opsForValue().set(
                    getOtpRedisKey(phoneNumber),
                    otp,
                    Duration.ofMinutes(EXPIRY_TIME)
            );


            whatsappEventTemplate.send(OTP_TOPIC,
                    new WhatsappEvent(phoneNumber, otp));

            return AuthenticationResponse.builder()
                    .message("OTP sent successfully!")
                    .build();
        } catch (InvalidAuthenticationRequestException e) {

            log.warn(
     "Invalid authentication request for phone number: {}. Error: {}",
                    request.getPhoneNumber(), e.getMessage());
            throw e;

        } catch (Exception e) {

            log.error("Unexpected error while sending OTP to {}",
                    request.getPhoneNumber(), e);
            throw e;
        }
    }

    private String getOtpRedisKey(final String phoneNumber){
        return OTP_KEY + phoneNumber;
    }
}
