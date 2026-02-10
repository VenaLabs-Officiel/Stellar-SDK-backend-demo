package com.venalabs.demo.controller;

import com.venalabs.demo.service.VenalabsTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class VenalabsTokenController {

    private final VenalabsTokenService venalabsTokenService;

    /**
     * Get Venalabs access token for SDK authentication.
     * In a real application, you would first authenticate the user via session/JWT
     * and use their actual userId.
     */
    @GetMapping("/venalabs-token")
    public ResponseEntity<Map<String, String>> getVenalabsToken() {
        // In a real app, get the authenticated user's ID from session/JWT
        // For demo purposes, we use a hardcoded test user ID
        String userId = "demo-user-001";

        String accessToken = venalabsTokenService.getAccessToken(userId);

        return ResponseEntity.ok(Map.of("accessToken", accessToken));
    }
}
