package org.example.flashcreditspring.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "borrowerReport")
public class BorrowerReport {




    @Id
    private String basiqId;  // Use Borrower’s basiqId as Primary Key

//    @OneToOne
//    @JoinColumn(name = "basiq_id")  // This column references borrower.basiqUserId
//    @MapsId  // Ensures that it shares the same ID as Borrower
//    private Borrower borrower;


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



    public BorrowerReport() {

    }

    //    public BorrowerReport(String basiqId, double totalAsset, double totalLiability, double netPosition, double creditLimit,
//                          double saving, double income, double expense, double loanRepayment, double DTI,
//                          double disposableIncome, double maximumMonthlyRepayment, double interestRatePerWeek,
//                          double durationInWeeks, double loanAmount, double recomendedLoanAmount, String incomeLink,
//                          String expenseLink, boolean approveStatus) {
//        this.basiqId = basiqId;
//        this.totalAsset = totalAsset;
//        this.totalLiability = totalLiability;
//        this.netPosition = netPosition;
//        this.creditLimit = creditLimit;
//        this.saving = saving;
//        this.income = income;
//        this.expense = expense;
//        this.loanRepayment = loanRepayment;
//        this.DTI = DTI;
//        this.disposableIncome = disposableIncome;
//        this.maximumMonthlyRepayment = maximumMonthlyRepayment;
//        this.interestRatePerWeek = interestRatePerWeek;
//        this.durationInWeeks = durationInWeeks;
//        this.loanAmount = loanAmount;
//        this.recomendedLoanAmount = recomendedLoanAmount;
//        this.incomeLink = incomeLink;
//        this.expenseLink = expenseLink;
//        this.approveStatus = approveStatus;
//    }

}
