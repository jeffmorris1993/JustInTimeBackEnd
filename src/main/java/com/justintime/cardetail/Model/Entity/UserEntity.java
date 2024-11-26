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
@Table(name = "user")
public class UserEntity {
    @Id
    @GenericGenerator(name = "generator", strategy ="uuid2")
    @GeneratedValue(generator = "generator")
    @Type(type="pg-uuid")
    @Column(columnDefinition = "uniqueidentifier")
    private UUID id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
}
