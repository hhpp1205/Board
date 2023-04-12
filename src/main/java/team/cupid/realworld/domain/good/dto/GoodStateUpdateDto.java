package team.cupid.realworld.domain.good.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class GoodStateUpdateDto {

    @NotBlank
    private Long id;
}
