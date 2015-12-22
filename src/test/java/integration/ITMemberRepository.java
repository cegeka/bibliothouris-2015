package integration;

import cgk.bibliothouris.learning.application.transferobject.MemberListingTO;
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

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@Transactional
public class ITMemberRepository {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BorrowHistoryRepository borrowedHistoryRepository;

    @Autowired
    private BookRepository bookRepository;

    private Member memberWithNoUUID;

    @Before
    public void setUp() {
        memberWithNoUUID = MemberTestFixture.createMemberWithNoUUID();
    }

    @Test
    public void givenAMember_WhenWePersist_WeGetThatMember() {
        Member actualMember = memberRepository.createMember(memberWithNoUUID);

        assertThat(actualMember.getUUID()).isNotNull();
    }

    @Test
    public void givenAMember_WhenWePersist_weCanRetrieveThatUser() {
        Member createdMember = memberRepository.createMember(memberWithNoUUID);

        Member retrievedMember = memberRepository.getMember(createdMember.getUUID());

        assertThat(retrievedMember).isEqualTo(createdMember);
    }

    @Test
    public void givenNoMember_WhenWeRetrieve_WeGetNull() {
        Member retrievedMember = memberRepository.getMember("randomString");

        assertThat(retrievedMember).isEqualTo(null);
    }

    @Test
    public void givenTwoMembers_findMembers_findTwoMembers(){
        Member createdMember1 = memberRepository.createMember(MemberTestFixture.createMemberWithNoUUID());
        Member createdMember2 = memberRepository.createMember(MemberTestFixture.createMemberWithNoUUID());
        MemberTO expectedMemberTO1 = new MemberTO(createdMember1);
        MemberTO expectedMemberTO2 = new MemberTO(createdMember2);

        MemberListingTO foundMemberListingTO = memberRepository.findAllMembers(0, 100, "firstName", "asc");

        assertThat(foundMemberListingTO.getMembers()).contains(expectedMemberTO1);
        assertThat(foundMemberListingTO.getMembers()).contains(expectedMemberTO2);
    }

}
