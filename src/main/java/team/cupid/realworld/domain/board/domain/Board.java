package team.cupid.realworld.domain.board.domain;

import lombok.*;
import team.cupid.realworld.domain.board.domain.like.Like;
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

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Column(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "board", orphanRemoval = true)
    private List<Tag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "board", orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "board_status")
    private BoardStatus boardStatus = BoardStatus.TEMPORARY;

    @Builder
    private Board(Long id, String title, String content, List<Tag> tags, List<Like> likes, BoardStatus boardStatus) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.tags = tags;
        this.likes = likes;
        this.boardStatus = boardStatus;
     }
}
