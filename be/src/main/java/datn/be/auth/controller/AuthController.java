package datn.be.auth.controller;

import datn.be.auth.dto.request.ForgotPasswordRequest;
import datn.be.auth.dto.request.ResetPasswordRequest;
import datn.be.auth.dto.response.AuthResponse;
import datn.be.auth.dto.request.LoginRequest;
import datn.be.auth.dto.request.RegisterRequest;
import datn.be.auth.helpers.ResponseHelper;
import datn.be.auth.helpers.StandardResponse;
import datn.be.auth.service.AuthService;
import datn.be.model.UserView;
import jakarta.validation.Valid;
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
            AuthResponse token = authService.register(registerRequest);
            return ResponseEntity.ok(ResponseHelper.createStandardResponse("success", 0, "Registered successfully", token));
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            return ResponseEntity.ok(ResponseHelper.createStandardResponse("error", -1, e.getMessage(), null));
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<StandardResponse<UserView>> forgetPassword(@Valid @RequestBody ForgotPasswordRequest registerRequest) {
        try {
            UserView token = authService.forgotPassword(registerRequest);
            return ResponseEntity.ok(ResponseHelper.createStandardResponse("success", 0, "Successfully", token));
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            return ResponseEntity.ok(ResponseHelper.createStandardResponse("error", -1, e.getMessage(), null));
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<StandardResponse<Optional<AuthResponse>>> changePassword(@RequestBody ResetPasswordRequest requestDto) {
        try {
            Optional<AuthResponse> token = authService.resetPassword(requestDto);
            return ResponseEntity.ok(ResponseHelper.createStandardResponse("success", 0, "Successfully", token));
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            return ResponseEntity.ok(ResponseHelper.createStandardResponse("error", -1, e.getMessage(), null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<StandardResponse<Optional<AuthResponse>>> login(@RequestBody LoginRequest loginRequest) {
        try {
            Optional<AuthResponse> dataResponse = authService.login(loginRequest);
            return ResponseEntity.ok(ResponseHelper.createStandardResponse("success", 0, "Login successful", dataResponse));
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            return ResponseEntity.ok(ResponseHelper.createStandardResponse("error", -1, e.getMessage(), null));
        }

    }
}


