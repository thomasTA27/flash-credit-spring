package org.example.flashcreditspring.model.apiResponseHandle;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // Add this annotation
public class Account {

    private String id;
    private String accountNo;
    private String name;
    private String balance;
    private String availableFunds;
}
