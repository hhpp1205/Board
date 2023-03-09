package team.cupid.realworld.member.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
public class Email {
    @javax.validation.constraints.Email (message = "이메일 형식이 아닙니다.")
    @NotBlank
    @Column(length = 30, unique = true, nullable = false)
    String email;

}
