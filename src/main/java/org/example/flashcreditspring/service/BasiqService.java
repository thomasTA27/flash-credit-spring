package org.example.flashcreditspring.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import lombok.SneakyThrows;
import org.example.flashcreditspring.model.Borrower;
import org.example.flashcreditspring.model.BorrowerDTO;
import org.example.flashcreditspring.model.BorrowerReport;
import org.example.flashcreditspring.model.apiResponseHandle.Account;
import org.example.flashcreditspring.model.apiResponseHandle.AccountResponse;
import org.example.flashcreditspring.model.apiResponseHandle.AffordabilityResponse;
import org.example.flashcreditspring.repository.BorrowerReportRepository;
import org.example.flashcreditspring.repository.BorrowerRepository;
import org.example.flashcreditspring.util.BasiqUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


@Service
public class BasiqService {

    @Value("${basiq.api.url.base}")
    private String BASE_URL;

    @Autowired
    private BasiqUtil basiqUtil;

    @Autowired
    private BorrowerRepository borrowerRepository;


    @Autowired
    private BorrowerReportRepository borrowerReportRepository;

//    @Autowired
//    private EntityManager entityManager;

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


    @SneakyThrows
    public List<Account> getListAccount(String basiqId) {
        WebClient webClient = webClientBuilder.baseUrl(BASE_URL).build();
        String response = webClient.get()
                .uri("/users/"  + basiqId +  "/accounts")
                .header(HttpHeaders.ACCEPT, "application/json")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + basiqUtil.getAccessCode() )
                .retrieve()
                .bodyToMono(String.class)
                .block();  // block() is used here to make it a synchronous call (similar to HttpClient)

        AccountResponse accountResponse = objectMapper.readValue(response, AccountResponse.class);
        return accountResponse.getData();

    }

    @SneakyThrows
    public String checkBorrowerConnection(String basiqId)  {
        WebClient webClient = webClientBuilder.baseUrl(BASE_URL).build();
        String status = null;

            try {
            String response = webClient.get()
                    .uri("/users/"  + basiqId  + "/connections")
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .header(HttpHeaders.ACCEPT, "application/json")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + basiqUtil.getAccessCode() )
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();  // block() is used here to make it a synchronous call (similar to HttpClient)

                JsonNode rootNode = objectMapper.readTree(response);
                JsonNode dataArray = rootNode.path("data");

                if (dataArray.isArray() && dataArray.size() > 0) {
                    JsonNode firstConnection = dataArray.get(0); // Get first object in the array
                    status = firstConnection.path("status").asText();
                } else {
                    System.out.println("No connections found.");
                }

                return status;
        } catch (WebClientResponseException ex) {
            // Handle error responses here
            throw new Exception("Error during API call from func createBorrowerBasiq in BasiqService class : " + ex.getResponseBodyAsString(), ex);
        }

    }



    @SneakyThrows
    public void createBorrowerReport(String basiqId , double askLoanAmount , double askLoanDuration){
        WebClient webClient = webClientBuilder.baseUrl(BASE_URL).build();

        try {
            String jsonResponse = webClient.post()
                    .uri("/users/"  + basiqId  + "/affordability")
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .header(HttpHeaders.ACCEPT, "application/json")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + basiqUtil.getAccessCode() )
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();  // block() is used here to make it a synchronous call (similar to HttpClient)
            AffordabilityResponse response = objectMapper.readValue(jsonResponse ,AffordabilityResponse.class );

            double totalAsset = safeParseDouble(response.getSummary().getAssets());
            double totalLiability = safeParseDouble(response.getSummary().getLiabilities());

            double avgIncome = safeParseDouble(response.getSummary()
                    .getRegularIncome()
                    .getPrevious3Months()
                    .getAvgMonthly());

            double netPosition = totalAsset - totalLiability;

            double creditLimit = safeParseDouble(response.getSummary().getCreditLimit());
            double expense = safeParseDouble(response.getSummary().getExpenses());
            double saving = safeParseDouble(response.getSummary().getSavings());
            double loanRepayment = safeParseDouble(response.getSummary().getLoanRepaymentsMonthly());

            double DTI = (loanRepayment / avgIncome) * 100;

            double disposableIncome = avgIncome - (expense + loanRepayment);

            double maximumMonthlyRepayment = 0.35 * disposableIncome;

            double interestRatePerWeek = 0.001; //this one should be change

            double recomendedLoanAmount = recomendLoan(disposableIncome, interestRatePerWeek * 4, askLoanDuration / 4);

            String incomeLink = response.getLinks().getIncome();
            String expenseLink = response.getLinks().getExpenses();

            Borrower b1 = borrowerRepository.findById(basiqId).orElse(null);

            if (b1 != null) {  // Ensure the borrower exists before proceeding

//                b1 = entityManager.merge(b1);
                BorrowerReport borrowerReport = new BorrowerReport(
                        basiqId,  // Pass only the Borrower object
                        totalAsset, totalLiability, netPosition, creditLimit,
                        saving, avgIncome, expense, loanRepayment, DTI,
                        disposableIncome, maximumMonthlyRepayment, interestRatePerWeek,
                        askLoanDuration, askLoanAmount, recomendedLoanAmount,
                        incomeLink, expenseLink, false
                );
//                BorrowerReport borrowerReport = new BorrowerReport();
//                System.out.println("Borrower Report : before ");
//                borrowerReport.setBorrower(b1);
//                borrowerReport.setBasiqId(b1.getBasiqUserId());

                borrowerReportRepository.save(borrowerReport);


                System.out.println("Borrower Report : after ");
            } else {
                throw new RuntimeException("Borrower with ID " + basiqId + " not found.");
            }
//            BorrowerReport borrowerReport = new BorrowerReport(  basiqId, b1,  totalAsset,  totalLiability,  netPosition, creditLimit,
//                    saving,  avgIncome,  expense, loanRepayment,  DTI,  disposableIncome, maximumMonthlyRepayment, interestRatePerWeek ,
//                    askLoanDuration,  askLoanAmount,  recomendedLoanAmount, incomeLink,  expenseLink , false);
//            borrowerReportRepository.save(borrowerReport);


        } catch (WebClientResponseException ex) {
            // Handle error responses here
            throw new Exception("Error during API call from func createBorrowerBasiq in BasiqService class : " + ex.getResponseBodyAsString(), ex);
        }
    }

    public static double recomendLoan(double monthlyRepayment, double interestPerMonth, double durationInMonths) {
        return (monthlyRepayment * (1 - Math.pow(1 + interestPerMonth, - durationInMonths )) / interestPerMonth); }


    private static double safeParseDouble(String value) {
        if (value == null || value.trim().isEmpty()) {
            return 0.0; // Default to 0 if null or empty
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0; // Default to 0 if parsing fails
        }
    }


}



