package com.passzone.passzone_backend.repo;

import com.passzone.passzone_backend.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepo extends JpaRepository<Reservation, Long> {
  List<Reservation> findByUserUidOrderByCreatedAtDesc(String uid);
}
