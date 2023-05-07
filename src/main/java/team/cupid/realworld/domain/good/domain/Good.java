package team.cupid.realworld.domain.good.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.cupid.realworld.domain.board.domain.Board;
import team.cupid.realworld.domain.member.domain.Member;
import team.cupid.realworld.global.common.BaseTimeEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(GoodId.class)
public class Good extends BaseTimeEntity{
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "is_good")
    private boolean isGood;

    @Builder
    public Good(Board board, Member member, boolean isGood) {
        this.board = board;
        this.member = member;
        this.isGood = isGood;
    }

    public static Good of(Board board, Member member) {
        return Good.builder()
                .board(board)
                .member(member)
                .isGood(true)
                .build();
    }

    public void updateGood(boolean isGood) {
        this.isGood = isGood;
    }
}
