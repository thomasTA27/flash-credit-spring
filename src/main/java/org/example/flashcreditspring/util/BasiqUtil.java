package org.example.flashcreditspring.util;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
//import org.hibernate.annotations.Comment;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
//import org.springframework.web.reactive.function.client.WebClient;

@Component
public class BasiqUtil {
    private  WebClient webClient;
    private final ObjectMapper objectMapper;

//    ObjectMapper mapper = new ObjectMapper();
//    @Autowired
//    ObjectMapper objectMapper ;

    @Value("${basiq.api.key}")
    private String apiKey;

    @Value("${basiq.api.url.token}")
    private String apiUrl;

    public BasiqUtil(ObjectMapper objectMapper ) {
//        System.out.println("BasiqUtil class yo this is api url lmao " + apiUrl);
//        System.out.println("BasiqUtil class API URL: " + apiUrl);  // this will print out null
//        this.webClient = webClientBuilder.baseUrl(apiUrl).build();
        this.objectMapper = objectMapper;
    }

    //need to do this as if I use constructor injection the conbstructor is called before the
    //field get inject can get null
    //postCOnstructor make sure the value inject propery
    @PostConstruct
    public void init() {
        System.out.println("BasiqUtil class API URL: " + apiUrl);  // Log the apiUrl
        this.webClient = WebClient.builder().baseUrl(apiUrl).build();
    }


    public String getAccessCode() {
        try {
            String response = webClient.post()
                    .header("accept", "application/json")
                    .header("basiq-version", "3.0")
                    .header("content-type", "application/x-www-form-urlencoded")
                    .header("Authorization", "Basic " + apiKey)
                    .bodyValue("scope=SERVER_ACCESS")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JsonNode jsonNode = objectMapper.readTree(response);
            return jsonNode.get("access_token").asText();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public String getAccessCodeClientSide(String basiqUserId) {
        try {
            String response = webClient.post()
                    .header("accept", "application/json")
                    .header("basiq-version", "3.0")
                    .header("content-type", "application/x-www-form-urlencoded")
                    .header("Authorization", "Basic " + apiKey)
                    .bodyValue("scope=CLIENT_ACCESS&userId=" + basiqUserId)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JsonNode jsonNode = objectMapper.readTree(response);
            return jsonNode.get("access_token").asText();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
