package datn.be.auth.controller;

import datn.be.auth.helpers.ResponseHelper;
import datn.be.auth.helpers.StandardResponse;
import datn.be.auth.model.User;
import datn.be.auth.service.CustomUserDetailsService;
import datn.be.model.UserView;
import datn.be.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/me")
public class MeController {

    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private UserService userProfileService;


    @GetMapping
    public ResponseEntity<StandardResponse<UserView>> getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String email = ((UserDetails) principal).getUsername();
            User user = userService.getUserByEmail(email);
            UserView userView = userProfileService.findById(user.getId());
            return ResponseEntity.ok(ResponseHelper.createStandardResponse("success", 0, "User details fetched successfully", userView));
        } else {
            return ResponseEntity.status(401).body(ResponseHelper.createStandardResponse("error", -1, "No user is logged in", null));
        }
    }

    @PutMapping
    public ResponseEntity<StandardResponse<UserView>> updateProfile(@RequestBody UserView u) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String email = ((UserDetails) principal).getUsername();
            User user = userService.getUserByEmail(email);

            UserView userView = userProfileService.updateProfile(user.getId(), u);
            return ResponseEntity.ok(ResponseHelper.createStandardResponse("success", 0, "User details fetched successfully", userView));
        } else {
            return ResponseEntity.status(401).body(ResponseHelper.createStandardResponse("error", -1, "No user is logged in", null));
        }
    }
}
