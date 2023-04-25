package team.cupid.realworld.domain.board.domain;

import lombok.*;
import team.cupid.realworld.domain.board.domain.tag.BoardTag;
import team.cupid.realworld.domain.good.domain.Good;
import team.cupid.realworld.domain.board.domain.tag.Tag;
import team.cupid.realworld.domain.member.domain.Member;
import team.cupid.realworld.global.common.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_id")
    private Long id;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardTag> boardTags = new ArrayList<>();

    @OneToMany(mappedBy = "board", orphanRemoval = true)
    private List<Good> goods = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "board_status")
    private BoardStatus boardStatus = BoardStatus.TEMPORARY;

    @Column(name = "good_count")
    private Long goodCount;

    @Builder
    private Board(Long id, String title, String content, Member member, List<BoardTag> boardTags, List<Good> goods, BoardStatus boardStatus, Long goodCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.member = member;
        this.boardTags = boardTags;
        this.goods = goods;
        this.boardStatus = boardStatus;
        this.goodCount = goodCount;
     }

     public void update(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
     }
}
