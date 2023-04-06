package team.cupid.realworld.domain.board.dto;

import lombok.Getter;
import team.cupid.realworld.domain.board.domain.tag.Tag;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
public class BoardSaveDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotBlank
    private List<Tag> tag;
    @NotBlank
    private String status;
}
