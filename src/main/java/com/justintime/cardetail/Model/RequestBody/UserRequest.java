package com.justintime.cardetail.Model.RequestBody;

import com.justintime.cardetail.Model.Entity.UserRoleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    private Long id;
    private String email;
    private String password;
    private Set<UserRoleEntity> roles;
}
