package team.cupid.realworld.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class MemberUpdateRequest {
    @NotBlank
    private String nickname;
    private String bio;
    private String image;
}
