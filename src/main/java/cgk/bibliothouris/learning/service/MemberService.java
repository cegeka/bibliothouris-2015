package cgk.bibliothouris.learning.service;

import cgk.bibliothouris.learning.application.transferobject.BookListingTO;
import cgk.bibliothouris.learning.application.transferobject.MemberListingTO;
import cgk.bibliothouris.learning.repository.MemberRepository;
import cgk.bibliothouris.learning.service.entity.Member;
import cgk.bibliothouris.learning.service.exception.ValidationException;
import org.glassfish.grizzly.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Service
@Transactional
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

    @Transactional(readOnly = true)
    public Member findMember(String uuid) {
        return repository.getMember(uuid);
    }

    @Transactional(readOnly = true)
    public MemberListingTO findAllMembers(String start, String end, String sort, String order) {
        Pair<Integer, Integer> paginationParams = BiblioService.findPaginationParameters(start, end, () -> countMembers());

        return repository.findAllMembers(paginationParams.getFirst(), paginationParams.getSecond(), sort, order);
    }

    @Transactional(readOnly = true)
    public Long countMembers() {
        return repository.countMembers();
    }
}
