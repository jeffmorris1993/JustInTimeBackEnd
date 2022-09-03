package com.justintime.cardetail.Repository;

import com.justintime.cardetail.Model.Entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookingRepository extends JpaRepository<BookingEntity, UUID> {
}
