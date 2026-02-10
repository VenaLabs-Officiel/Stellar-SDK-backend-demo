package com.venalabs.demo.service;

import com.venalabs.demo.config.VenalabsProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class VenalabsTokenService {

    private final VenalabsProperties venalabsProperties;
    private final RestTemplate restTemplate;

    /**
     * Get an access token from Venalabs API for the given user.
     *
     * @param userId The user ID to generate token for
     * @return The access token
     */
    public String getAccessToken(String userId) {
        String url = venalabsProperties.getApiUrl() + "/api/v1/sdk/auth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Api-Key", venalabsProperties.getApiKey());
        headers.set("X-Api-Secret", venalabsProperties.getApiSecret());

        Map<String, String> body = Map.of(
                "userId", userId
        );

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        log.info("Requesting Venalabs token for user: {}", userId);

        @SuppressWarnings("unchecked")
        Map<String, Object> response = restTemplate.postForObject(url, request, Map.class);

        if (response == null || !response.containsKey("token")) {
            throw new RuntimeException("Failed to get access token from Venalabs API");
        }

        return (String) response.get("token");
    }
}
