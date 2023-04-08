package team.cupid.realworld.domain.member.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.cupid.realworld.domain.board.domain.Board;
import team.cupid.realworld.domain.good.domain.Good;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(length = 50, unique = true, nullable = false, updatable = false)
    private String email;


    @Column(nullable = false)
    private String password;

    @Column(length = 12, unique = true, nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType roleType;

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Good> goods = new ArrayList<>();

    //자기소개
    @Column(length = 100)
    private String bio;

    private String image;

    @Builder
    public Member(Long memberId, String email, String password, String nickname, RoleType roleType, String bio, String image) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.roleType = roleType;
        this.bio = bio;
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Member member = (Member) o;

        return Objects.equals(memberId, member.memberId);
    }

    @Override
    public int hashCode() {
        return memberId != null ? memberId.hashCode() : 0;
    }

    public void update(Member request) {
        this.nickname = request.getNickname();
        this.bio = request.getBio();
        this.image = request.getImage();
    }
}
