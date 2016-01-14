package unit;

import cgk.bibliothouris.learning.application.transferobject.ItemsListingTO;
import cgk.bibliothouris.learning.application.transferobject.MemberNameTO;
import cgk.bibliothouris.learning.application.valueobject.PaginationParams;
import cgk.bibliothouris.learning.application.valueobject.SortParams;
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
import java.util.List;

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
        when(mockRepository.getMember("unexistentUUID")).thenReturn(null);

        Member returnedMember = service.findMember("inexistentUUID");

        assertThat(returnedMember).isEqualTo(null);
    }

    @Test
    public void givenOneMember_findAllMembers_returnsTheMember() {
        ItemsListingTO expectedMemberListingTO = new ItemsListingTO();
        when(mockRepository.findAllMembers(new PaginationParams("0", "100"), "John", new SortParams("address", "desc"))).thenReturn(expectedMemberListingTO);

        ItemsListingTO memberListingTO = service.findAllMembers(new PaginationParams("0", "100"), "John", new SortParams("address", "desc"));

        assertThat(memberListingTO).isEqualTo(expectedMemberListingTO);
    }

    @Test
    public void givenOneMember_findAllMembersWithNegativePaginationParams_returnsListOfMembers() {
        ItemsListingTO expectedMemberListingTO = new ItemsListingTO();
        when(mockRepository.findAllMembers(new PaginationParams("0", "0"), "", new SortParams("lastName", "desc"))).thenReturn(expectedMemberListingTO);

        ItemsListingTO memberListingTO = service.findAllMembers(new PaginationParams("-1", "-5"), "", new SortParams("lastName", "desc"));

        verify(mockRepository).findAllMembers(new PaginationParams("0", "0"), "", new SortParams("lastName", "desc"));
        assertThat(memberListingTO).isEqualTo(expectedMemberListingTO);
    }

    @Test
    public void givenOneMember_findAllMembersWithNoPaginationParams_returnsListOfMembers() {
        ItemsListingTO expectedMemberListingTO = new ItemsListingTO();
        when(mockRepository.findAllMembers(new PaginationParams("0", "0"), "", new SortParams("lastName", "desc"))).thenReturn(expectedMemberListingTO);

        ItemsListingTO memberListingTO = service.findAllMembers(new PaginationParams("", ""), "", new SortParams("lastName", "desc"));

        verify(mockRepository).findAllMembers(new PaginationParams("0", "0"), "", new SortParams("lastName", "desc"));
        assertThat(memberListingTO).isEqualTo(expectedMemberListingTO);
    }

    @Test
    public void givenTwoNegativeParameters_findPaginationParameters_returnCorrectParameters() {
        ItemsListingTO expectedMemberListingTO = new ItemsListingTO();
        expectedMemberListingTO.setItemsCount(10L);

        PaginationParams findParams = BiblioUtilityService.findPaginationParameters(new PaginationParams("-1", "-5"), () -> expectedMemberListingTO.getItemsCount());

        assertThat(findParams.getStart()).isEqualTo("0");
        assertThat(findParams.getEnd()).isEqualTo(expectedMemberListingTO.getItemsCount().toString());
    }

    @Test
    public void givenTwoParameters_findPaginationParameters_returnCorrectParameters() {
        ItemsListingTO expectedMemberListingTO = new ItemsListingTO();
        expectedMemberListingTO.setItemsCount(7L);

        PaginationParams findParams = BiblioUtilityService.findPaginationParameters(new PaginationParams("1", "5"), () -> expectedMemberListingTO.getItemsCount());

        assertThat(findParams.getStart()).isEqualTo("1");
        assertThat(findParams.getEnd()).isEqualTo("5");
    }

    @Test
    public void givenOneMember_findAllMembersNames_returnsTheMemberNames() {
        List<MemberNameTO> expectedMembersNamesList = new ArrayList<>();
        when(mockRepository.findAllMembersNames()).thenReturn(expectedMembersNamesList);

        List<MemberNameTO> names = service.findAllMembersNames();

        assertThat(names).isEqualTo(expectedMembersNamesList);
    }
}
