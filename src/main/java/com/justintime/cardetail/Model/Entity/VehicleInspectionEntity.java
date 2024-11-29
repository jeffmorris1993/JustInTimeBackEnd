package com.justintime.cardetail.Model.Entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
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
@Table(name = "vehicle_inspection")
public class VehicleInspectionEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID vehicleInspectionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicleId", nullable = false)
    private VehicleEntity vehicle;

    private String externalInspectionValueTypeId;

    private String internalInspectionValueTypeId;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
}
