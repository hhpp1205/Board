package team.cupid.realworld.domain.board.dto;


import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class BoardDeleteDto {
    @NotNull
    private Long id;
}
