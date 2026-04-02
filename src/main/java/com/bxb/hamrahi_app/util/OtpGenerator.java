package com.bxb.hamrahi_app.util;

import org.springframework.stereotype.Component;

import java.util.Random;

/** Utility class for generating One-Time Passwords (OTPs). */
@Component
public class OtpGenerator {

    /** Generates a random 6-digit OTP (One-Time Password). */
    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
}
