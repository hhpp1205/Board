package team.cupid.realworld.domain.board.domain.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import team.cupid.realworld.domain.board.dto.BoardReadResponseDto;
import team.cupid.realworld.global.policy.RedisPolicy;

import java.util.List;
import java.util.Optional;

public interface CustomBoardRepository {

    Optional<List<BoardReadResponseDto>> searchAllBoardReadDto(Long id);

    Optional<Page<BoardReadResponseDto>> searchPageBoardReadDto(Pageable pageable);
}
