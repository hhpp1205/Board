package team.cupid.realworld.domain.good.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import team.cupid.realworld.domain.board.domain.Board;
import team.cupid.realworld.domain.good.domain.Good;

@Getter
@Builder
@AllArgsConstructor
public class CommonGoodResponseDto {

    private Long boardId;
    private Boolean isGood;

    public static CommonGoodResponseDto of(Board board, Good good) {
        return CommonGoodResponseDto.builder()
                .boardId(board.getId())
                .isGood(good.isGood())
                .build();
    }
}
