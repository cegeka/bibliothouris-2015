package integration;

import cgk.bibliothouris.learning.config.AppConfig;
import cgk.bibliothouris.learning.repository.BookRepository;
import cgk.bibliothouris.learning.repository.BorrowedHistoryRepository;
import cgk.bibliothouris.learning.repository.MemberRepository;
import cgk.bibliothouris.learning.service.entity.Book;
import cgk.bibliothouris.learning.service.entity.BorrowedHistory;
import cgk.bibliothouris.learning.service.entity.Member;
import fixture.BookTestFixture;
import fixture.BorrowedHistoryFixture;
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
public class ITBorrowedHistoryRepository {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowedHistoryRepository borrowedHistoryRepository;

    private Book bookWithOneAuthorAndOneCategory;
    private Member member;
    private BorrowedHistory borrowedHistoryItem;

    @Before
    public void setUp() {
        bookWithOneAuthorAndOneCategory = BookTestFixture.createBookWithOneAuthorAndOneCategory();
        member = MemberTestFixture.createMember();
        borrowedHistoryItem = BorrowedHistoryFixture.createHistoryItem();
    }


    @Test
    public void givenABookAMemberAndBorrowDetails_addBorrowedBook_returnsNewBorrowedHistoryItem(){
        Member newMember = memberRepository.createMember(member);
        Book newBook = bookRepository.createBook(bookWithOneAuthorAndOneCategory);

        BorrowedHistory persistedHistoryItem = borrowedHistoryRepository.addBorrowedBook(newBook, newMember, borrowedHistoryItem);

        assertThat(persistedHistoryItem.getId()).isNotNull();
    }

    @Test
    public void givenABookAMemberAndBorrowDetails_addBorrowedBook_persistBorrowDataIntoMemberDetails(){
        Member newMember = memberRepository.createMember(member);
        Book newBook = bookRepository.createBook(bookWithOneAuthorAndOneCategory);

        BorrowedHistory persistedHistoryItem = borrowedHistoryRepository.addBorrowedBook(newBook, newMember, borrowedHistoryItem);

        Member memberWithBorrowedBook = memberRepository.getMember(newMember.getUUID());
        assertThat(memberWithBorrowedBook.getHistory()).contains(persistedHistoryItem);
    }

}
