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
@Table(name = "vehicle")
public class VehicleEntity {

    @Id
    @GenericGenerator(name = "generator", strategy ="uuid2")
    @GeneratedValue(generator = "generator")
    @Type(type = "uuid-char")
    @Column(columnDefinition = "uniqueidentifier")
    private UUID vehicleId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_customer", referencedColumnName = "customer_id", nullable = false)
    private CustomerEntity customerEntity;

    private int serviceTypeId;

    private String make;

    private String model;

    private int year;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

}
