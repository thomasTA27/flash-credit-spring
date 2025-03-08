package org.example.flashcreditspring.service;

import org.example.flashcreditspring.model.User;
import org.example.flashcreditspring.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HexFormat;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    public User createUser(String phoneNum, String password , String salt , String role) throws NoSuchAlgorithmException {

        String bcryptHashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        User user = new User();
        user.setPhoneNum(phoneNum);
        user.setPassword(bcryptHashedPassword);
        user.setSalt(salt);

        user.setRole(role);

        return userRepository.save(user);
    }

    public Optional<User> getUserByPhoneNum(String phoneNum) {
        return userRepository.findByPhoneNum(phoneNum);
    }





}
