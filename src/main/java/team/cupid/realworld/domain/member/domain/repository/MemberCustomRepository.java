package team.cupid.realworld.domain.member.domain.repository;

import team.cupid.realworld.global.security.principal.CustomUserDetails;

import java.util.Optional;

public interface MemberCustomRepository {
    Optional<CustomUserDetails> findUserDetailsByEmail(String email);

    Optional<CustomUserDetails> findUserDetailsById(Long id);
}
