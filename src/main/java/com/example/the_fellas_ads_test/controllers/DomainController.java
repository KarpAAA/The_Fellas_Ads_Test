package com.example.the_fellas_ads_test.controllers;


import com.example.the_fellas_ads_test.dtos.DomainDTO;
import com.example.the_fellas_ads_test.modal.DomainAddRequest;
import com.example.the_fellas_ads_test.services.DomainServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/domain")
@RequiredArgsConstructor
public class DomainController {
    private final DomainServices domainServices;

    @GetMapping()
    public ResponseEntity<List<DomainDTO>> getAllDomains(){
        return ResponseEntity
                .ok()
                .body(domainServices.getAllDomain());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DomainDTO> getSingleDomain(@PathVariable(name = "id") Long domainId){
        return ResponseEntity
                .ok()
                .body(domainServices.getSingleDomain(domainId));
    }

    @PostMapping()
    public ResponseEntity<?> addDomain(@RequestBody DomainAddRequest domainAddRequest){
        String addingResult = domainServices.addDomain(domainAddRequest) ?
                "Domain was added successfully" : "Domain was not added";
        return ResponseEntity
                .ok()
                .body(addingResult);
    }
}
