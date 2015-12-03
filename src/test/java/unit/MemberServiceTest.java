package unit;

import cgk.bibliothouris.learning.repository.MemberRepository;
import cgk.bibliothouris.learning.service.MemberService;
import cgk.bibliothouris.learning.service.entity.Member;
import cgk.bibliothouris.learning.service.exception.ValidationException;
import fixture.MemberTestFixture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MemberServiceTest {

    @InjectMocks
    private MemberService service;

    @Mock
    private MemberRepository mockRepository;


    @Test(expected = ValidationException.class)
    public void givenEmptyFirstName_whenValidated_shouldThrowException() {
        Member member = MemberTestFixture.createInvalidMemberWithNoFirstName();

        service.createMember(member);
    }

    @Test(expected = ValidationException.class)
    public void givenEmptyLastName_whenValidated_shouldThrowException() {
        Member member = MemberTestFixture.createInvalidMemberWithNoLastName();

        service.createMember(member);
    }

    @Test(expected = ValidationException.class)
    public void givenEmptyBirthDate_whenValidated_shouldThrowException() {
        Member member = MemberTestFixture.createInvalidMemberWithNoBirthDate();

        service.createMember(member);
    }

    @Test(expected = ValidationException.class)
    public void givenEmptyNationalNumber_whenValidated_shouldThrowException() {
        Member member = MemberTestFixture.createInvalidMemberWithNoNationalNumber();

         service.createMember(member);
    }
}
