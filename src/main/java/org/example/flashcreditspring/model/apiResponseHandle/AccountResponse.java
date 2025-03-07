package org.example.flashcreditspring.model.apiResponseHandle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;



@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountResponse {

    private String type;
    private List<Account> data;// the name shouldnt be change as it need to match with the response from basiq

}
