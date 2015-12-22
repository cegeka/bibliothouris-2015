package integration;

import cgk.bibliothouris.learning.application.transferobject.BookBorrowerTO;
import cgk.bibliothouris.learning.application.transferobject.BookListingTO;
import cgk.bibliothouris.learning.application.transferobject.DetailedBorrowHistoryTO;
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

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
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
        BorrowHistoryItem borrowHistoryItem = BorrowedHistoryFixture.createBorrowedHistoryItem();
        borrowHistoryItem = buildBorrowHistoryIem(borrowHistoryItem);

        BorrowHistoryItem persistedHistoryItem = borrowHistoryRepository.addBorrowedBook(borrowHistoryItem);

        assertThat(persistedHistoryItem.getId()).isNotNull();
    }

    @Test
    public void givenABookAMemberAndBorrowDetails_addBorrowedBook_persistBorrowDataIntoMemberDetails(){
        BorrowHistoryItem borrowHistoryItem = BorrowedHistoryFixture.createBorrowedHistoryItem();
        borrowHistoryItem = buildBorrowHistoryIem(borrowHistoryItem);

        BorrowHistoryItem persistedHistoryItem = borrowHistoryRepository.addBorrowedBook(borrowHistoryItem);

        Member memberWithBorrowedBook = memberRepository.getMember(persistedHistoryItem.getMember().getUUID());
        assertThat(memberWithBorrowedBook.getHistory()).contains(persistedHistoryItem);
    }

    @Test
    public void givenAMemberId_countBorrowedBooks_returnsTheCorrectListOfBorrowedBookTOs(){
        BorrowHistoryItem borrowHistoryItem = BorrowedHistoryFixture.createBorrowedHistoryItem();
        BorrowHistoryItem persistedHistoryItem = borrowHistoryRepository.addBorrowedBook(buildBorrowHistoryIem(borrowHistoryItem));

        Long memberBorrowHistoryTOsSize = borrowHistoryRepository.countBorrowedBooksByMember(persistedHistoryItem.getMember().getUUID());

        assertThat(memberBorrowHistoryTOsSize).isEqualTo(1);
    }

    @Test
    public void givenAMemberId_findBorrowedBooks_returnsTheCorrectListOfBorrowedBookTOs(){
        BorrowHistoryItem borrowHistoryItem = BorrowedHistoryFixture.createBorrowedHistoryItem();
        BorrowHistoryItem persistedHistoryItem = borrowHistoryRepository.addBorrowedBook(buildBorrowHistoryIem(borrowHistoryItem));
        MemberBorrowHistoryTO memberBorrowHistoryTO = new MemberBorrowHistoryTO(persistedHistoryItem);

        List<MemberBorrowHistoryTO> memberBorrowHistoryTOs = borrowHistoryRepository.findBorrowedBooksByMember(persistedHistoryItem.getMember().getUUID(), 0, 10);

        assertThat(memberBorrowHistoryTOs).contains(memberBorrowHistoryTO);
    }

    @Test
    public void givenAPopulatedBorrowHistory_whenWeRetrieveIt_WeGetThatHistory(){
        BorrowHistoryItem borrowHistoryItem = BorrowedHistoryFixture.createBorrowedHistoryItem();
        BorrowHistoryItem persistedHistoryItem = borrowHistoryRepository.addBorrowedBook(buildBorrowHistoryIem(borrowHistoryItem));
        DetailedBorrowHistoryTO transformedPersistedItem = new DetailedBorrowHistoryTO(persistedHistoryItem);
        transformedPersistedItem.setDueDate(borrowHistoryItem.getStartDate().plusDays(BorrowHistoryRepository.ALLOWED_BORROW_DAYS_NUMBER.longValue()));
        transformedPersistedItem.setOverdue(transformedPersistedItem.getDueDate().until(LocalDate.now(), ChronoUnit.DAYS));

        List<DetailedBorrowHistoryTO> borrowedBooks = borrowHistoryRepository.getBorrowedBooks(0, 1000, "title", "asc");

        assertThat(borrowedBooks).contains(transformedPersistedItem);
    }

    @Test
    public void givenABookId_whenWeRetrieveBorrowerDetails_WeGetTheCorrectDetails(){
        BorrowHistoryItem borrowHistoryItem = BorrowedHistoryFixture.createBorrowedHistoryItem();
        BorrowHistoryItem persistedHistoryItem = borrowHistoryRepository.addBorrowedBook(buildBorrowHistoryIem(borrowHistoryItem));

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

    @Test
    public void givenBorrowedBooks_countBorrowedBooks_returnsTheCorrectNumberOfBorrowedBooks(){
        BorrowHistoryItem borrowHistoryItem = BorrowedHistoryFixture.createBorrowedHistoryItem();
        BorrowHistoryItem persistedHistoryItem = borrowHistoryRepository.addBorrowedBook(buildBorrowHistoryIem(borrowHistoryItem));

        Long memberBorrowHistoryTOsSize = borrowHistoryRepository.countBorrowedBooks();

        assertThat(memberBorrowHistoryTOsSize).isGreaterThan(1);
    }

    @Test
    public void givenOverdueBooks_findOverdueBooks_returnsTheCorrectListOfOverdueBooks(){
        BorrowHistoryItem borrowHistoryItem = BorrowedHistoryFixture.createOverdueHistoryItem();
        BorrowHistoryItem persistedHistoryItem = borrowHistoryRepository.addBorrowedBook(buildBorrowHistoryIem(borrowHistoryItem));
        DetailedBorrowHistoryTO detailedBorrowHistoryTO = new DetailedBorrowHistoryTO(persistedHistoryItem);
        detailedBorrowHistoryTO.setDueDate(borrowHistoryItem.getStartDate().plusDays(BorrowHistoryRepository.ALLOWED_BORROW_DAYS_NUMBER.longValue()));
        detailedBorrowHistoryTO.setOverdue(detailedBorrowHistoryTO.getDueDate().until(LocalDate.now(), ChronoUnit.DAYS));

        BookListingTO<DetailedBorrowHistoryTO> overdueBooks = borrowHistoryRepository.getOverdueBooks(0, 1000, "title", "asc");

        assertThat(overdueBooks.getBooks()).contains(detailedBorrowHistoryTO);
    }

    @Test
    public void givenOverdueBooks_countOverdueBooks_returnsTheCorrectNumberOfOverdueBooks(){
        BorrowHistoryItem borrowHistoryItem = BorrowedHistoryFixture.createOverdueHistoryItem();
        BorrowHistoryItem persistedHistoryItem = borrowHistoryRepository.addBorrowedBook(buildBorrowHistoryIem(borrowHistoryItem));

        Long memberBorrowHistoryTOsSize = borrowHistoryRepository.countOverdueBooks();

        assertThat(memberBorrowHistoryTOsSize).isGreaterThan(1);
    }

    private BorrowHistoryItem buildBorrowHistoryIem(BorrowHistoryItem borrowHistoryItem) {
        Book book = bookRepository.createBook(BookTestFixture.createBookWithOneAuthorAndOneCategory());
        Member member = memberRepository.createMember(MemberTestFixture.createMember());

        borrowHistoryItem.setBook(book);
        borrowHistoryItem.setMember(member);

        return borrowHistoryItem;
    }
}
