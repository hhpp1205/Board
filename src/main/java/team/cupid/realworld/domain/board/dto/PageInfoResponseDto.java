package team.cupid.realworld.domain.board.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PageInfoResponseDto {

    private List<BoardReadResponseDto> content;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalPage;
    private Long totalElement;

    public static PageInfoResponseDto of(
            List<BoardReadResponseDto> content,
            Page<BoardReadResponseDto> page
    ) {
        return PageInfoResponseDto.builder()
                .content(content)
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalPage(page.getTotalPages())
                .totalElement(page.getTotalElements())
                .build();
    }
}
