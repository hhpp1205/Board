package team.cupid.realworld.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SimpleMemberResponse {
    private Long memberId;
    private boolean result;
}
