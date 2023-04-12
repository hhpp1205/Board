package team.cupid.realworld.domain.member.dto;

import lombok.*;
import team.cupid.realworld.domain.member.domain.Member;

import java.time.LocalDateTime;

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
    private LocalDateTime createdBy;
    private LocalDateTime lastModifiedBy;

    public static MemberResponse of(Member member) {
        return MemberResponse.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .bio(member.getBio())
                .image(member.getImage())
                .createdBy(member.getCreatedBy())
                .lastModifiedBy(member.getLastModifiedBy())
                .build();
    }
}
