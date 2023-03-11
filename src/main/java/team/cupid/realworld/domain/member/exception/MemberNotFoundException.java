package team.cupid.realworld.domain.member.exception;

import javax.persistence.EntityNotFoundException;

public class MemberNotFoundException extends EntityNotFoundException {
    public MemberNotFoundException(String message) {
        super(message);
    }
}
