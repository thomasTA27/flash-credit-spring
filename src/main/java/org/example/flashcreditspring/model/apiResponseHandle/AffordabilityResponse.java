package org.example.flashcreditspring.model.apiResponseHandle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AffordabilityResponse {
    private String type;
    private String id;
    private String fromMonth;
    private String toMonth;
    private int coverageDays;
    private String generatedDate;
    private Summary summary;

    private List<Asset> assets;
    private Liabilities liabilities;
    private List<Object> external;
    private Links links;

    @Data
    public static class Summary {
        private String assets;
        private String liabilities;
        private String netPosition;
        private String creditLimit;
        private String expenses;
        private String savings;
        private String loanRepaymentsMonthly;
        private Object potentialLiabilitiesMonthly;
        private RegularIncome regularIncome;
    }

    @Data
    public static class RegularIncome {
        @JsonProperty("previous3Months")
        private Previous3Months previous3Months; //I want to call retrive this one
    }

    @Data
    public static class Previous3Months {
        @JsonProperty("avgMonthly")
        private String avgMonthly;
    }

    @Data
    public static class Asset {
        private String currency;
        private String balance;
        private String availableFunds;
        private String institution;
        private String type;
        private Account account;
        private Previous6Months previous6Months;
    }
//
//    @Data
//    public static class Previous6Months {
//        private String minBalance;
//        private String maxBalance;
//        private String arrears; // Added this field to match the JSON
//    }

    @Data
    public static class Previous6Months {
        private String minBalance;
        private String maxBalance;
        private String arrears; // For loans
        private String cashAdvances; // For credit cards
    }

    @Data
    public static class Liabilities {
        private List<Loan> loan;
        private List<Credit> credit;
    }

    @Data
    public static class Loan {
        private String currency;
        private String balance;
        private String availableFunds;
        private String institution;
        private Account account;
        private PreviousMonth previousMonth;
        private List<ChangeHistory> changeHistory;
        private Previous6Months previous6Months;
    }

    @Data
    public static class ChangeHistory {
        private String direction;
        private String amount;
        private String date;
        private String source;
    }

    @Data
    public static class Credit {
        private String currency;
        private String balance;
        private String availableFunds;
        private String institution;
        private String creditLimit;
        private Account account;
        private PreviousMonth previousMonth;
        private Previous6Months previous6Months;
    }

    @Data
    public static class PreviousMonth {
        private String totalCredits;
        private String totalDebits;
        private String totalInterestCharged;
        private String totalRepayments;
        private String minBalance;
        private String maxBalance;
    }

    @Data
    public static class Links {
        private String self;
        private String income;
        private String expenses;
        private List<String> accounts;
    }

    @Data
    public static class Account {
        private String product;
        private String type;
    }

}
