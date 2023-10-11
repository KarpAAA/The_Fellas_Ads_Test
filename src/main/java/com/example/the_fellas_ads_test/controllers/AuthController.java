package com.example.the_fellas_ads_test.controllers;

import com.example.the_fellas_ads_test.modal.JwtRequest;
import com.example.the_fellas_ads_test.modal.JwtResponse;
import com.example.the_fellas_ads_test.services.JwtServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtServices jwtServices;


    @PostMapping()
    public ResponseEntity<JwtResponse> generateToken(@RequestBody JwtRequest jwtRequest){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        jwtRequest.getUsername(),
                        jwtRequest.getPassword()));

        return ResponseEntity
                .ok()
                .body(jwtServices.createToken(jwtRequest));
    }


}
