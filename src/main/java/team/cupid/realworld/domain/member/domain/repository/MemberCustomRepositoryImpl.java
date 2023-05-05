package team.cupid.realworld.domain.member.domain.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.cupid.realworld.global.security.principal.CustomUserDetails;

import java.util.Optional;

import static team.cupid.realworld.domain.member.domain.QMember.member;


@Repository
@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository{

    private final JPAQueryFactory query;

    @Override
    public Optional<CustomUserDetails> findUserDetailsByEmail(String email) {
        return Optional.ofNullable(
                query.select(Projections.constructor(CustomUserDetails.class,
                                member.memberId,
                                member.email,
                                member.password,
                                member.roleType))
                        .from(member)
                        .where(member.email.eq(email))
                        .fetchOne());
    }

    @Override
    public Optional<CustomUserDetails> findUserDetailsById(Long memberId) {
        return Optional.ofNullable(
                query.select(Projections.constructor(CustomUserDetails.class,
                                member.memberId,
                                member.email,
                                member.password,
                                member.roleType))
                        .from(member)
                        .where(member.memberId.eq(memberId))
                        .fetchOne());
    }
}
