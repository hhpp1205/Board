package team.cupid.realworld.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class SignInDto {
    @Email(message = "올바르지 않은 email 형식입니다.")
    @NotBlank(message = "email은 필수 값입니다.")
    private String email;

    @NotBlank(message = "password는 필수 값입니다.")
    private String password;
}
