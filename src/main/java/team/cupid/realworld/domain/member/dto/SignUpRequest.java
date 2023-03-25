package team.cupid.realworld.domain.member.dto;

import lombok.*;
import team.cupid.realworld.domain.member.domain.Member;
import team.cupid.realworld.domain.member.domain.RoleType;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SignUpRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String nickname;

    @NotNull
    @AssertFalse
    private Boolean emailExists;

    @NotNull
    @AssertFalse
    private Boolean nicknameExists;


    public Member toEntity() {
        return Member.builder()
                .email(this.email)
                .password(this.password)
                .nickname(this.nickname)
                .roleType(RoleType.USER)
                .build();
    }

}
