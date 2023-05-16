package team.cupid.realworld.domain.board.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PageInfoResponseDto {

    private List<BoardReadResponseDto> content;
    private Integer pageNo;
    private Integer pageSize;

    public static PageInfoResponseDto of(
            List<BoardReadResponseDto> content,
            Integer pageNo,
            Integer pageSize
    ) {
        return PageInfoResponseDto.builder()
                .content(content)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .build();
    }
}
