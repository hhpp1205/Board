package team.cupid.realworld.domain.board.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.cupid.realworld.domain.board.domain.Board;

import java.util.Optional;
@Repository
public interface BoardRepository extends JpaRepository<Board, Long>, CustomBoardRepository {
}
