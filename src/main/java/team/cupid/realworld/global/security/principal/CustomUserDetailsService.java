package team.cupid.realworld.global.security.principal;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import team.cupid.realworld.domain.member.exception.MemberNotFoundException;
import team.cupid.realworld.domain.member.domain.repository.MemberRepository;
import team.cupid.realworld.global.error.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return memberRepository.findUserDetailsById(Long.valueOf(id))
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }

}
