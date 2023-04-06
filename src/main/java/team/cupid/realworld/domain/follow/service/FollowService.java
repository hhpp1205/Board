package team.cupid.realworld.domain.follow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import team.cupid.realworld.domain.follow.dto.FollowResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {


    public Long following(Long followingId, Long followerId) {
        // TODO: 2023/04/06  
        return null;
    }

    public void unFollowing(Long followingId, Long followerId) {
        // TODO: 2023/04/06  
    }

    public ResponseEntity<List<FollowResponse>> getFollowing(Long memberId) {
        // TODO: 2023/04/06  
        return null;
    }

    public ResponseEntity<List<FollowResponse>> getFollower(Long memberId) {
        // TODO: 2023/04/06  
        return null;
    }
}
