package datn.be.auth.service;

import datn.be.auth.dto.request.ForgotPasswordRequest;
import datn.be.auth.dto.request.LoginRequest;
import datn.be.auth.dto.request.RegisterRequest;
import datn.be.auth.dto.request.ResetPasswordRequest;
import datn.be.auth.dto.response.AuthResponse;
import datn.be.auth.repository.UserRepository;
import datn.be.auth.security.JwtTokenUtil;
import datn.be.model.UserView;
import datn.be.service.EmailService;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public AuthResponse register(RegisterRequest registerRequest) {
        try {
            UserView user = new UserView();
            String type = registerRequest.getUser_type() != "" ? registerRequest.getUser_type() : "USER";
            user.setUser_type(type);
            user.setStatus(1);
            user.setEmail(registerRequest.getEmail());
            user.setName(registerRequest.getName());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword())); // Mã hóa mật khẩu
            userRepository.save(user);
            return new AuthResponse(this.jwtTokenUtil.generateToken(user.getEmail()), user);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("Đăng ký thất bại.");
        }
    }

    public Optional<AuthResponse> login(LoginRequest loginRequest)  {
        try {
            Optional<UserView> userOpt = userRepository.findByEmail(loginRequest.getEmail());
            if (userOpt.isEmpty()) {
                throw new RuntimeException("Tài khoản không tồn tại.");
            }
            if( ! passwordEncoder.matches(loginRequest.getPassword(), userOpt.get().getPassword())) {
                throw new RuntimeException("Mật khẩu không chính xác.");

            }
            return Optional.of(new AuthResponse(this.jwtTokenUtil.generateToken(userOpt.get().getEmail()), userOpt.get())); // Tạo JWT token dựa trên email
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("Đăng nhập thất bại.");
        }
    }

    public UserView forgotPassword(ForgotPasswordRequest requestDto) throws MessagingException {
        try {
            Optional<UserView> userOpt = userRepository.findByEmail(requestDto.getEmail());
            if(!userOpt.isPresent()) {
                throw new RuntimeException("Không tìm thấy tài khoản");
            }
            UserView user = userOpt.get();
            user.setRemember_token(this.emailService.generateResetToken(32));
            user.setEmail_verified_at(new Date());
            this.userRepository.save(user);
            String url = "http://localhost:4223/reset-password/"+ user.getRemember_token();
            String htmlContent =
                    "<div style=\"font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px;\">" +
                            "<h2 style=\"color: #333;\">Đặt lại mật khẩu của bạn</h2>" +
                            "<p>Chúng tôi đã nhận được yêu cầu đặt lại mật khẩu cho tài khoản của bạn.</p>" +
                            "<p>Nhấn vào nút bên dưới để tiến hành đặt lại mật khẩu:</p>" +
                            "<a href=\""+url+"\" style=\"display: inline-block; padding: 10px 20px; margin-top: 20px; color: #fff; background-color: #007bff; border-radius: 5px; text-decoration: none;\">Đặt lại mật khẩu</a>" +
                            "<p style=\"margin-top: 20px;\">Hoặc, bạn có thể sao chép và dán đường link sau vào trình duyệt của mình:</p>" +
                            "<p><a href=\""+url+"\" style=\"color: #007bff;\">"+url+"</a></p>" +
                            "<p>Nếu bạn không yêu cầu thay đổi mật khẩu, vui lòng bỏ qua email này.</p>" +
                            "<p>Trân trọng,<br/>Đội ngũ hỗ trợ của chúng tôi</p>" +
                            "</div>";
            this.emailService.sendEmail(user.getEmail(), "Đổi mật khẩu",htmlContent );
            return user;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("Quên mật khẩu thất bại.");
        }
    }

    public Optional<AuthResponse> resetPassword(ResetPasswordRequest requestDto) {
        try {
            LocalDateTime now = LocalDateTime.now();
            Optional<UserView> userOpt = this.userRepository.findByTokenAndEmailAt(requestDto.getToken(), Timestamp.valueOf(now));
            if(!userOpt.isPresent()) {
                throw new RuntimeException("Cập nhật mật khẩu thất bại.");
            }
            UserView user = userOpt.get();
            user.setPassword(passwordEncoder.encode(requestDto.getPassword())); // Mã hóa mật khẩu
            user.setRemember_token(null); // Mã hóa mật khẩu
            user.setEmail_verified_at(null); // Mã hóa mật khẩu
            this.userRepository.save(user);
            LoginRequest lg = new LoginRequest();
            lg.setPassword(requestDto.getPassword());
            lg.setEmail(user.getEmail());
            return this.login(lg);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("Thay đổi mật khẩu thất bại.");
        }
    }
}