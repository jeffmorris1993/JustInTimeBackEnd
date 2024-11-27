package com.justintime.cardetail.Domain_Service;

import com.justintime.cardetail.Model.Entity.UserEntity;
import com.justintime.cardetail.Model.RequestBody.UserRequest;
import com.justintime.cardetail.Model.Response.UserResponse;
import com.justintime.cardetail.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    ModelMapper modelMapper = new ModelMapper();

    public UserResponse saveUser(UserRequest userRequest) {
        if(userRequest.getEmail() == null){
            throw new RuntimeException("Parameter email is not found in request..!!");
        } else if(userRequest.getPassword() == null){
            throw new RuntimeException("Parameter password is not found in request..!!");
        }

        UserEntity savedUser = null;

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = userRequest.getPassword();
        String encodedPassword = encoder.encode(rawPassword);

        UserEntity user = modelMapper.map(userRequest, UserEntity.class);
        user.setPassword(encodedPassword);
        if(userRequest.getId() != null){
            UserEntity oldUser = userRepository.findFirstById(userRequest.getId());
            if(oldUser != null){
                oldUser.setId(user.getId());
                oldUser.setPassword(user.getPassword());
                oldUser.setEmail(user.getEmail());
                oldUser.setRoles(user.getRoles());

                savedUser = userRepository.save(oldUser);
            } else {
                throw new RuntimeException("Can't find record with identifier: " + userRequest.getId());
            }
        } else {
            savedUser = userRepository.save(user);
        }
        return modelMapper.map(savedUser, UserResponse.class);
    }

    public UserResponse getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
        String usernameFromAccessToken = userDetail.getUsername();
        UserEntity user = userRepository.findByEmail(usernameFromAccessToken);
        return modelMapper.map(user, UserResponse.class);
    }

    public List<UserResponse> getAllUser() {
        List<UserEntity> users = userRepository.findAll();
        Type setOfDTOsType = new TypeToken<List<UserResponse>>(){}.getType();
        return modelMapper.map(users, setOfDTOsType);
    }
}
