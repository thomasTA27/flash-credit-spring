package org.example.flashcreditspring.model;


import lombok.Data;

@Data
public class BorrowerDetailDTOMapper {

    public static BorrowerDetailDTO toDTO(Borrower borrower , BorrowerReport borrowerReport) {
        BorrowerDetailDTO dto = new BorrowerDetailDTO();
        dto.setTitle(borrower.getTitle());
        dto.setFirstName(borrower.getFirstName());
        dto.setMiddleName(borrower.getMiddleName());
        dto.setLastName(borrower.getLastName());
        dto.setDob(borrower.getDob());
        dto.setAddress(borrower.getAddress());
        dto.setApt(borrower.getApt());
        dto.setCity(borrower.getCity());
        dto.setState(borrower.getState());
        dto.setPostalCode(borrower.getPostalCode());
        dto.setCountry(borrower.getCountry());
        dto.setEmail(borrower.getEmail());

        if (borrowerReport != null) {
            dto.setTotalAsset(borrowerReport.getTotalAsset());
            dto.setTotalLiability(borrowerReport.getTotalLiability());
            dto.setNetPosition(borrowerReport.getNetPosition());
            dto.setCreditLimit(borrowerReport.getCreditLimit());
            dto.setSaving(borrowerReport.getSaving());
            dto.setIncome(borrowerReport.getIncome());
            dto.setExpense(borrowerReport.getExpense());
            dto.setLoanRepayment(borrowerReport.getLoanRepayment());
            dto.setDTI(borrowerReport.getDTI());
            dto.setDisposableIncome(borrowerReport.getDisposableIncome());
            dto.setMaximumMonthlyRepayment(borrowerReport.getMaximumMonthlyRepayment());
            dto.setInterestRatePerWeek(borrowerReport.getInterestRatePerWeek());
            dto.setDurationInWeeks(borrowerReport.getDurationInWeeks());
            dto.setLoanAmount(borrowerReport.getLoanAmount());
            dto.setRecomendedLoanAmount(borrowerReport.getRecomendedLoanAmount());
            dto.setIncomeLink(borrowerReport.getIncomeLink());
            dto.setExpenseLink(borrowerReport.getExpenseLink());
            dto.setApproveStatus(borrowerReport.isApproveStatus());
        }

        return dto;
//        return dto;
    }
}
