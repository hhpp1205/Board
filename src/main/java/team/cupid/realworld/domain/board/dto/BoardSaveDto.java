package team.cupid.realworld.domain.board.dto;

import lombok.Getter;
import team.cupid.realworld.domain.board.domain.Board;
import team.cupid.realworld.domain.board.domain.BoardStatus;
import team.cupid.realworld.domain.board.domain.tag.Tag;
import team.cupid.realworld.domain.member.domain.Member;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
public class BoardSaveDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;

    private List<@NotBlank String> tags;
    private BoardStatus status;

    public Board toEntity(Member member) {
        return Board.builder()
                .title(title)
                .content(content)
                .member(member)
                .boardStatus(status)
                .build();
    }
}
