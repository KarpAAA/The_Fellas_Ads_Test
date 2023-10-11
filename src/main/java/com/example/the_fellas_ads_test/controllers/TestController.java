package com.example.the_fellas_ads_test.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/502")
    public ResponseEntity<?> get502(){
        return ResponseEntity.status(502).body("502 error request");
    }
}
