package team.cupid.realworld.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class SignInDto {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
