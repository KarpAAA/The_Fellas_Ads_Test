package com.example.the_fellas_ads_test.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatusCode;

@Data
@AllArgsConstructor
public class DomainDTO {
    private Long id;
    private String domain;
    private HttpStatusCode httpStatusCode;
}
