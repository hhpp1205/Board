package team.cupid.realworld.member.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(length = 50, unique = true, nullable = false, updatable = false, insertable = false)
    private String email;


    @Column(nullable = false)
    private String password;

    @Column(length = 12, unique = true, nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType roleType;

    //자기소개
    @Column(length = 100)
    private String bio;

    private String image;

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
}
