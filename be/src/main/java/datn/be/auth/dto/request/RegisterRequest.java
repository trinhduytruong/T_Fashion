package datn.be.auth.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String email;
    private String phone;
    public String name;
    private String password;
    public String user_type;
}
