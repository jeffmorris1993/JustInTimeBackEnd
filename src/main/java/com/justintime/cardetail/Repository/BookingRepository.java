package com.justintime.cardetail.Repository;

import com.justintime.cardetail.Model.Entity.BookingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<BookingEntity, UUID> {
    @Query("SELECT b FROM BookingEntity b WHERE " +
            "(:firstName IS NULL OR LOWER(b.customer.firstName) LIKE LOWER(CONCAT('%', :firstName, '%'))) AND " +
            "(:lastName IS NULL OR LOWER(b.customer.lastName) LIKE LOWER(CONCAT('%', :lastName, '%')))")
    Page<BookingEntity> findByCustomerFirstNameOrLastNameContainingIgnoreCase(@Param("firstName") String firstName,
                                                                              @Param("lastName") String lastName,
                                                                              Pageable pageable);

    @Query("SELECT b FROM BookingEntity b WHERE b.dateOfService BETWEEN :startDate AND :endDate")
    Page<BookingEntity> findByDateOfServiceBetween(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate,
                                            Pageable pageable);
    Page<BookingEntity> findByBookingNumber(UUID bookingNumber, Pageable pageable);
}
