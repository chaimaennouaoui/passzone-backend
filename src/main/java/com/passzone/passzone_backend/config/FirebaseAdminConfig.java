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

    // ✅ On récupère le JSON depuis variable d'environnement
    @Value("${app.firebaseServiceAccountJson}")
    private String firebaseJson;

    @PostConstruct
    public void initFirebase() {
        try {
            if (FirebaseApp.getApps().isEmpty()) {

                if (firebaseJson == null || firebaseJson.isBlank()) {
                    System.out.println("❌ FIREBASE_SERVICE_ACCOUNT vide !");
                    return;
                }

                ByteArrayInputStream serviceAccount =
                        new ByteArrayInputStream(firebaseJson.getBytes(StandardCharsets.UTF_8));

                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(options);

                System.out.println("✅ Firebase Admin initialisé correctement (via Secret) !");
            }
        } catch (Exception e) {
            System.out.println("❌ Erreur Firebase init : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
