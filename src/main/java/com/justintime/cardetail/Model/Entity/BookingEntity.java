package com.justintime.cardetail.Model.Entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "booking")
public class BookingEntity {

    @Id
    private long bookingNumber;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private CustomerEntity customer;

    @ManyToOne
    @JoinColumn(name = "vehicleId")
    private VehicleEntity vehicle;

    private Timestamp dateOfService;

    private boolean isSubmitted;

    private BigDecimal baseCost;

    private BigDecimal totalCost;

    private BigDecimal tip;

    private String notes;

    private String serviceProviders;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
}
