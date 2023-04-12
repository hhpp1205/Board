package team.cupid.realworld.domain.board.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
public class TestDto {
    private Long boardId;
    private String title;
    private String content;
    private Long memberId;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createTime;

    @Builder
    public TestDto(Long boardId, String title, String content, Long memberId, LocalDateTime createTime) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.memberId = memberId;
        this.createTime = createTime;
    }
}
