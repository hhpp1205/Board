package team.cupid.realworld.domain.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDateTime;
import java.util.List;

@Getter
public class BoardReadDto {
    private Long boardId;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime createdDate;
    private Boolean isGood;
    private Long goodCount;

    public BoardReadDto(Long boardId, String title, String content, String writer, LocalDateTime createdDate, Boolean isGood, Long goodCount) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.createdDate = createdDate;
        this.isGood = isGood;
        this.goodCount = goodCount;
    }
}
