package team.cupid.realworld.domain.board.domain.tag;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import team.cupid.realworld.domain.board.domain.Board;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardTagRepository extends JpaRepository<BoardTag, BoardTagId> {

    Optional<List<BoardTag>> findAllByBoardId(Long boardId);

    Boolean existsByBoardAndTag(Board board, Tag tag);

    @Modifying
    @Query(value = "delete from board_tag where board_id = :boardId and tag_id = :tagId", nativeQuery = true)
    void deleteById(@Param("boardId") Long boardId, @Param("tagId") Long tagId);
}