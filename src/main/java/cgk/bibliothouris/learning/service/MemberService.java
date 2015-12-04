package cgk.bibliothouris.learning.service;

import cgk.bibliothouris.learning.repository.MemberRepository;
import cgk.bibliothouris.learning.service.entity.Member;
import cgk.bibliothouris.learning.service.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Service
public class MemberService {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Autowired
    private MemberRepository repository;

    public Member createMember(Member member) {
        validateMember(member);

        return repository.createMember(member);
    }

    private void validateMember(Member member) {
        Set<ConstraintViolation<Member>> bookConstraintViolations = validator.validate(member);
        if (!bookConstraintViolations.isEmpty())
            throw new ValidationException(bookConstraintViolations.iterator().next().getMessage());
    }
}
