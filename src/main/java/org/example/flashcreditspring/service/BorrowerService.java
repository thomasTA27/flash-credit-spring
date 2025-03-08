package org.example.flashcreditspring.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.flashcreditspring.model.*;
import org.example.flashcreditspring.repository.BorrowerReportRepository;
import org.example.flashcreditspring.repository.BorrowerRepository;
import org.example.flashcreditspring.repository.UserRepository;
import org.example.flashcreditspring.util.BasiqUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowerService {

    @Autowired
    private BorrowerRepository borrowerRepository;


    @Autowired
    private BorrowerReportRepository borrowerReportRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private BasiqUtil basiqUtil;


    @Autowired
    private BasiqService basiqService;

    @SneakyThrows
    public String createBorrower(BorrowerDTO borrowerDTO) {
        // Ensure the user exists before creating a borrower
        User user = userRepository.findById(borrowerDTO.getMobileNumber() ).orElse(null) ;
        String basiqUserId = null;
        Borrower borrower = new Borrower();
//        assert user != null;
        if (user != null) {
            System.out.println("this is user num " + user.getPhoneNum());
            borrower.setUser(user);
            //link borrower to user

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
            basiqUserId = basiqService.createBorrowerBasiq(borrowerDTO);

            user.setBasiqId(basiqUserId);
            userRepository.save(user);

            borrower.setBasiqUserId(basiqUserId);
            borrowerRepository.save(borrower);

        }
        else {
            throw new RuntimeException("User with phone number " + borrower.getUser().getPhoneNum() + " does not exist.");
        }
        return basiqUserId;
    }
//
//    public List<BorrowerDTO> getAllBorrowers() {
//        List<Borrower> borrowers = borrowerRepository.findAll();
//        return borrowers.stream()
//                .map(BorrowerDetailDTOMapper::toDTO)
//                .collect(Collectors.toList());
//    }

    public List<BorrowerDetailDTO> getAllBorrowers() {
        List<Borrower> borrowers = borrowerRepository.findAll();

        return borrowers.stream()
                .map(borrower -> {
                    BorrowerReport borrowerReport = borrowerReportRepository.findById(borrower.getBasiqUserId()).orElse(null);
                    return BorrowerDetailDTOMapper.toDTO(borrower, borrowerReport);
                })
                .collect(Collectors.toList());
    }




}
