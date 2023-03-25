package team.cupid.realworld.domain.member.dto;

import lombok.*;
import team.cupid.realworld.domain.member.domain.Member;

import javax.validation.constraints.NotBlank;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MemberUpdateRequest {
    @NotBlank
    private String nickname;
    private String bio;
    private String image;

    public Member toEntity() {
        return Member.builder()
                .nickname(this.nickname)
                .bio(this.bio)
                .image(this.image)
                .build();
    }
}
