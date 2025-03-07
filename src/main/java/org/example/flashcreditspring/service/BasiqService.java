package org.example.flashcreditspring.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.flashcreditspring.model.BorrowerDTO;
import org.example.flashcreditspring.util.BasiqUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;


@Service
public class BasiqService {

    @Value("${basiq.api.url.base}")
    private String BASE_URL;

    @Autowired
    private BasiqUtil basiqUtil;

    private final WebClient.Builder webClientBuilder;
    private final ObjectMapper objectMapper;

    public BasiqService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClientBuilder = webClientBuilder;
        this.objectMapper = objectMapper;
    }

    public String createBorrowerBasiq(BorrowerDTO borrowerDTO) throws Exception {
        WebClient webClient = webClientBuilder.baseUrl(BASE_URL).build();
        try {
            String response = webClient.post()
                    .uri("/users")
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .header(HttpHeaders.ACCEPT, "application/json")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + basiqUtil.getAccessCode() )
                    .bodyValue(borrowerDTO)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();  // block() is used here to make it a synchronous call (similar to HttpClient)

            // Parsing the response JSON to extract the ID
            JsonNode rootNode = objectMapper.readTree(response);
            return rootNode.get("id").asText();
        } catch (WebClientResponseException ex) {
            // Handle error responses here
            throw new Exception("Error during API call from func createBorrowerBasiq in BasiqService class : " + ex.getResponseBodyAsString(), ex);
        }

    }


}



