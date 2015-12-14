package integration;

import cgk.bibliothouris.learning.config.AppConfig;
import cgk.bibliothouris.learning.repository.BookRepository;
import cgk.bibliothouris.learning.repository.MemberRepository;
import cgk.bibliothouris.learning.service.entity.Book;
import cgk.bibliothouris.learning.service.entity.BorrowedHistory;
import cgk.bibliothouris.learning.service.entity.Member;
import fixture.BookTestFixture;
import fixture.BorrowedHistoryFixture;
import fixture.MemberTestFixture;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.AssertTrue;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@Transactional
public class ITMemberRepository {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void givenAMember_WhenWePersist_WeGetThatMember() {
        Member memberWithNoUUID = MemberTestFixture.createMemberWithNoUUID();

        Member actualMember = memberRepository.createMember(memberWithNoUUID);

        assertThat(actualMember.getUUID()).isNotNull();
    }

    @Test
    public void givenABookAndMember_persist_getBorrowedBook(){
        Member memberWithNoUUID = MemberTestFixture.createMemberWithNoUUID();

        Member actualMember = memberRepository.createMember(memberWithNoUUID);
        Book bookWithOneAuthorAndOneCategory = BookTestFixture.createBookWithOneAuthorAndOneCategory();
        Book newBook = bookRepository.createBook(bookWithOneAuthorAndOneCategory);
        BorrowedHistory borrowedHistoryBuild = BorrowedHistoryFixture.createHistoryItem();
        BorrowedHistory borrowedHistory = memberRepository.addBorrowedBook(newBook, actualMember, borrowedHistoryBuild);

        boolean isBorrowedHistoryAdded = false;
        assertThat(borrowedHistory.getId()).isNotNull();
        for(BorrowedHistory borrowedHistory1 : actualMember.getHistory()) {
            if(borrowedHistory.getId().equals(borrowedHistory1.getId())) {
                isBorrowedHistoryAdded = true;
            }
        }

        assertThat(isBorrowedHistoryAdded).isTrue();
    }

}
