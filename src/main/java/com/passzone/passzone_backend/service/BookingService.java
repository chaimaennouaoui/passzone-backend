package com.passzone.passzone_backend.service;

import com.passzone.passzone_backend.config.AppProperties;
import com.passzone.passzone_backend.dto.CheckinResp;
import com.passzone.passzone_backend.dto.MyReservationDto;
import com.passzone.passzone_backend.dto.TicketResponse;
import com.passzone.passzone_backend.model.Reservation;
import com.passzone.passzone_backend.model.Ticket;
import com.passzone.passzone_backend.model.TimeSlot;
import com.passzone.passzone_backend.repo.ReservationRepo;
import com.passzone.passzone_backend.repo.TicketRepo;
import com.passzone.passzone_backend.repo.TimeSlotRepo;
import com.passzone.passzone_backend.security.FirebaseUserPrincipal;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final TimeSlotRepo slotRepo;
    private final ReservationRepo reservationRepo;
    private final TicketRepo ticketRepo;
    private final QrService qrService;
    private final EmailService emailService;
    private final AppProperties props;

    public BookingService(TimeSlotRepo slotRepo,
                          ReservationRepo reservationRepo,
                          TicketRepo ticketRepo,
                          QrService qrService,
                          EmailService emailService,
                          AppProperties props) {
        this.slotRepo = slotRepo;
        this.reservationRepo = reservationRepo;
        this.ticketRepo = ticketRepo;
        this.qrService = qrService;
        this.emailService = emailService;
        this.props = props;
    }

    // ✅ RESERVE
    @Transactional
    public TicketResponse reserve(FirebaseUserPrincipal user, Long slotId, int qty) {

        TimeSlot slot = slotRepo.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Créneau introuvable"));

        int remaining = slot.getCapacity() - slot.getBooked();
        if (qty <= 0) throw new RuntimeException("Quantité invalide");
        if (qty > remaining) throw new RuntimeException("Capacité insuffisante");

        // update booked
        slot.setBooked(slot.getBooked() + qty);
        slotRepo.save(slot);

        // create reservation
        Reservation reservation = new Reservation();
        reservation.setUserUid(user.uid());
        reservation.setUserEmail(user.email());
        reservation.setSlot(slot);
        reservation.setQty(qty);
        reservation.setStatus(Reservation.Status.CONFIRMED);
        reservation.setCreatedAt(Instant.now());
        reservationRepo.save(reservation);

        // ticket code
        String ticketCode = "PZ-" + UUID.randomUUID()
                .toString().replace("-", "")
                .substring(0, 8)
                .toUpperCase();

        Ticket ticket = new Ticket();
        ticket.setReservation(reservation);
        ticket.setTicketCode(ticketCode);

        // qr base64
        String qrBase64 = qrService.generatePngBase64(ticketCode);
        ticket.setBarcodePngBase64(qrBase64);

        ticketRepo.save(ticket);

        // demo email console
        if (user.email() != null) {
            emailService.sendBookingEmail(user.email(), ticketCode);
        }

        TicketResponse resp = new TicketResponse();
        resp.setReservationId(reservation.getId());
        resp.setTicketCode(ticketCode);
        resp.setQrPngBase64(qrBase64);
        return resp;
    }

    // ✅ MY RESERVATIONS
    public List<MyReservationDto> myReservations(FirebaseUserPrincipal user) {

        List<Reservation> reservations = reservationRepo.findByUserUidOrderByCreatedAtDesc(user.uid());

        return reservations.stream().map(r -> {

            Ticket ticket = ticketRepo.findByReservation_Id(r.getId()).orElse(null);

            MyReservationDto dto = new MyReservationDto();
            dto.setId(r.getId());
            dto.setFanZoneName(r.getSlot().getFanZone().getName());
            dto.setCity(r.getSlot().getFanZone().getCity());
            dto.setMatchName(r.getSlot().getMatchName());
            dto.setStartTime(r.getSlot().getStartTime().toString());
            dto.setQty(r.getQty());
            dto.setStatus(r.getStatus().name());
            dto.setTicketCode(ticket != null ? ticket.getTicketCode() : "—");

            return dto;

        }).collect(Collectors.toList());
    }

    // ✅ CANCEL (cutoff)
    @Transactional
    public void cancel(FirebaseUserPrincipal user, Long reservationId) {

        Reservation r = reservationRepo.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Réservation introuvable"));

        if (!r.getUserUid().equals(user.uid())) {
            throw new RuntimeException("Interdit");
        }

        if (r.getStatus() != Reservation.Status.CONFIRMED) {
            throw new RuntimeException("Déjà annulée");
        }

        // ✅ cutoff rule
        Instant start = r.getSlot().getStartTime();
        long cutoffMinutes = props.getCancelCutoffMinutes();

        if (Instant.now().isAfter(start.minus(Duration.ofMinutes(cutoffMinutes)))) {
            throw new RuntimeException("Annulation impossible (" + cutoffMinutes + " min avant match)");
        }

        r.setStatus(Reservation.Status.CANCELED);
        reservationRepo.save(r);

        // restore slot capacity
        TimeSlot slot = r.getSlot();
        slot.setBooked(Math.max(0, slot.getBooked() - r.getQty()));
        slotRepo.save(slot);
    }

    // ✅ ADMIN CHECKIN (returns CheckinResp)
    @Transactional
    public CheckinResp adminCheckin(String ticketCode) {

        Ticket ticket = ticketRepo.findByTicketCode(ticketCode).orElse(null);

        if (ticket == null) {
            return new CheckinResp("NOT_FOUND", "Ticket introuvable ❌");
        }

        Reservation r = ticket.getReservation();

        if (r.getStatus() == Reservation.Status.CANCELED) {
            return new CheckinResp("CANCELED", "Réservation annulée ❌");
        }

        if (r.getStatus() == Reservation.Status.CHECKED_IN) {
            return new CheckinResp("ALREADY_CHECKED_IN", "Déjà check-in ✅");
        }

        r.setStatus(Reservation.Status.CHECKED_IN);
        reservationRepo.save(r);

        return new CheckinResp("CHECKED_IN_OK", "Check-in validé ✅");
    }
}
