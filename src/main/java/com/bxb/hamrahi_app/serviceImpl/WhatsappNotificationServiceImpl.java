package com.bxb.hamrahi_app.serviceImpl;

import com.bxb.hamrahi_app.service.WhatsappNotificationService;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/** * Service implementation for sending OTP messages via WhatsApp using Twilio.
 * This class implements the WhatsappService interface and provides the logic
 * to send OTP messages.
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class WhatsappNotificationServiceImpl implements WhatsappNotificationService {

    /** The WhatsApp number from which the OTP messages will be sent. */
    @Value("${twilio.whatsapp.from}")
    private String fromNumber;

    /** Sends an OTP message to the specified phone number via WhatsApp.
     * This method constructs the message body with the provided OTP and uses
     * the Twilio API to send the message.
     *
     * @param phoneNumber the recipient's phone number in E.164 format
     *                    (without the "whatsapp:" prefix).
     * @param otp         the One-Time Password to be sent in the message.
     */
    @Override
    public void sendOtp(String phoneNumber, String otp) {

        try {
            String messageBody = "Your HamRahi OTP is: " + otp;

            Message.creator(
                    new PhoneNumber("whatsapp:" + phoneNumber),
                    new PhoneNumber(fromNumber),
                    messageBody
            ).create();
        } catch (Exception e){
            log.error(
          "Failed to send OTP via WhatsApp to phone number: {}. Error: {}",
                    phoneNumber, e.getMessage());
            throw new RuntimeException(
                    "Failed to send OTP via WhatsApp. Please try again later.");
        }
    }
}
