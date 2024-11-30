package datn.be.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ForgotPasswordRequest {
    @NotNull(message = "Email không được để trống.")
    @NotBlank(message = "Email không được để trống.")
    private String email;
}
