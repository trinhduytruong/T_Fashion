package datn.be.auth.controller;

import datn.be.auth.helpers.ResponseHelper;
import datn.be.auth.helpers.StandardResponse;
import datn.be.auth.model.User;
import datn.be.auth.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeController {

    @Autowired
    private CustomUserDetailsService userService;


    @GetMapping("/api/v1/me")
    public ResponseEntity<StandardResponse<User>> getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String email = ((UserDetails) principal).getUsername(); // email thay vì username
            // Lấy thông tin đầy đủ từ service
            User user = userService.getUserByEmail(email);
            // Trả về response theo format yêu cầu
            return ResponseEntity.ok(ResponseHelper.createStandardResponse("success", 0, "User details fetched successfully", user));
        } else {
            return ResponseEntity.status(401).body(ResponseHelper.createStandardResponse("error", -1, "No user is logged in", null));
        }
    }
}
