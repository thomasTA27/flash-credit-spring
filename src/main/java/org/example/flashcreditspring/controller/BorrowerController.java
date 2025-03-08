package org.example.flashcreditspring.controller;


import lombok.SneakyThrows;
import org.example.flashcreditspring.model.*;
import org.example.flashcreditspring.model.apiResponseHandle.Account;
import org.example.flashcreditspring.repository.BorrowerReportRepository;
import org.example.flashcreditspring.repository.BorrowerRepository;
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
//@CrossOrigin(origins = "*") //not recomended in production
@CrossOrigin(origins = "http://localhost:8080")
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
    @Autowired
    private BorrowerRepository borrowerRepository;
    @Autowired
    private BorrowerReportRepository borrowerReportRepository;


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

        String phone = requestBody.get("phone");


        BorrowerReport br = borrowerReportRepository.findById(basiqUserId).orElse(null);
        if (br != null) {
            borrowerReportRepository.delete(br);  // Deletes the entity from the database
        } else {
            System.out.println("BorrowerReport not found");
        }

        Borrower borrower = borrowerRepository.findById(basiqUserId).orElse(null);
        if (borrower != null) {
            borrowerRepository.delete(borrower);  // Deletes the entity from the database
        } else {
            System.out.println("BorrowerReport not found");
        }

        User user = userRepository.findById(phone).orElse(null);
        if (user != null) {
            user.removeBasiqId();
            userRepository.save(user);
        }
        else {
            System.out.println("User not found");
        }

            System.out.println("we done");
        //this can be optimized better by using foreign key then who depend on will be delete
        //delted basiq_id in the user -> the borrower and borrowerReport will disapear as well
        return ResponseEntity.ok().body("");
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


    @GetMapping("/getAllBorrower")
    public List<BorrowerDetailDTO> getListAccount() {
        return borrowerService.getAllBorrowers();
    }


    @GetMapping("/displayBorrower")
    public List<BorrowerDetailDTO> displayAllBorrower() {
        return borrowerService.getAllBorrowers();
    }
}
