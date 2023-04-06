package team.cupid.realworld.domain.follow.dto;

import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SimpleFollowResponse {
    private Long followId;
}
