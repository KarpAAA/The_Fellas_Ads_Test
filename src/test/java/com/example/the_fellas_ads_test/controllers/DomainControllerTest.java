package com.example.the_fellas_ads_test.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class DomainControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void addDomainForbiddenCase() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/domain")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void addDomainBadRequestCase() throws Exception {
        String authRequestBody = "{ \"username\": \"Admin\", \"password\": \"1111\" }";
        String token = mockMvc.perform(MockMvcRequestBuilders.post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(authRequestBody)).andReturn().getResponse().getContentAsString();
        token = token.substring(10, token.length()-2);


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/domain")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void addDomainNormalCase() throws Exception {
        String authRequestBody = "{ \"username\": \"Admin\", \"password\": \"1111\" }";
        String domainRequestBody = "{ \"domain\": \"http://www.example.com\"}";
        String token = mockMvc.perform(MockMvcRequestBuilders.post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(authRequestBody)).andReturn().getResponse().getContentAsString();
        token = token.substring(10, token.length()-2);


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/domain")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(domainRequestBody))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}