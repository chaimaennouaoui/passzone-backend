package com.passzone.passzone_backend.repo;

import com.passzone.passzone_backend.model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeSlotRepo extends JpaRepository<TimeSlot, Long> {
  List<TimeSlot> findByFanZone_IdOrderByStartTimeAsc(Long id);
  void deleteByFanZone_Id(Long fanZoneId);
}
