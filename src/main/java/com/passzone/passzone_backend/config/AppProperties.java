package com.passzone.passzone_backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {
  private List<String> adminEmails;
  private int cancelCutoffMinutes;
  private String firebaseServiceAccountPath;

  public List<String> getAdminEmails() { return adminEmails; }
  public void setAdminEmails(List<String> adminEmails) { this.adminEmails = adminEmails; }

  public int getCancelCutoffMinutes() { return cancelCutoffMinutes; }
  public void setCancelCutoffMinutes(int cancelCutoffMinutes) { this.cancelCutoffMinutes = cancelCutoffMinutes; }

  public String getFirebaseServiceAccountPath() { return firebaseServiceAccountPath; }
  public void setFirebaseServiceAccountPath(String firebaseServiceAccountPath) { this.firebaseServiceAccountPath = firebaseServiceAccountPath; }
}
