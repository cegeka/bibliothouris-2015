package unit;

import cgk.bibliothouris.learning.repository.MemberRepository;
import cgk.bibliothouris.learning.service.MemberService;
import cgk.bibliothouris.learning.service.entity.Member;
import cgk.bibliothouris.learning.service.exception.ValidationException;
import fixture.MemberTestFixture;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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

    @Test
    public void givenValidMember_whenValidated_shouldReturnMember() {
        Member member = MemberTestFixture.createMember();
        Mockito.when(mockRepository.createMember(member)).thenReturn(member);

        Member createdMember = service.createMember(member);

        Assertions.assertThat(member).isEqualTo(createdMember);
    }

    @Test
    public void givenAValidMember_afterGet_shouldReturnThatMember() {
        Member member = MemberTestFixture.createMember();
        Mockito.when(mockRepository.getMember(member.getUUID())).thenReturn(member);

        Member returnedMember = service.findMember(member.getUUID());

        Assertions.assertThat(returnedMember).isEqualTo(member);
    }

    @Test
    public void givenANonExistentUser_afterGet_shouldReturnNull() {
        Mockito.when(mockRepository.getMember("inexistentUUID")).thenReturn(null);

        Member returnedMember = service.findMember("inexistentUUID");

        Assertions.assertThat(returnedMember).isEqualTo(null);
    }


}
