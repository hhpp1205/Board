package team.cupid.realworld.domain.board.dto;


import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class BoardDeleteDto {
    @NotBlank
    private Long id;
}
