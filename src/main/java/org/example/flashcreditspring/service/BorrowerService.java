package org.example.flashcreditspring.service;


import org.example.flashcreditspring.model.Borrower;
import org.example.flashcreditspring.model.BorrowerDTO;
import org.example.flashcreditspring.model.User;
import org.example.flashcreditspring.repository.BorrowerRepository;
import org.example.flashcreditspring.repository.UserRepository;
import org.example.flashcreditspring.util.BasiqUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BorrowerService {

    @Autowired
    private BorrowerRepository borrowerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BasiqUtil basiqUtil;

    public Borrower createBorrower(BorrowerDTO borrowerDTO) {
        // Ensure the user exists before creating a borrower
        User user = userRepository.findById(borrowerDTO.getMobileNumber() ).orElse(null) ;

        Borrower borrower = new Borrower();
//        assert user != null;
        if (user != null) {
            System.out.println("this is user num " + user.getPhoneNum());
            borrower.setTitle(borrowerDTO.getTitle());
            borrower.setFirstName(borrowerDTO.getFirstName());
            borrower.setMiddleName(borrowerDTO.getMiddleName());
            borrower.setLastName(borrowerDTO.getLastName());
            borrower.setDob(borrowerDTO.getDob());
            borrower.setAddress(borrowerDTO.getAddress());
            borrower.setApt(borrowerDTO.getApt());
            borrower.setCity(borrowerDTO.getCity());
            borrower.setState(borrowerDTO.getState());
            borrower.setPostalCode(borrowerDTO.getPostalCode());
            borrower.setCountry(borrowerDTO.getCountry());
            borrower.setEmail(borrowerDTO.getEmail());

        }
        else {
            throw new RuntimeException("User with phone number " + borrower.getUser().getPhoneNum() + " does not exist.");
        }


        // Link the borrower to the user
//        borrower.setUser(user);
        // then we create the report

        // Save borrower data to the database
        return borrowerRepository.save(borrower);
    }



}
