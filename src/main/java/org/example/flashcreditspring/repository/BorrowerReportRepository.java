package org.example.flashcreditspring.repository;

import org.example.flashcreditspring.model.Borrower;
import org.example.flashcreditspring.model.BorrowerReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BorrowerReportRepository extends JpaRepository<BorrowerReport, String> {
}
