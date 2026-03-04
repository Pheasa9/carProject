package com.sa.leanning.CarProject.controller;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
@CrossOrigin(origins = "*")
public class EmailVerificationController {

    @Autowired
    private JavaMailSender mailSender;

    private final Map<String, String> codeStore = new ConcurrentHashMap<>();

    @PostMapping("/send-code")
    public ResponseEntity<?> sendCode(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email required"));
        }

        String code = String.valueOf(100000 + new Random().nextInt(900000));
        codeStore.put(email, code);

        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(email);
            msg.setFrom("PhoneShop <your-email@gmail.com>"); 
            msg.setSubject("Your Verification Code");
            msg.setText("Your verification code is: " + code + "\n\nThis code expires in 10 minutes.");
            mailSender.send(msg);
            return ResponseEntity.ok(Map.of("sent", true));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to send email"));
        }
    }

    @PostMapping("/verify-code")
    public ResponseEntity<?> verifyCode(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String code = body.get("code");

        String stored = codeStore.get(email);
        if (stored != null && stored.equals(code)) {
            codeStore.remove(email);
            return ResponseEntity.ok(Map.of("verified", true));
        }
        return ResponseEntity.ok(Map.of("verified", false));
    }
}