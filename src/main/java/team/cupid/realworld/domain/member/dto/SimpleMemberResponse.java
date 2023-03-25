package team.cupid.realworld.domain.member.dto;

import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SimpleMemberResponse {
    private Long memberId;
}
