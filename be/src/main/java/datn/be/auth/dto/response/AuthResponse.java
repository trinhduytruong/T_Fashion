package datn.be.auth.dto.response;

import datn.be.auth.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private User user;
    private String token;

    public AuthResponse(String token, User u) {
        this.user = u;
        this.token = token;
    }
}

