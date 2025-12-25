package com.passzone.passzone_backend.controller;

import com.passzone.passzone_backend.dto.*;
import com.passzone.passzone_backend.model.TimeSlot;
import com.passzone.passzone_backend.repo.FanZoneRepo;
import com.passzone.passzone_backend.repo.TimeSlotRepo;
import com.passzone.passzone_backend.security.FirebaseUserPrincipal;
import com.passzone.passzone_backend.service.BookingService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class FanController {

    private final FanZoneRepo fanZoneRepo;
    private final TimeSlotRepo slotRepo;
    private final BookingService bookingService;

    public FanController(FanZoneRepo fanZoneRepo, TimeSlotRepo slotRepo, BookingService bookingService) {
        this.fanZoneRepo = fanZoneRepo;
        this.slotRepo = slotRepo;
        this.bookingService = bookingService;
    }

    // ✅ GET /api/fanzones (PUBLIC)
    @GetMapping("/fanzones")
    public List<FanZoneDto> listZones() {
        return fanZoneRepo.findAll().stream()
                .map(z -> new FanZoneDto(
                        z.getId(),
                        z.getName(),
                        z.getCity(),
                        z.getCapacity(),
                        z.getRules()
                ))
                .collect(Collectors.toList());
    }

    // ✅ GET /api/fanzones/{id}/slots (PUBLIC)
    @GetMapping("/fanzones/{id}/slots")
    public List<SlotDto> listSlots(@PathVariable Long id) {

        List<TimeSlot> slots = slotRepo.findByFanZone_IdOrderByStartTimeAsc(id);

        return slots.stream()
                .map(s -> new SlotDto(
                        s.getId(),
                        s.getFanZone().getId(),
                        s.getMatchName(),
                        s.getStartTime().toString(),
                        s.getCapacity(),
                        s.getBooked()
                ))
                .collect(Collectors.toList());
    }

    // ✅ POST /api/reservations (PROTECTED TOKEN)
    @PostMapping("/reservations")
    public TicketResponse reserve(Authentication authentication,
                                  @RequestBody CreateReservationReq req) {

        FirebaseUserPrincipal user = (FirebaseUserPrincipal) authentication.getPrincipal();
        return bookingService.reserve(user, req.getSlotId(), req.getQty());
    }

    // ✅ GET /api/my/reservations (PROTECTED TOKEN)
    @GetMapping("/my/reservations")
    public List<MyReservationDto> myReservations(Authentication authentication) {
        FirebaseUserPrincipal user = (FirebaseUserPrincipal) authentication.getPrincipal();
        return bookingService.myReservations(user);
    }

    // ✅ POST /api/reservations/{id}/cancel (PROTECTED TOKEN)
    @PostMapping("/reservations/{id}/cancel")
    public void cancel(Authentication authentication, @PathVariable Long id) {
        FirebaseUserPrincipal user = (FirebaseUserPrincipal) authentication.getPrincipal();
        bookingService.cancel(user, id);
    }
}
