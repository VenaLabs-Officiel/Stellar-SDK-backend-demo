package com.venalabs.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "venalabs")
public class VenalabsProperties {

    /**
     * Venalabs API base URL
     */
    private String apiUrl = "https://api.venalabs.com";

    /**
     * API Key from Saas dashboard
     */
    private String apiKey;

    /**
     * API Secret from Saas dashboard
     */
    private String apiSecret;
}
