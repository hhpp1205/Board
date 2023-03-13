package team.cupid.realworld.domain.member.service;

import org.springframework.stereotype.Service;
import team.cupid.realworld.domain.member.dto.MemberResponse;
import team.cupid.realworld.domain.member.dto.MemberUpdateRequest;
import team.cupid.realworld.domain.member.dto.SignUpRequest;

import java.util.List;

@Service
public class MemberService {
    public void create(SignUpRequest request) {
        // TODO: 2023/03/13  
    }

    public MemberResponse findById(Long memberId) {
        // TODO: 2023/03/13
        return null;
    }

    public List<MemberResponse> findAll() {
        // TODO: 2023/03/13
        return null;
    }

    public void update(Long memberId, MemberUpdateRequest request) {
        // TODO: 2023/03/13
    }

    public void delete(Long memberId) {
        // TODO: 2023/03/13
    }
}
