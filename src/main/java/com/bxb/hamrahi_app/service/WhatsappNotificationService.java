package com.bxb.hamrahi_app.service;

/** * Service interface for sending OTP messages via WhatsApp.
 */
public interface WhatsappNotificationService {
/** * Sends an OTP message to the specified phone number via WhatsApp.
 * This method is intended to be implemented by classes that will handle
 * the logic for sending OTP messages using a service like Twilio.
 * @param phoneNumber the recipient's phone number in E.164 format
 *                    (without the "whatsapp:" prefix).
 * @param otp the One-Time Password to be sent in the message.
 * */
    void sendOtp(String phoneNumber, String otp);
}
