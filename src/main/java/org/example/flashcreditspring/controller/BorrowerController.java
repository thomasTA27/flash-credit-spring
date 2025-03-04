package org.example.flashcreditspring.controller;


import org.example.flashcreditspring.model.Borrower;
import org.example.flashcreditspring.model.User;
import org.example.flashcreditspring.repository.UserRepository;
import org.example.flashcreditspring.service.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/borrowers")
public class BorrowerController {
    @Autowired
    private  BorrowerService borrowerService;


    @Autowired
    private UserRepository userRepository;


    @PostMapping("/create")
    public ResponseEntity<?> createBorrower(@RequestBody Borrower borrower) {
//        borrowerService.createBorrower();

        System.out.println("Borrower: whasssup " + borrower.getUser().getPhoneNum());


//        Optional<User> userOptional = userRepository.findById(borrower.getMobileNumber());
//
//        if (userOptional.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body("Error: User with phone number " + borrower.getMobileNumber() + " not found.");
//        }
//      User user = userOptional.get();


        borrowerService.createBorrower(borrower);

        return ResponseEntity.ok().body("Borrower created hahah " + borrower.getCity());
    }
}
