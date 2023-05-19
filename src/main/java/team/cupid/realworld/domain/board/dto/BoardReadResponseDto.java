package team.cupid.realworld.domain.board.dto;

import lombok.*;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class BoardReadResponseDto implements Serializable {
    private Long boardId;
    private String title;
    private String content;
    private List<String> tags;
    private String writer;
    private LocalDateTime createdDate;
    private Boolean isGood;
    private Long goodCount;

    @Builder
    public BoardReadResponseDto(Long boardId, String title, String content, String writer, LocalDateTime createdDate, Boolean isGood, Long goodCount) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.createdDate = createdDate;
        this.isGood = isGood == null ? false : isGood;
        this.goodCount = goodCount;
    }

    @Builder
    public BoardReadResponseDto(Long boardId, String title, String content, String writer, LocalDateTime createdDate, Long goodCount) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.createdDate = createdDate;
        this.goodCount = goodCount;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void isGood() {
        this.isGood = true;
    }

    public void isNotGood() {
        this.isGood = false;
    }
}
