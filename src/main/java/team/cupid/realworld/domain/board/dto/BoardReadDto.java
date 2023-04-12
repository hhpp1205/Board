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
    private String writer; // 연관관계 member table 작성자 이름 => m.name

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdDate;
    private Boolean isGood; // Good table 좋아요 눌렀는지 안눌렀는지 => g.memberId가 id(jwt 토큰에서 가져온 유저 식별자)와 같고 g.boardId가 b.boardId와 같은 레코드의 isGood 값
    private Long goodCount; // Good table 레코드 갯수 => COUNT(CASE WHEN g.isGood = true THEN 1 ELSE NULL END) 쿼리를 수향하여 좋아요 여부가 true인 행을 카운팅함

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
