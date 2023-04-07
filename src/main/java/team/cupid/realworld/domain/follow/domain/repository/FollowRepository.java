package team.cupid.realworld.domain.follow.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.cupid.realworld.domain.follow.domain.Follow;
import team.cupid.realworld.domain.member.domain.Member;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

     boolean existsByFromMemberAndToMember(Member fromMember, Member toMember);
     void deleteByFromMemberAndToMember(Member fromMember, Member toMember);

    List<Follow> findByFromMember(Member fromMember);
    List<Follow> findByToMember(Member fromMember);

}
