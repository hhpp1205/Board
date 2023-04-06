package team.cupid.realworld.domain.member.dto;

import lombok.*;
import team.cupid.realworld.domain.member.domain.Member;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MemberResponse {

    private String email;
    private String nickname;
    private String bio;
    private String image;
    private boolean isFollow;
    public static MemberResponse of(Member member) {
        return MemberResponse.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .bio(member.getBio())
                .image(member.getImage())
                .build();
    }
}
