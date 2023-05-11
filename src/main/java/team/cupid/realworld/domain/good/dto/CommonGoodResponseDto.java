package team.cupid.realworld.domain.good.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CommonGoodResponseDto {
    private Boolean isGood;

    public static CommonGoodResponseDto of(Boolean isGood) {
        return CommonGoodResponseDto.builder()
                .isGood(isGood)
                .build();
    }
}
