package com.passzone.passzone_backend.controller;

import com.passzone.passzone_backend.dto.*;
import com.passzone.passzone_backend.model.FanZone;
import com.passzone.passzone_backend.model.TimeSlot;
import com.passzone.passzone_backend.repo.FanZoneRepo;
import com.passzone.passzone_backend.repo.TimeSlotRepo;
import com.passzone.passzone_backend.service.BookingService;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final FanZoneRepo fanZoneRepo;
    private final TimeSlotRepo slotRepo;
    private final BookingService bookingService;

    public AdminController(FanZoneRepo fanZoneRepo, TimeSlotRepo slotRepo, BookingService bookingService) {
        this.fanZoneRepo = fanZoneRepo;
        this.slotRepo = slotRepo;
        this.bookingService = bookingService;
    }

    // ✅ LIST FANZONES
    @GetMapping("/fanzones")
    public List<FanZoneDto> adminZones() {
        return fanZoneRepo.findAll().stream()
                .map(z -> new FanZoneDto(z.getId(), z.getName(), z.getCity(), z.getCapacity(), z.getRules()))
                .toList();
    }

    // ✅ CREATE FANZONE
    @PostMapping("/fanzones")
    public FanZoneDto createZone(@RequestBody AdminCreateZoneReq req) {
        FanZone z = new FanZone();
        z.setName(req.getName());
        z.setCity(req.getCity());
        z.setCapacity(req.getCapacity());
        z.setRules(req.getRules());
        z = fanZoneRepo.save(z);

        return new FanZoneDto(z.getId(), z.getName(), z.getCity(), z.getCapacity(), z.getRules());
    }

    // ✅ DELETE FANZONE
    @DeleteMapping("/fanzones/{id}")
    public void deleteFanZone(@PathVariable Long id) {
        // Supprimer d'abord tous les slots liés
        slotRepo.deleteByFanZone_Id(id);

        // Ensuite supprimer la fan zone
        fanZoneRepo.deleteById(id);
    }

    // ✅ LIST SLOTS
    @GetMapping("/slots")
    public List<SlotDto> listSlots() {
        return slotRepo.findAll().stream()
                .map(s -> new SlotDto(
                        s.getId(),
                        s.getFanZone().getId(),
                        s.getMatchName(),
                        s.getStartTime().toString(),
                        s.getCapacity(),
                        s.getBooked()
                ))
                .toList();
    }

    // ✅ CREATE SLOT
    @PostMapping("/fanzones/{id}/slots")
    public SlotDto createSlot(@PathVariable Long id, @RequestBody AdminCreateSlotReq req) {

        FanZone z = fanZoneRepo.findById(id).orElseThrow();

        TimeSlot s = new TimeSlot();
        s.setFanZone(z);
        s.setMatchName(req.getMatchName());
        s.setStartTime(Instant.parse(req.getStartTime()));
        s.setCapacity(req.getCapacity());
        s.setBooked(0);

        s = slotRepo.save(s);

        return new SlotDto(
                s.getId(),
                z.getId(),
                s.getMatchName(),
                s.getStartTime().toString(),
                s.getCapacity(),
                s.getBooked()
        );
    }

    // ✅ DELETE SLOT
    @DeleteMapping("/slots/{slotId}")
    public void deleteSlot(@PathVariable Long slotId) {
        slotRepo.deleteById(slotId);
    }

    // ✅ CHECKIN
    @PostMapping("/checkin/{ticketCode}")
    public CheckinResp checkin(@PathVariable String ticketCode) {
        return bookingService.adminCheckin(ticketCode);
    }
}
