package datn.be.auth.dto.response;

import datn.be.model.UserView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private String token;
    private UserView user;

    public AuthResponse(String token, UserView u) {
        this.token = token;
        this.user = u;
    }
}

