package cgk.bibliothouris.learning.service;

import cgk.bibliothouris.learning.application.transferobject.ItemsListingTO;
import cgk.bibliothouris.learning.application.transferobject.MemberNameTO;
import cgk.bibliothouris.learning.application.transferobject.MemberTO;
import cgk.bibliothouris.learning.application.valueobject.PaginationParams;
import cgk.bibliothouris.learning.application.valueobject.SortParams;
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
import java.util.List;
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
    public ItemsListingTO<MemberTO> findAllMembers(PaginationParams pagination, String name, SortParams sortParams) {
        PaginationParams paginationParams = BiblioUtilityService.findPaginationParameters(pagination, () -> countMembers());

        return repository.findAllMembers(paginationParams, name, sortParams);
    }

    @Transactional(readOnly = true)
    public Long countMembers() {
        return repository.countMembers();
    }

    @Transactional(readOnly = true)
    public List<MemberNameTO> findAllMembersNames() {
        return repository.findAllMembersNames();
    }
}
