package com.passzone.passzone_backend.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseAdminConfig {

    @Value("${app.firebaseServiceAccountJson}")
    private String firebaseServiceAccountJson;

    @PostConstruct
    public void initFirebase() {
        try {
            if (FirebaseApp.getApps().isEmpty()) {

                ByteArrayInputStream serviceAccount =
                        new ByteArrayInputStream(firebaseServiceAccountJson.getBytes(StandardCharsets.UTF_8));

                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(options);
                System.out.println("✅ Firebase Admin initialisé correctement !");
            }
        } catch (Exception e) {
            System.out.println("❌ Erreur Firebase init : " + e.getMessage());
        }
    }
}
