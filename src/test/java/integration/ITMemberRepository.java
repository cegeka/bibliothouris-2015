package integration;

import cgk.bibliothouris.learning.config.AppConfig;
import cgk.bibliothouris.learning.repository.MemberRepository;
import cgk.bibliothouris.learning.service.entity.Member;
import fixture.MemberTestFixture;
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

    @Test
    public void givenAMember_WhenWePersist_WeGetThatMember() {
        Member memberWithNoUUID = MemberTestFixture.createMemberWithNoUUID();

        Member actualMember = memberRepository.createMember(memberWithNoUUID);

        assertThat(actualMember.getUUID()).isNotNull();
    }

}
