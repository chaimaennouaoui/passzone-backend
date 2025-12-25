package com.passzone.passzone_backend.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
  public void sendBookingEmail(String to, String ticketCode) {
    System.out.println("✅ EMAIL SENT (DEMO)");
    System.out.println("To: " + to);
    System.out.println("TicketCode: " + ticketCode);
  }
}
