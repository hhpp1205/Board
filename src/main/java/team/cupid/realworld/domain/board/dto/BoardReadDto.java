package team.cupid.realworld.domain.board.dto;

import lombok.*;


import java.time.LocalDateTime;
import java.util.List;

@Getter
public class BoardReadDto {
    private Long boardId;
    private String title;
    private String content;
    private String writer; // 연관관계 member table 작성자 이름 => m.name
    private String createdDate;
    private Boolean isGood; // Good table 좋아요 눌렀는지 안눌렀는지 => g.memberId가 id(jwt 토큰에서 가져온 유저 식별자)와 같고 g.boardId가 b.boardId와 같은 레코드의 isGood 값
    private Long goodCount; // Good table 레코드 갯수 => COUNT(CASE WHEN g.isGood = true THEN 1 ELSE NULL END) 쿼리를 수향하여 좋아요 여부가 true인 행을 카운팅함


}
