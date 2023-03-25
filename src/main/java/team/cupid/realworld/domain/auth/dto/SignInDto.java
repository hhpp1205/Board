package team.cupid.realworld.domain.auth.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SignInDto {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

}
