package team.cupid.realworld.domain.follow.dto;

import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class FollowResponse {

    private String nickname;
    private String image;
}
