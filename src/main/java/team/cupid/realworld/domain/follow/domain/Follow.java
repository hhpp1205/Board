package team.cupid.realworld.domain.follow.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.cupid.realworld.domain.member.domain.Member;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"following", "follower"})})
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;

    @ManyToOne
    @JoinColumn(name = "following")
    private Member fromMember;

    @ManyToOne
    @JoinColumn(name = "follower")
    private Member toMember;


    private Follow(Member following, Member follower) {
        this.fromMember = following;
        this.toMember = follower;
    }

    public static Follow of(Member fromMember, Member toMember) {
        return new Follow(fromMember, toMember);
    }


}
