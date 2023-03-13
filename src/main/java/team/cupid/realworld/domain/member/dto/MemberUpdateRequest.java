package team.cupid.realworld.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class MemberUpdateRequest {
    @NotBlank(message = "nickname은 필수 값입니다.")
    private String nickname;
    private String bio;
    private String image;
}
