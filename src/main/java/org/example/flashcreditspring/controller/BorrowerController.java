package org.example.flashcreditspring.controller;


import lombok.SneakyThrows;
import org.example.flashcreditspring.model.Borrower;
import org.example.flashcreditspring.model.BorrowerDTO;
import org.example.flashcreditspring.model.User;
import org.example.flashcreditspring.model.apiResponseHandle.Account;
import org.example.flashcreditspring.repository.UserRepository;
import org.example.flashcreditspring.service.BasiqService;
import org.example.flashcreditspring.service.BorrowerService;
import org.example.flashcreditspring.util.BasiqUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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
    @Autowired
    private BasiqService basiqService;


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


    @PostMapping("/basiqBankConnectionStatus")
    public ResponseEntity<?> createBorrower(@RequestBody Map<String, String> requestBody) {

        String basiqUserId = requestBody.get("basiqUserId");
        double loanAmount = Double.parseDouble(requestBody.get("loanAmount"));
        double loanTenure = Double.parseDouble(requestBody.get("loanTenure")) ;

        String status = basiqService.checkBorrowerConnection(basiqUserId);

        Map<String, String> response = new HashMap<>();
        if(status.equals("active")) {
        response.put("status", "active");
            basiqService.createBorrowerReport(basiqUserId, loanAmount , loanTenure);
        }
        else {

            response.put("status", "error");
        }
        return ResponseEntity.ok().body(response);
    }


    @PostMapping("/deleteBorrowerDetail")
    public ResponseEntity<?> deleteBorrowerDetail(@RequestBody Map<String, String> requestBody) {

        String basiqUserId = requestBody.get("basiqUserId");

//        String status = basiqService.checkBorrowerConnection(basiqUserId);



        Map<String, String> response = new HashMap<>();

        return ResponseEntity.ok().body(response);
    }


    @GetMapping("/getListAccount")
    public ResponseEntity<?> getListAccount(@RequestHeader("basiqUserId") String basiqUserId) {

        List<Account> list = basiqService.getListAccount(basiqUserId);

        if(!list.isEmpty()) {

            return ResponseEntity.ok().body(list);
        }
        else {

            return ResponseEntity.noContent().build();
        }

    }
}
