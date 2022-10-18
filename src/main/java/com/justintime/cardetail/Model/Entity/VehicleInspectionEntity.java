package com.justintime.cardetail.Model.Entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
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
    @GenericGenerator(name = "generator", strategy ="uuid2")
    @GeneratedValue(generator = "generator")
    @Type(type="pg-uuid")
    @Column(columnDefinition = "uniqueidentifier")
    private UUID vehicleInspectionId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicleId", nullable = false)
    private VehicleEntity vehicle;

    private Integer inspectionTypeId;

    private String inspectionValueTypeId;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
}
