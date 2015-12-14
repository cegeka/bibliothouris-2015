package integration;

import cgk.bibliothouris.learning.config.AppConfig;
import cgk.bibliothouris.learning.repository.BookRepository;
import cgk.bibliothouris.learning.repository.BorrowHistoryRepository;
import cgk.bibliothouris.learning.repository.MemberRepository;
import cgk.bibliothouris.learning.service.entity.Book;
import cgk.bibliothouris.learning.service.entity.BorrowHistoryItem;
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
    private BorrowHistoryRepository borrowHistoryRepository;

    private Book bookWithOneAuthorAndOneCategory;
    private Member member;
    private BorrowHistoryItem borrowHistoryItemItem;

    @Before
    public void setUp() {
        bookWithOneAuthorAndOneCategory = BookTestFixture.createBookWithOneAuthorAndOneCategory();
        member = MemberTestFixture.createMember();
        borrowHistoryItemItem = BorrowedHistoryFixture.createHistoryItem();
    }


    @Test
    public void givenABookAMemberAndBorrowDetails_addBorrowedBook_returnsNewBorrowedHistoryItem(){
        Member newMember = memberRepository.createMember(member);
        Book newBook = bookRepository.createBook(bookWithOneAuthorAndOneCategory);

        BorrowHistoryItem persistedHistoryItem = borrowHistoryRepository.addBorrowedBook(newBook, newMember, borrowHistoryItemItem);

        assertThat(persistedHistoryItem.getId()).isNotNull();
    }

    @Test
    public void givenABookAMemberAndBorrowDetails_addBorrowedBook_persistBorrowDataIntoMemberDetails(){
        Member newMember = memberRepository.createMember(member);
        Book newBook = bookRepository.createBook(bookWithOneAuthorAndOneCategory);

        BorrowHistoryItem persistedHistoryItem = borrowHistoryRepository.addBorrowedBook(newBook, newMember, borrowHistoryItemItem);

        Member memberWithBorrowedBook = memberRepository.getMember(newMember.getUUID());
        assertThat(memberWithBorrowedBook.getHistory()).contains(persistedHistoryItem);
    }

}
