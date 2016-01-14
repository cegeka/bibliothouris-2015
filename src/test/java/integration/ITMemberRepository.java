package integration;

import cgk.bibliothouris.learning.application.transferobject.ItemsListingTO;
import cgk.bibliothouris.learning.application.transferobject.MemberNameTO;
import cgk.bibliothouris.learning.application.transferobject.MemberTO;
import cgk.bibliothouris.learning.config.AppConfig;
import cgk.bibliothouris.learning.repository.BookRepository;
import cgk.bibliothouris.learning.repository.BorrowHistoryRepository;
import cgk.bibliothouris.learning.repository.MemberRepository;
import cgk.bibliothouris.learning.service.entity.Member;
import fixture.MemberTestFixture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@Transactional
public class ITMemberRepository {

    @Autowired
    private MemberRepository memberRepository;

    private Member member1, member2;

    @Before
    public void setUp() {
        member1 = MemberTestFixture.createMemberWithNoUUID();
        member2 = MemberTestFixture.createMemberWithNoUUID();
    }

    @Test
    public void givenAMember_WhenWePersist_WeGetThatMember() {
        Member actualMember = memberRepository.createMember(member1);

        assertThat(actualMember.getUUID()).isNotNull();
    }

    @Test
    public void givenAMember_WhenWePersist_weCanRetrieveThatUser() {
        Member createdMember = memberRepository.createMember(member1);

        Member retrievedMember = memberRepository.getMember(createdMember.getUUID());

        assertThat(retrievedMember).isEqualTo(createdMember);
    }

    @Test
    public void givenNoMember_WhenWeRetrieve_WeGetNull() {
        Member retrievedMember = memberRepository.getMember("randomString");

        assertThat(retrievedMember).isEqualTo(null);
    }

    @Test
    public void givenTwoMembers_findMembersByFirstNameAndPaginateThem_findTheMembers(){
        member1.setFirstName("John");
        memberRepository.createMember(member1);
        member2.setFirstName("Jane");
        memberRepository.createMember(member2);

        ItemsListingTO<MemberTO> foundMemberListingTO = memberRepository.findAllMembers(0, 5, member1.getFirstName(), "firstName", "asc");

        assertThat(foundMemberListingTO.getItems().size()).isLessThanOrEqualTo(5);
        foundMemberListingTO.getItems().stream().forEach(member -> assertThat(member.getFirstName().contains(member1.getFirstName())));
    }

    @Test
    public void givenTwoMembers_findMembersByFirstName_findTheMembers(){
        member1.setFirstName("John");
        memberRepository.createMember(member1);
        member2.setFirstName("Jane");
        memberRepository.createMember(member2);

        ItemsListingTO<MemberTO> foundMemberListingTO = memberRepository.findAllMembers(0, 100, member1.getFirstName(), "", "");

        foundMemberListingTO.getItems().stream().forEach(member -> assertThat(member.getFirstName().contains(member1.getFirstName())));
    }

    @Test
    public void givenTwoMembers_findMembersByLastName_findTheMembers(){
        member1.setLastName("Doe1");
        memberRepository.createMember(member1);
        member2.setLastName("Doe2");
        memberRepository.createMember(member2);

        ItemsListingTO<MemberTO> foundMemberListingTO = memberRepository.findAllMembers(0, 100, member1.getLastName(), "", "");

        foundMemberListingTO.getItems().stream().forEach(member -> assertThat(member.getLastName().contains(member1.getLastName())));
    }

    @Test
    public void givenTwoMembers_findMembersByFirstAndLastName_findTheMembers(){
        member1.setFirstName("John");
        member1.setLastName("Doe1");
        memberRepository.createMember(member1);
        member2.setFirstName("Jane");
        member2.setLastName("Doe2");
        memberRepository.createMember(member2);
        String name = member1.getFirstName() + " " + member1.getLastName();

        ItemsListingTO<MemberTO> foundMemberListingTO = memberRepository.findAllMembers(0, 100, name, "", "");

        foundMemberListingTO.getItems().stream().forEach(member -> assertThat(member.getLastName().contains(name)));
    }

    @Test
    public void givenTwoMembers_findMembersByPartOfFirstAndLastName_findTheMembers(){
        member1.setFirstName("John");
        member1.setLastName("Doe1");
        memberRepository.createMember(member1);
        member2.setFirstName("Jane");
        member2.setLastName("Doe2");
        memberRepository.createMember(member2);
        String name = (member1.getFirstName() + " " + member1.getLastName()).substring(3);

        ItemsListingTO<MemberTO> foundMemberListingTO = memberRepository.findAllMembers(0, 100, name, "", "");

        foundMemberListingTO.getItems().stream().forEach(member -> assertThat(member.getLastName().contains(name)));
    }

    @Test
    public void givenTwoMembers_sortMembersByFirstNameAscendingly_sortTheMembers() {
        member1.setFirstName("John");
        memberRepository.createMember(member1);
        member2.setFirstName("Jane");
        memberRepository.createMember(member2);

        ItemsListingTO<MemberTO> foundMemberListingTO = memberRepository.findAllMembers(0, 100, "", "firstName", "asc");

        assertThat(foundMemberListingTO.getItems()).isSortedAccordingTo((MemberTO m1, MemberTO m2) -> m1.getFirstName().toLowerCase().compareTo(m2.getFirstName().toLowerCase()));
    }

    @Test
    public void givenTwoMembers_sortMembersByFirstNameDescendingly_sortTheMembers(){
        member1.setFirstName("John");
        memberRepository.createMember(member1);
        member2.setFirstName("Jane");
        memberRepository.createMember(member2);

        ItemsListingTO<MemberTO> foundMemberListingTO = memberRepository.findAllMembers(0, 100, "", "firstName", "desc");

        assertThat(foundMemberListingTO.getItems()).isSortedAccordingTo((MemberTO m1, MemberTO m2) -> m2.getFirstName().toLowerCase().compareTo(m1.getFirstName().toLowerCase()));
    }

    @Test
    public void givenTwoMembers_sortMembersByLastNameAscendingly_sortTheMembers() {
        member1.setLastName("Doe1");
        memberRepository.createMember(member1);
        member2.setLastName("Doe2");
        memberRepository.createMember(member2);

        ItemsListingTO<MemberTO> foundMemberListingTO = memberRepository.findAllMembers(0, 100, "", "lastName", "asc");

        assertThat(foundMemberListingTO.getItems()).isSortedAccordingTo((MemberTO m1, MemberTO m2) -> m1.getLastName().toLowerCase().compareTo(m2.getLastName().toLowerCase()));
    }

    @Test
    public void givenTwoMembers_sortMembersByLastNameDescendingly_sortTheMembers(){
        member1.setLastName("Doe1");
        memberRepository.createMember(member1);
        member2.setLastName("Doe2");
        memberRepository.createMember(member2);

        ItemsListingTO<MemberTO> foundMemberListingTO = memberRepository.findAllMembers(0, 100, "", "lastName", "desc");

        assertThat(foundMemberListingTO.getItems()).isSortedAccordingTo((MemberTO m1, MemberTO m2) -> m2.getLastName().toLowerCase().compareTo(m1.getLastName().toLowerCase()));
    }

    @Test
    public void givenTwoMembers_sortMembersByBirthDateAscendingly_sortTheMembers(){
        member1.setBirthDate(LocalDate.of(2015, Month.APRIL, 14));
        memberRepository.createMember(member1);
        member2.setBirthDate(LocalDate.of(1980, Month.APRIL, 14));
        memberRepository.createMember(member2);

        ItemsListingTO<MemberTO> foundMemberListingTO = memberRepository.findAllMembers(0, 100, "", "birthDate", "asc");

        assertThat(foundMemberListingTO.getItems()).isSortedAccordingTo((MemberTO m1, MemberTO m2) -> m1.getBirthDate().compareTo(m2.getBirthDate()));
    }

    @Test
    public void givenTwoMembers_sortMembersByBirthDateDescendingly_sortTheMembers(){
        member1.setBirthDate(LocalDate.of(2015, Month.APRIL, 14));
        memberRepository.createMember(member1);
        member2.setBirthDate(LocalDate.of(1980, Month.APRIL, 14));
        memberRepository.createMember(member2);

        ItemsListingTO<MemberTO> foundMemberListingTO = memberRepository.findAllMembers(0, 100, "", "birthDate", "desc");

        assertThat(foundMemberListingTO.getItems()).isSortedAccordingTo((MemberTO m1, MemberTO m2) -> m2.getBirthDate().compareTo(m1.getBirthDate()));
    }

    @Test
    public void givenTwoMembers_sortMembersByAddressAscendingly_sortTheMembers(){
        member1.setAddress("address1");
        memberRepository.createMember(member1);
        member2.setAddress("address2");
        memberRepository.createMember(member2);

        ItemsListingTO<MemberTO> foundMemberListingTO = memberRepository.findAllMembers(0, 100, "", "address", "asc");

        assertThat(foundMemberListingTO.getItems()).isSortedAccordingTo((MemberTO m1, MemberTO m2) -> m1.getAddress().toLowerCase().compareTo(m2.getAddress().toLowerCase()));
    }

    @Test
    public void givenTwoMembers_sortMembersByAddressDescendingly_sortTheMembers() {
        member1.setAddress("address1");
        memberRepository.createMember(member1);
        member2.setAddress("address2");
        memberRepository.createMember(member2);

        ItemsListingTO<MemberTO> foundMemberListingTO = memberRepository.findAllMembers(0, 100, "", "address", "desc");

        assertThat(foundMemberListingTO.getItems()).isSortedAccordingTo((MemberTO m1, MemberTO m2) -> m2.getAddress().toLowerCase().compareTo(m1.getAddress().toLowerCase()));
    }

    @Test
    public void givenTwoMembers_sortMembersByCityAscendingly_sortTheMembers(){
        member1.setCity("city1");
        memberRepository.createMember(member1);
        member2.setCity("city2");
        memberRepository.createMember(member2);

        ItemsListingTO<MemberTO> foundMemberListingTO = memberRepository.findAllMembers(0, 100, "", "city", "asc");

        assertThat(foundMemberListingTO.getItems()).isSortedAccordingTo((MemberTO m1, MemberTO m2) -> m1.getCity().toLowerCase().compareTo(m2.getCity().toLowerCase()));
    }

    @Test
    public void givenTwoMembers_sortMembersByCityDescendingly_sortTheMembers() {
        member1.setCity("city1");
        memberRepository.createMember(member1);
        member2.setCity("city2");
        memberRepository.createMember(member2);

        ItemsListingTO<MemberTO> foundMemberListingTO = memberRepository.findAllMembers(0, 100, "", "city", "desc");

        assertThat(foundMemberListingTO.getItems()).isSortedAccordingTo((MemberTO m1, MemberTO m2) -> m2.getCity().toLowerCase().compareTo(m1.getCity().toLowerCase()));
    }

    @Test
    public void givenTwoMembers_findAllMembersNames_returnsTheNewMembersNames() {
        member1.setFirstName("John");
        member1.setLastName("Doe1");
        memberRepository.createMember(member1);
        member2.setFirstName("Jane");
        member2.setLastName("Doe2");
        memberRepository.createMember(member2);

        List<MemberNameTO> names = memberRepository.findAllMembersNames();

        assertThat(names.contains(member1.getFirstName() + " " + member1.getLastName()));
        assertThat(names.contains(member2.getFirstName() + " " + member2.getLastName()));
    }
}
