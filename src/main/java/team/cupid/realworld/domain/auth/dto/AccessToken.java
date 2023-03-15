package team.cupid.realworld.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class AccessToken {

    @NotBlank(message = "accessToken은 필수 값입니다.")
    private String accessToken;

}
