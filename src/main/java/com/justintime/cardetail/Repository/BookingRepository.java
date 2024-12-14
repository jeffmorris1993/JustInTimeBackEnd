package com.justintime.cardetail.Repository;

import com.justintime.cardetail.Model.Entity.BookingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<BookingEntity, UUID> {
    @Query("SELECT b FROM BookingEntity b WHERE " +
            "(:firstName IS NULL OR LOWER(b.customer.firstName) LIKE LOWER(CONCAT('%', :firstName, '%'))) AND " +
            "(:lastName IS NULL OR LOWER(b.customer.lastName) LIKE LOWER(CONCAT('%', :lastName, '%')))")
    Page<BookingEntity> findByCustomerFirstNameOrLastNameContainingIgnoreCase(@Param("firstName") String firstName,
                                                                              @Param("lastName") String lastName,
                                                                              Pageable pageable);
    Page<BookingEntity> findByDateOfService(Date dateOfService, Pageable pageable);
    Page<BookingEntity> findByBookingNumber(UUID bookingNumber, Pageable pageable);
}
