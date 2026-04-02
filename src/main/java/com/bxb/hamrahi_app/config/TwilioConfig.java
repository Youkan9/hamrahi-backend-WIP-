package com.bxb.hamrahi_app.config;

import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Twilio integration.
 * Loads Twilio credentials and initializes Twilio client.
 */
@Configuration
@ConfigurationProperties(prefix = "twilio")
@Getter
@Setter
public class TwilioConfig {

    /** Twilio Account SID */
    private String accountSid;

    /** Twilio Auth Token */
    private String authToken;

    /** Twilio WhatsApp number */
    private String whatsappNumber;

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }
}