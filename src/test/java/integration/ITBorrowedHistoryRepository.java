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
    private BorrowHistoryRepository borrowHistoryRepository;

    @Autowired
    private BookRepository bookRepository;

    private BorrowHistoryItem borrowHistoryItem;

    @Before
    public void setUp() {
        Book book = bookRepository.createBook(BookTestFixture.createBookWithOneAuthorAndOneCategory());
        Member member = memberRepository.createMember(MemberTestFixture.createMember());

        borrowHistoryItem = BorrowedHistoryFixture.createHistoryItem();
        borrowHistoryItem.setBook(book);
        borrowHistoryItem.setMember(member);
    }


    @Test
    public void givenABookAMemberAndBorrowDetails_addBorrowedBook_returnsNewBorrowedHistoryItem(){
        BorrowHistoryItem persistedHistoryItem = borrowHistoryRepository.addBorrowedBook(borrowHistoryItem);

        assertThat(persistedHistoryItem.getId()).isNotNull();
    }

    @Test
    public void givenABookAMemberAndBorrowDetails_addBorrowedBook_persistBorrowDataIntoMemberDetails(){
        BorrowHistoryItem persistedHistoryItem = borrowHistoryRepository.addBorrowedBook(borrowHistoryItem);

        Member memberWithBorrowedBook = memberRepository.getMember(persistedHistoryItem.getMember().getUUID());
        assertThat(memberWithBorrowedBook.getHistory()).contains(persistedHistoryItem);
    }

}
