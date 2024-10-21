package datn.be.auth.service;

import datn.be.auth.dto.LoginRequest;
import datn.be.auth.dto.RegisterRequest;
import datn.be.auth.model.User;
import datn.be.auth.repository.UserRepository;
import datn.be.auth.security.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public String register(RegisterRequest registerRequest) {
        try {
            logger.info("Register request: " + registerRequest);
            User user = new User();
            user.setEmail(registerRequest.getEmail());
            user.setName(registerRequest.getName());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            userRepository.save(user);
            String token = jwtTokenUtil.generateToken(user.getEmail());
            logger.info("token: {}", token);
            return token;
        } catch (Exception e) {
            logger.error("AuthService.register(): " + e);
            throw new RuntimeException(e);
        }
    }

    public Optional<String> login(LoginRequest loginRequest) {
        try {
            logger.info("Login request: " + loginRequest);
            Optional<User> userOpt = userRepository.findByEmail(loginRequest.getEmail());
            logger.info("userOpt.isPresent(): " + userOpt.isPresent());
            if (userOpt.isPresent() && passwordEncoder.matches(loginRequest.getPassword(), userOpt.get().getPassword())) {
                String token = jwtTokenUtil.generateToken(userOpt.get().getEmail());
                logger.info("token: {}", token);
                return Optional.of(token);
            }
            return Optional.empty();
        } catch (Exception e) {
            logger.error("AuthService.login(): " + e);
            throw new RuntimeException(e);
        }
    }
}

