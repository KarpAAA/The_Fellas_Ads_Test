package com.example.the_fellas_ads_test.services;


import com.example.the_fellas_ads_test.dtos.DomainDTO;
import com.example.the_fellas_ads_test.entity.Domain;
import com.example.the_fellas_ads_test.modal.DomainAddRequest;
import com.example.the_fellas_ads_test.repository.DomainRepository;
import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.core5.http.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DomainServices {
    private final DomainRepository domainRepository;
    private final SlackServices slackServices;
    private final HttpClient httpClient;

    @Value("${slack.channel}")
    private String SLACK_CHANNEL;
    @Value("${need.code}")
    private int NEED_RESPONSE_CODE;

    public List<DomainDTO> getAllDomain() {
        return domainRepository.findAll().stream().map(this::domainToDTO).toList();
    }

    public DomainDTO getSingleDomain(Long domainId) {
        Optional<Domain> domain = domainRepository.findById(domainId);
        if (domain.isPresent()) return domainToDTO(domain.get());
        else throw new NoSuchElementException();
    }

    public boolean addDomain(DomainAddRequest domainAddRequest) {
        HttpGet httpGet = new HttpGet(domainAddRequest.getDomain());
        HttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int statusCode = response.getCode();


        if (statusCode == NEED_RESPONSE_CODE){
            String message = "Domain " +
                    domainAddRequest.getDomain() +
                    " has replied with code " +
                    statusCode;
            slackServices.sendMessageToSlack(SLACK_CHANNEL, message);
        }
        domainRepository.save(new Domain(null, domainAddRequest.getDomain(), statusCode));
        return true;
    }

    private DomainDTO domainToDTO(Domain domain) {
        return new DomainDTO(domain.getId(),
                domain.getDomain(),
                HttpStatusCode.valueOf(domain.getStatusCode()));
    }
}
