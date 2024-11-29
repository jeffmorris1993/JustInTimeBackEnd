package com.justintime.cardetail.Model.RequestBody;

import com.justintime.cardetail.Model.Entity.UserRoleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<UserRoleEntity> roles;
}
