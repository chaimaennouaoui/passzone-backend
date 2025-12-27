package com.passzone.passzone_backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailJsService {

    @Value("${emailjs.serviceId}")
    private String serviceId;

    @Value("${emailjs.templateId}")
    private String templateId;

    @Value("${emailjs.publicKey}")
    private String publicKey;

    @Value("${emailjs.privateKey}")
    private String privateKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendReservationEmail(String toEmail,
                                     String ticketCode,
                                     String zoneName,
                                     String matchName,
                                     String startTime) {

        String url = "https://api.emailjs.com/api/v1.0/email/send";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // ✅ Template variables matching EmailJS template
        Map<String, Object> templateParams = new HashMap<>();
        templateParams.put("to_email", toEmail);
        templateParams.put("ticket_code", ticketCode);
        templateParams.put("zone_name", zoneName);
        templateParams.put("match_name", matchName);
        templateParams.put("start_time", startTime);

        Map<String, Object> body = new HashMap<>();
        body.put("service_id", serviceId);
        body.put("template_id", templateId);
        body.put("user_id", publicKey);
        body.put("accessToken", privateKey);
        body.put("template_params", templateParams);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("EmailJS error: " + response.getBody());
        }

        System.out.println("✅ Email envoyé à " + toEmail);
    }
}
