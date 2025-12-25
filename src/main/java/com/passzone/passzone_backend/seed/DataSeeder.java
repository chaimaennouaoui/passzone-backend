package com.passzone.passzone_backend.seed;

import com.passzone.passzone_backend.model.FanZone;
import com.passzone.passzone_backend.model.TimeSlot;
import com.passzone.passzone_backend.repo.FanZoneRepo;
import com.passzone.passzone_backend.repo.TimeSlotRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class DataSeeder implements CommandLineRunner {

  private final FanZoneRepo fanZoneRepo;
  private final TimeSlotRepo slotRepo;

  public DataSeeder(FanZoneRepo fanZoneRepo, TimeSlotRepo slotRepo) {
    this.fanZoneRepo = fanZoneRepo;
    this.slotRepo = slotRepo;
  }

  @Override
  public void run(String... args) {
    if (fanZoneRepo.count() > 0) return;

    FanZone z1 = new FanZone();
    z1.setName("Fan Zone Rabat");
    z1.setCity("Rabat");
    z1.setCapacity(500);
    z1.setRules("Pas d'objets dangereux. Respect des consignes.");
    fanZoneRepo.save(z1);

    FanZone z2 = new FanZone();
    z2.setName("Fan Zone Casablanca");
    z2.setCity("Casablanca");
    z2.setCapacity(800);
    z2.setRules("Interdiction fumigènes. Respect file d'attente.");
    fanZoneRepo.save(z2);

    TimeSlot s1 = new TimeSlot();
    s1.setFanZone(z1);
    s1.setMatchName("Match A");
    s1.setStartTime(Instant.now().plusSeconds(86400));
    s1.setCapacity(500);
    s1.setBooked(0);
    slotRepo.save(s1);

    TimeSlot s2 = new TimeSlot();
    s2.setFanZone(z1);
    s2.setMatchName("Match B");
    s2.setStartTime(Instant.now().plusSeconds(2 * 86400));
    s2.setCapacity(500);
    s2.setBooked(0);
    slotRepo.save(s2);

    TimeSlot s3 = new TimeSlot();
    s3.setFanZone(z2);
    s3.setMatchName("Match A");
    s3.setStartTime(Instant.now().plusSeconds(86400));
    s3.setCapacity(800);
    s3.setBooked(0);
    slotRepo.save(s3);

    System.out.println("✅ Seed data inserted (zones + slots)");
  }
}
