package team.cupid.realworld.domain.board.dto;

import lombok.*;


import java.time.LocalDateTime;
import java.util.List;

@Getter
public class BoardReadDto {
    private Long boardId;
    private String title;
    private String content;
    private List<TagDto> tags;
    private String writer;
    private LocalDateTime createdDate;
    private Boolean isGood;
    private Long goodCount;

    @Builder
    public BoardReadDto(Long boardId, String title, String content, List<TagDto> tags, String writer, LocalDateTime createdDate, Boolean isGood, Long goodCount) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.tags = tags;
        this.writer = writer;
        this.createdDate = createdDate;
        this.isGood = isGood == null ? false : isGood;
        this.goodCount = goodCount;
    }
}
