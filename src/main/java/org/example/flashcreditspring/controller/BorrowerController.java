package org.example.flashcreditspring.controller;


import org.example.flashcreditspring.model.Borrower;
import org.example.flashcreditspring.model.BorrowerDTO;
import org.example.flashcreditspring.model.User;
import org.example.flashcreditspring.repository.UserRepository;
import org.example.flashcreditspring.service.BorrowerService;
import org.example.flashcreditspring.util.BasiqUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/borrowers")
public class BorrowerController {
    @Autowired
    private  BorrowerService borrowerService;


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private BasiqUtil basiqUtil;

    @GetMapping("/getAccessCode")
    public ResponseEntity<String> getBorrowerById() {


        return ResponseEntity.ok (   "this is api token is " + basiqUtil.getAccessCode());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createBorrower(@RequestBody BorrowerDTO borrowerDTO) {
//        borrowerService.createBorrower();

        Borrower borrower = borrowerService.createBorrower(borrowerDTO);

//        System.out.println("Borrower: whasssup " + borrowerDTO.getCity());


//        Optional<User> userOptional = userRepository.findById(borrower.getMobileNumber());
//
//        if (userOptional.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body("Error: User with phone number " + borrower.getMobileNumber() + " not found.");
//        }
//      User user = userOptional.get();


//        borrowerService.createBorrower(borrower);

        return ResponseEntity.ok().body("Borrower created hahah " + borrowerDTO.getCity());
    }
}
