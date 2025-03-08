package org.example.flashcreditspring.model;


import lombok.Data;

@Data
public class BorrowerDetailDTO {

    private String title;
    private String firstName;
    private String middleName;
    private String lastName;
    private String dob;
    private String address;
    private String apt;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String mobileNumber;
    private String email;


    private double totalAsset;
    private double totalLiability;
    private double netPosition; //asset - liability
    private double creditLimit;
    private double saving;
    private double income;
    private double expense;
    private double loanRepayment;

    private double DTI; //Debt-to-Income Ratio (DTI)

    //    A DTI of 40.63% is relatively high. Most lenders prefer a DTI below 36%, though some may accept up to 43%.

    //DEBT (total loan payment each month (home loan + credit card payback) / income *100)

    //Debt Repayments mean all of the debt that need to be paid each month

    private double disposableIncome;
    //Disposable Income=Monthly Income−(Expenses+Debt Repayments)

    //A common rule is that loan repayments should not exceed 30-40% of disposable income.


    private double maximumMonthlyRepayment;
    //Maximum Monthly Loan Repayment=Disposable Income×0.35 //this mean how much borrower can paid each month


    private double interestRatePerWeek;

    private double durationInWeeks;
    private double loanAmount;

    private double recomendedLoanAmount;

    private String  incomeLink;

    private String  expenseLink;


    private boolean  approveStatus;


}
