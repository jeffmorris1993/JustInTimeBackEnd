package com.justintime.cardetail.Model.Entity;

import lombok.*;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;


@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "role")
public class UserRoleEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;
    private String name;
}
