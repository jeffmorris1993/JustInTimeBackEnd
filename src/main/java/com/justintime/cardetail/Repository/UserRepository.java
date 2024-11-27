package com.justintime.cardetail.Repository;

import com.justintime.cardetail.Model.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findFirstById(Long id);
}
