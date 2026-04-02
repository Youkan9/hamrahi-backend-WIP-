package com.bxb.hamrahi_app.kafkaEvent;

import lombok.*;

/** * Event class representing a WhatsApp message to be sent.
 * This class is used for Kafka messaging to encapsulate the details
 * of the WhatsApp message.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WhatsappEvent {
    /** The phone number to which the WhatsApp message will be sent. */
    private String phoneNumber;
    /** The OTP (One-Time Password) to be included in the WhatsApp message. */
    private String otp;
}
