package com.justintime.cardetail.Repository;

import com.justintime.cardetail.Model.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository  extends JpaRepository<UserEntity, UUID> {
    UserEntity findByEmail(String email);
    UserEntity findFirstById(UUID id);
}
