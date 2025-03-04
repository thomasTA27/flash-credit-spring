package org.example.flashcreditspring.service;


import org.example.flashcreditspring.model.Borrower;
import org.example.flashcreditspring.model.User;
import org.example.flashcreditspring.repository.BorrowerRepository;
import org.example.flashcreditspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BorrowerService {

    @Autowired
    private BorrowerRepository borrowerRepository;

    @Autowired
    private UserRepository userRepository;

    public Borrower createBorrower(Borrower borrower) {
        // Ensure the user exists before creating a borrower
        User user = userRepository.findById(borrower.getUser().getPhoneNum()).orElse(null);

        System.out.println("this is user num " + user.getPhoneNum());
        if (user == null) {
            throw new RuntimeException("User with phone number " + borrower.getUser().getPhoneNum() + " does not exist.");
        }
        // Link the borrower to the user
        borrower.setUser(user);
        // then we create the report

        // Save borrower data to the database
        return borrowerRepository.save(borrower);
    }



}
