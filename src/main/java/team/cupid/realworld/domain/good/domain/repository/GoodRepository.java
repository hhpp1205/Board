package team.cupid.realworld.domain.good.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.cupid.realworld.domain.good.domain.Good;
import team.cupid.realworld.domain.good.domain.GoodId;

import java.util.Optional;

@Repository
public interface GoodRepository extends JpaRepository<Good, GoodId> {

    Boolean existsByBoardIdAndMemberMemberId(Long BoardId, Long memberId);

    Optional<Good> findByBoardIdAndMemberMemberId(Long BoardId, Long memberId);
}
