package integration;

import cgk.bibliothouris.learning.application.transferobject.BookBorrowerTO;
import cgk.bibliothouris.learning.application.transferobject.GlobalBorrowHistoryTO;
import cgk.bibliothouris.learning.application.transferobject.MemberBorrowHistoryTO;
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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Test
    public void givenABookAMemberAndBorrowDetails_addBorrowedBook_returnsNewBorrowedHistoryItem(){
        BorrowHistoryItem borrowHistoryItem = buildBorrowHistoryIem();

        BorrowHistoryItem persistedHistoryItem = borrowHistoryRepository.addBorrowedBook(borrowHistoryItem);

        assertThat(persistedHistoryItem.getId()).isNotNull();
    }

    @Test
    public void givenABookAMemberAndBorrowDetails_addBorrowedBook_persistBorrowDataIntoMemberDetails(){
        BorrowHistoryItem borrowHistoryItem = buildBorrowHistoryIem();

        BorrowHistoryItem persistedHistoryItem = borrowHistoryRepository.addBorrowedBook(borrowHistoryItem);

        Member memberWithBorrowedBook = memberRepository.getMember(persistedHistoryItem.getMember().getUUID());
        assertThat(memberWithBorrowedBook.getHistory()).contains(persistedHistoryItem);
    }

    @Test
    public void givenAMemberId_countBorrowedBooks_returnsTheCorrectListOfBorrowedBookTOs(){
        BorrowHistoryItem persistedHistoryItem = borrowHistoryRepository.addBorrowedBook(buildBorrowHistoryIem());

        Long memberBorrowHistoryTOsSize = borrowHistoryRepository.countBorrowedBooksByMember(persistedHistoryItem.getMember().getUUID());

        assertThat(memberBorrowHistoryTOsSize).isEqualTo(1);
    }

    @Test
    public void givenAMemberId_findBorrowedBooks_returnsTheCorrectListOfBorrowedBookTOs(){
        BorrowHistoryItem persistedHistoryItem = borrowHistoryRepository.addBorrowedBook(buildBorrowHistoryIem());

        List<MemberBorrowHistoryTO> memberBorrowHistoryTOs = borrowHistoryRepository.findBorrowedBooksByMember(persistedHistoryItem.getMember().getUUID(), 0, 10);

        assertThat(memberBorrowHistoryTOs.size()).isEqualTo(1);
    }

    @Test
    public void givenAPopulatedBorrowHistory_whenWeRetrieveIt_WeGetThatHistory(){
        BorrowHistoryItem persistedHistoryItem = borrowHistoryRepository.addBorrowedBook(buildBorrowHistoryIem());
        GlobalBorrowHistoryTO transformedPersistedItem = new GlobalBorrowHistoryTO(persistedHistoryItem);

        List<GlobalBorrowHistoryTO> borrowedBooks = borrowHistoryRepository.getBorrowedBooks();

        assertThat(borrowedBooks).contains(transformedPersistedItem);
    }

    @Test
    public void givenABookId_whenWeRetrieveBorrowerDetails_WeGetTheCorrectDetails(){
        BorrowHistoryItem persistedHistoryItem = borrowHistoryRepository.addBorrowedBook(buildBorrowHistoryIem());

        BookBorrowerTO bookBorrowerTO = bookRepository.findBookBorrowerDetails(persistedHistoryItem.getBook().getId());

        assertThat(bookBorrowerTO.getIsBorrowed()).isTrue();
        assertThat(bookBorrowerTO.getUuid()).isEqualTo(persistedHistoryItem.getMember().getUUID());
    }

    @Test
    public void givenABookId_whenWeRetrieveBorrowerDetails_WeFindOutThatTheBookIsAvailable(){
        Book book = bookRepository.createBook(BookTestFixture.createBookWithOneAuthorAndOneCategory());

        BookBorrowerTO bookBorrowerTO = bookRepository.findBookBorrowerDetails(book.getId());

        assertThat(bookBorrowerTO.getIsBorrowed()).isFalse();
    }

    private BorrowHistoryItem buildBorrowHistoryIem() {
        Book book = bookRepository.createBook(BookTestFixture.createBookWithOneAuthorAndOneCategory());
        Member member = memberRepository.createMember(MemberTestFixture.createMember());

        BorrowHistoryItem borrowHistoryItem = BorrowedHistoryFixture.createBorrowedHistoryItem();
        borrowHistoryItem.setBook(book);
        borrowHistoryItem.setMember(member);

        return borrowHistoryItem;
    }

}
