package team.cupid.realworld.domain.board.domain.repository;

import team.cupid.realworld.domain.board.dto.BoardReadResponseDto;

import java.util.List;
import java.util.Optional;

public interface CustomBoardRepository {

    Optional<List<BoardReadResponseDto>> findAllBoardReadDto(Long id);
}
