package com.passzone.passzone_backend.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.InputStream;

@Configuration
public class FirebaseAdminConfig {

    // ✅ chemin vers le fichier secret (fourni par Koyeb)
    @Value("${app.firebaseServiceAccountPath:/app/firebase-service-account.json}")
    private String firebaseServiceAccountPath;

    @PostConstruct
    public void initFirebase() {
        try {
            if (FirebaseApp.getApps().isEmpty()) {

                InputStream serviceAccount = new FileInputStream(firebaseServiceAccountPath);

                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(options);

                System.out.println("✅ Firebase Admin initialisé correctement !");
            }
        } catch (Exception e) {
            System.out.println("❌ Erreur Firebase init : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
