package com.example.the_fellas_ads_test.services;

import com.example.the_fellas_ads_test.modal.JwtRequest;
import com.example.the_fellas_ads_test.modal.JwtResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtServices {
    private final UserDetailsService userService;

    @Value("${jwt.lifetime}")
    private Duration duration;

    @Value("${jwt.secret}")
    private String secret;

    private final String USERNAME_CLAIMS_FIELD = "username";
    private final String ROLE_CLAIMS_FIELD = "role";

    public JwtResponse createToken(JwtRequest jwtRequest) {
        UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUsername());

        Map<String, Object> claims = new HashMap<>();
        claims.put(USERNAME_CLAIMS_FIELD, userDetails.getUsername());
        claims.put(ROLE_CLAIMS_FIELD, userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        Date currentDate = new Date();

        String token = Jwts.builder()
                .setSubject(jwtRequest.getUsername())
                .setIssuedAt(currentDate)
                .setExpiration(new Date(currentDate.getTime() + duration.toMillis()))
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256,secret.getBytes())
                .compact();
        return new JwtResponse(token);
    }
    private Claims getClaims(String token){
        return Jwts
                .parser()
                .setSigningKey(secret.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }
    public Collection<String> getRoles(String token){
        return (List<String>) getClaims(token).get(ROLE_CLAIMS_FIELD);
    }
    public String getUsername(String token){
        return (String) getClaims(token).get(USERNAME_CLAIMS_FIELD);
    }
}
