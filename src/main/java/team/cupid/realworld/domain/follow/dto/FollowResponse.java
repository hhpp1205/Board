package team.cupid.realworld.domain.follow.dto;

import lombok.*;
import team.cupid.realworld.domain.follow.domain.Follow;

import java.time.LocalDateTime;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class FollowResponse {

    private String nickname;
    private String image;
    private LocalDateTime createdBy;
    private LocalDateTime lastModifiedBy;
}
