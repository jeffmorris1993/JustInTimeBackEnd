package com.justintime.cardetail.Controller;

import com.justintime.cardetail.Domain_Service.UserService;
import com.justintime.cardetail.Model.Entity.UserEntity;
import com.justintime.cardetail.Util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private UserService userService;

    private JwtUtil jwtUtil;

    private AuthenticationManager authenticationManager;

    @GetMapping("/login-required")
    public ResponseEntity<String> loginRequired() {
        return ResponseEntity.status(401).body("You need to log in again.");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserEntity user) {
        userService.saveUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody UserEntity user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtil.generateToken(user.getEmail());
        return ResponseEntity.ok(token);
    }
}
