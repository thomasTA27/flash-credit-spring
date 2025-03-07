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

import java.util.HashMap;
import java.util.Map;
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

        String basiqUserId = borrowerService.createBorrower(borrowerDTO);

        String tokenBounce = basiqUtil.getAccessCodeClientSide(basiqUserId);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("tokenBounce", tokenBounce);
        response.put("basiqUserId", basiqUserId);

        return ResponseEntity.ok().body(response);
    }
}
