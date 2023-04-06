package team.cupid.realworld.domain.board;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.cupid.realworld.domain.board.tag.Tag;
import team.cupid.realworld.global.common.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //
    @Column(name="board_id")
    private Long id;

    private String title;

    private String description;

    private String body;

    @OneToMany(mappedBy = "board", orphanRemoval = true)//
    private List<Tag> tags = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "board_status")
//    @Builder.Default
    private BoardStatus boardStatus = BoardStatus.TEMPORARY;

    @Builder//
    private Board(Long id, String title, String description, String body, List<Tag> tags, BoardStatus boardStatus) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.body = body;
        this.tags = tags;
        this.boardStatus = boardStatus;
     }
}
