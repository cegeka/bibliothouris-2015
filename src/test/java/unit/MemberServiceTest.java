package unit;

import cgk.bibliothouris.learning.application.transferobject.ItemsListingTO;
import cgk.bibliothouris.learning.repository.MemberRepository;
import cgk.bibliothouris.learning.service.BiblioUtilityService;
import cgk.bibliothouris.learning.service.MemberService;
import cgk.bibliothouris.learning.service.entity.Member;
import cgk.bibliothouris.learning.service.exception.ValidationException;
import fixture.MemberTestFixture;
import org.glassfish.grizzly.utils.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        when(mockRepository.createMember(member)).thenReturn(member);

        Member createdMember = service.createMember(member);

        assertThat(member).isEqualTo(createdMember);
    }

    @Test
    public void givenAValidMember_afterGet_shouldReturnThatMember() {
        Member member = MemberTestFixture.createMember();
        when(mockRepository.getMember(member.getUUID())).thenReturn(member);

        Member returnedMember = service.findMember(member.getUUID());

        assertThat(returnedMember).isEqualTo(member);
    }

    @Test
    public void givenANonExistentUser_afterGet_shouldReturnNull() {
        when(mockRepository.getMember("inexistentUUID")).thenReturn(null);

        Member returnedMember = service.findMember("inexistentUUID");

        assertThat(returnedMember).isEqualTo(null);
    }

    @Test
    public void givenOneMember_findAllMembers_returnsTheMember() {
        ItemsListingTO expectedMemberListingTO = new ItemsListingTO();
        when(mockRepository.findAllMembers(0, 100, "", "")).thenReturn(expectedMemberListingTO);

        ItemsListingTO memberListingTO = service.findAllMembers("0","100", "", "");

        assertThat(memberListingTO).isEqualTo(expectedMemberListingTO);
    }

    @Test
    public void givenOneMember_findAllMembersWithNegativeParams_returnsListOfMembers() {
        ItemsListingTO expectedMemberListingTO = new ItemsListingTO();
        when(mockRepository.findAllMembers(0, 0, "lastName", "desc")).thenReturn(expectedMemberListingTO);

        ItemsListingTO memberListingTO = service.findAllMembers("-1", "-5", "lastName", "desc");

        verify(mockRepository).findAllMembers(0, 0, "lastName", "desc");
        assertThat(memberListingTO).isEqualTo(expectedMemberListingTO);
    }

    @Test
    public void givenTwoNegativeParameters_findPaginationParameters_returnCorrectParameters() {
        ItemsListingTO expectedMemberListingTO = new ItemsListingTO();
        expectedMemberListingTO.setItems(new ArrayList<>());
        expectedMemberListingTO.setItemsCount(0L);
        Pair<Integer, Integer> findParams = BiblioUtilityService.findPaginationParameters("-1", "-5", () -> expectedMemberListingTO.getItemsCount());

        assertThat(findParams.getFirst()).isEqualTo(0);
        assertThat(findParams.getSecond()).isEqualTo((int)(long)(expectedMemberListingTO.getItemsCount()));
    }

    @Test
    public void givenTwoParameters_findPaginationParameters_returnCorrectParameters() {
        ItemsListingTO expectedMemberListingTO = new ItemsListingTO();
        expectedMemberListingTO.setItems(new ArrayList<>());
        expectedMemberListingTO.setItemsCount(7L);
        Pair<Integer, Integer> findParams = BiblioUtilityService.findPaginationParameters("1", "5", () -> expectedMemberListingTO.getItemsCount());

        assertThat(findParams.getFirst()).isEqualTo(1);
        assertThat(findParams.getSecond()).isEqualTo(5);
    }


}
