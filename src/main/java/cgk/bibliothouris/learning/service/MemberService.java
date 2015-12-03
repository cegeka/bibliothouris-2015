package cgk.bibliothouris.learning.service;

import cgk.bibliothouris.learning.service.entity.Member;
import cgk.bibliothouris.learning.service.exception.ValidationException;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Service
public class MemberService {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public Member createMember(Member member) {
        validateMember(member);

        return null;
    }

    private void validateMember(Member member) {
        Set<ConstraintViolation<Member>> bookConstraintViolations = validator.validate(member);
        if (!bookConstraintViolations.isEmpty())
            throw new ValidationException(bookConstraintViolations.iterator().next().getMessage());
    }
}
