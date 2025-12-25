package com.passzone.passzone_backend.repo;

import com.passzone.passzone_backend.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketRepo extends JpaRepository<Ticket, Long> {
  Optional<Ticket> findByReservation_Id(Long reservationId);
  Optional<Ticket> findByTicketCode(String ticketCode);
}
