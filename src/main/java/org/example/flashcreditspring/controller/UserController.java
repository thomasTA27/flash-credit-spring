package org.example.flashcreditspring.controller;


import org.example.flashcreditspring.model.User;
import org.example.flashcreditspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;


import org.example.flashcreditspring.util.JwtUtil;

@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser(@RequestBody Map<String, String> payload) {
        String phoneNum = payload.get("tel");
        String password = payload.get("password");
        String salt = payload.get("salt");

        if (phoneNum == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "Missing required fields"));
        }

        try {
            User newUser = userService.createUser(phoneNum, password ,salt);
            return ResponseEntity.ok(Map.of("status", "success", "message", "User created", "userId", newUser.getPhoneNum()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("status", "error", "message", "Signup failed"));
        }
    }

    @PostMapping("/getSalt")
    public ResponseEntity<?> getSalt(@RequestBody Map<String, String> payload) {
        String phoneNum = payload.get("tel");
        Optional<User> user = userService.getUserByPhoneNum(phoneNum);

        if (user.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "User not found"));
        }

        return ResponseEntity.ok(Map.of("salt", user.get().getSalt()));
    }


    @PostMapping("/signIn")
    public ResponseEntity<?> signInUser(@RequestBody Map<String, String> payload) {
        String phoneNum = payload.get("tel");
        String hashedPassword = payload.get("password");

        Optional<User> userOpt = userService.getUserByPhoneNum(phoneNum);
        if (userOpt.isEmpty() || !userOpt.get().verifyPassword(hashedPassword)) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials"));
        }

        String token = jwtUtil.generateToken(phoneNum);
        return ResponseEntity.ok(Map.of("token", token));
    }

}