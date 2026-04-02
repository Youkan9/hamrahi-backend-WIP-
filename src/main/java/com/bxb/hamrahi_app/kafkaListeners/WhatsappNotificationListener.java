package com.bxb.hamrahi_app.kafkaListeners;

import com.bxb.hamrahi_app.kafkaEvent.WhatsappEvent;
import com.bxb.hamrahi_app.service.WhatsappNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Component;

import java.time.Duration;

/** * Kafka listener for processing WhatsApp OTP events.
 * This class listens to the "whatsapp-otp-topic" and handles
 * incoming WhatsAppEvent messages.
 */
@Component
@Log4j2
@RequiredArgsConstructor
public class WhatsappNotificationListener {
    /** Service to handle WhatsApp-related operations. */
    private final WhatsappNotificationService whatsappService;

    /** Listens to the "whatsapp-otp-topic" Kafka topic and processes
     * incoming WhatsAppEvent messages.
     * @param whatsappEvent the event containing OTP details to be
     *                     sent via WhatsApp
     */
    @RetryableTopic(attempts = "3")
    @KafkaListener(topics = "whatsapp-otp-topic", groupId = "otp-group")
    public void sendOtp(final WhatsappEvent whatsappEvent){
        try {
            whatsappService.sendOtp(whatsappEvent.getPhoneNumber(),
                    whatsappEvent.getOtp());
            log.info("OTP sent successfully and stored for {}",
                    whatsappEvent.getPhoneNumber());
        } catch (Exception e) {
            log.error("Failed to send OTP for {} : {}",
                    whatsappEvent.getPhoneNumber(), e.getMessage());
            throw e;
        }
    }
}
