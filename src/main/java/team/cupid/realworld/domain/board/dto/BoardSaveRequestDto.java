package team.cupid.realworld.domain.board.dto;

import lombok.Getter;
import team.cupid.realworld.domain.board.domain.Board;
import team.cupid.realworld.domain.board.domain.BoardStatus;
import team.cupid.realworld.domain.member.domain.Member;

import javax.validation.constraints.*;
import java.util.List;

@Getter
public class BoardSaveRequestDto {
    @NotBlank
    @Size(min = 3, max = 30)
    private String title;
    @NotBlank
    @Size(max = 300)
    private String content;
    @Size(min = 3, max = 10)
    private List<@NotBlank String> tags;
    @NotNull
    private BoardStatus status;

    public Board toEntity(Member member) {
        return Board.builder()
                .title(title)
                .content(content)
                .member(member)
                .boardStatus(status)
                .goodCount(0L)
                .build();
    }
}
