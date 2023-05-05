package team.cupid.realworld.domain.good.dto;

import lombok.Getter;
import team.cupid.realworld.domain.board.domain.Board;
import team.cupid.realworld.domain.good.domain.Good;
import team.cupid.realworld.domain.member.domain.Member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class GoodStateDto {

    @NotNull
    private Long id;

    public Good toEntity(Board board, Member member) {
        return Good.builder()
                .board(board)
                .member(member)
                .isGood(true)
                .build();
    }
}
