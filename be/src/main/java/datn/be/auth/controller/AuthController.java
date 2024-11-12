package datn.be.auth.controller;

import datn.be.auth.dto.response.AuthResponse;
import datn.be.auth.dto.request.LoginRequest;
import datn.be.auth.dto.request.RegisterRequest;
import datn.be.auth.helpers.ResponseHelper;
import datn.be.auth.helpers.StandardResponse;
import datn.be.auth.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<StandardResponse<AuthResponse>> register(@RequestBody RegisterRequest registerRequest) {
        try {
            AuthResponse authResponse = authService.register(registerRequest);
            return ResponseEntity.ok(ResponseHelper.createStandardResponse("success", 0, "Registered successfully", authResponse));
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            return ResponseEntity.status(400).body(ResponseHelper.createStandardResponse("error", -1, "Registration failed", null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<StandardResponse<Optional<AuthResponse>>> login(@RequestBody LoginRequest loginRequest) {
        try {
            Optional<AuthResponse> authResponseOptional = authService.login(loginRequest);
            return ResponseEntity.ok(ResponseHelper.createStandardResponse("success", 0, "Login successful", authResponseOptional));
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            return ResponseEntity.ok(ResponseHelper.createStandardResponse("error", -1, "Invalid credentials", null));
        }

    }
}

