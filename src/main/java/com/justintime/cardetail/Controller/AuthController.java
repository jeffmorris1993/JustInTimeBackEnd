package com.justintime.cardetail.Controller;

import com.justintime.cardetail.Model.RequestBody.AuthRequest;
import com.justintime.cardetail.Model.Response.AuthResponse;
import com.justintime.cardetail.Util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${jwt.cookieExpiry}")
    private int cookieExpiry;

    @PostMapping("/login")
    public AuthResponse AuthenticateAndGetToken(@RequestBody AuthRequest authRequestDTO, HttpServletResponse response){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDTO.getEmail(), authRequestDTO.getPassword()));
        if(authentication.isAuthenticated()){
            String accessToken = jwtUtil.GenerateToken(authRequestDTO.getEmail());
            // set accessToken to cookie header
            ResponseCookie cookie = ResponseCookie.from("accessToken", accessToken)
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(cookieExpiry)
                    .build();
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            return AuthResponse.builder()
                    .accessToken(accessToken)
                    .build();

        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }

    }
}