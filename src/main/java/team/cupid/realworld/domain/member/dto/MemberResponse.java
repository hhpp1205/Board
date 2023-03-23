package team.cupid.realworld.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberResponse {

    private String email;
    private String nickname;
    private String bio;
    private String image;
}
