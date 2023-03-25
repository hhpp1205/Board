package team.cupid.realworld.domain.auth.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class AccessToken {

    @NotBlank
    private String accessToken;

    public static AccessToken of(String accessToken) {
        return new AccessToken(accessToken);
    }



}
