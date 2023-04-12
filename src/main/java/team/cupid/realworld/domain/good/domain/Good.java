package team.cupid.realworld.domain.good.domain;

import team.cupid.realworld.domain.board.domain.Board;
import team.cupid.realworld.domain.member.domain.Member;
import team.cupid.realworld.global.common.BaseTimeEntity;

import javax.persistence.*;

@Entity
public class Good extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "good_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "is_good")
    private boolean isGood;
}
