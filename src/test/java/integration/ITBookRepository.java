package integration;

import cgk.bibliothouris.learning.application.transferobject.BookTO;
import cgk.bibliothouris.learning.application.transferobject.BookWithStatusTO;
import cgk.bibliothouris.learning.application.transferobject.ItemsListingTO;
import cgk.bibliothouris.learning.application.transferobject.StringTO;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@Transactional
public class ITBookRepository {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowHistoryRepository borrowRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Book bookWithOneAuthorAndOneCategory,
                 bookWithOneAuthorAndThreeCategories, bookWithFourAuthorsAndThreeCategories;


    @Before
    public void setUp() {
        bookWithOneAuthorAndOneCategory = BookTestFixture.createBookWithOneAuthorAndOneCategory();
        bookWithOneAuthorAndThreeCategories = BookTestFixture.createBookWithOneAuthorAndThreeCategories();
        bookWithFourAuthorsAndThreeCategories = BookTestFixture.createBookWithFourAuthorsAndThreeCategories();
        bookRepository.deleteAllBooks();
    }

    @Test
    public void givenANewBookWithOneAuthorAndOneCategory_createBook_createsNewBookWithOneAuthorAndOneCategory() {
        Book newBook = bookRepository.createBook(bookWithOneAuthorAndOneCategory);

        assertThat(newBook.getId()).isNotNull();
    }

    @Test
    public void givenANewBookWithOneAuthorAndThreeCategory_createBook_createsNewBookWithOneAuthorAndThreeCategories() {
        Book newBook = bookRepository.createBook(bookWithOneAuthorAndThreeCategories);

        assertThat(newBook.getId()).isNotNull();
    }

    @Test
    public void givenANewBookWithFourAuthorAndThreeCategories_createBook_createsNewBookWithFourAuthorsAndThreeCategories() {
        Book newBook = bookRepository.createBook(bookWithFourAuthorsAndThreeCategories);

        assertThat(newBook.getId()).isNotNull();
    }

    @Test
    public void givenOneBook_findBooks_findTheBook() {
        Book book1 = bookRepository.createBook(bookWithOneAuthorAndThreeCategories);
        BookWithStatusTO expectedBookTO = new BookWithStatusTO(book1);

        ItemsListingTO foundBookListingTO = bookRepository.findAllBooks(0, 5, null, null);

        assertThat(foundBookListingTO.getItemsCount()).isEqualTo(1);
        assertThat(foundBookListingTO.getItems()).contains(expectedBookTO);
    }

    @Test
    public void givenOneAvailableBookAndOneBorrowed_findAvailableBooks_findTheBook() {
        Member member = memberRepository.createMember(MemberTestFixture.createMember());
        Book book = bookRepository.createBook(bookWithOneAuthorAndThreeCategories);
        BorrowHistoryItem borrowHistoryItem = BorrowedHistoryFixture.createBorrowedHistoryItem();
        borrowHistoryItem.setBook(book);
        borrowHistoryItem.setMember(member);
        Book availableBook = bookRepository.createBook(bookWithFourAuthorsAndThreeCategories);
        BorrowHistoryItem borrowedHistory = borrowRepository.addBorrowedBook(borrowHistoryItem);
        BookTO expectedBookTO = new BookTO(availableBook);

        ItemsListingTO foundBookListingTO = bookRepository.findAllAvailableBooks(0, 5, null, null);

        assertThat(foundBookListingTO.getItemsCount()).isEqualTo(1);
        assertThat(foundBookListingTO.getItems()).contains(expectedBookTO);
    }

    @Test
    public void givenTwoAvailableBooksAndOneBorrowedBook_countAvailableBooks_returnAvailableBooksNumber() {
        Book book1 = bookRepository.createBook(bookWithOneAuthorAndOneCategory);
        Book book2 = bookRepository.createBook(bookWithFourAuthorsAndThreeCategories);
        Member member = memberRepository.createMember(MemberTestFixture.createMember());
        Book book = bookRepository.createBook(bookWithOneAuthorAndThreeCategories);
        BorrowHistoryItem borrowHistoryItem = BorrowedHistoryFixture.createBorrowedHistoryItem();
        borrowHistoryItem.setBook(book);
        borrowHistoryItem.setMember(member);
        Book availableBook = bookRepository.createBook(bookWithFourAuthorsAndThreeCategories);
        BorrowHistoryItem borrowedHistory = borrowRepository.addBorrowedBook(borrowHistoryItem);

        Long count = bookRepository.countAvailableBooks();

        assertThat(count).isEqualTo(2);
    }

    @Test
    public void givenTwoBooks_findBooks_findTwoBooks() {
        Book book1 = bookRepository.createBook(bookWithFourAuthorsAndThreeCategories);
        Book book2 = bookRepository.createBook(bookWithOneAuthorAndOneCategory);
        BookWithStatusTO expectedBookTO1 = new BookWithStatusTO(book1);
        BookWithStatusTO expectedBookTO2 = new BookWithStatusTO(book2);

        ItemsListingTO foundBookListingTO = bookRepository.findAllBooks(0, 5, null, null);

        assertThat(foundBookListingTO.getItemsCount()).isEqualTo(2);
        assertThat(foundBookListingTO.getItems()).contains(expectedBookTO1);
        assertThat(foundBookListingTO.getItems()).contains(expectedBookTO2);
    }

    @Test
    public void givenTwoBooks_findBooksFilteredByTitle_findTheCorrectBooks() {
        Book book1 = bookRepository.createBook(bookWithFourAuthorsAndThreeCategories);
        Book book2 = bookRepository.createBook(bookWithOneAuthorAndOneCategory);
        BookWithStatusTO expectedBookTO = new BookWithStatusTO(book1);

        ItemsListingTO foundBookListingTO = bookRepository.findAllBooks(0, 5, "Clean Code", null);

        assertThat(foundBookListingTO.getItemsCount()).isGreaterThanOrEqualTo(1);
        assertThat(foundBookListingTO.getItems()).contains(expectedBookTO);
    }

    @Test
    public void givenTwoBooks_findBooksFilteredByIsbn_findTheCorrectBooks() {
        Book book1 = bookRepository.createBook(bookWithFourAuthorsAndThreeCategories);
        Book book2 = bookRepository.createBook(bookWithOneAuthorAndOneCategory);
        BookWithStatusTO expectedBookTO = new BookWithStatusTO(book1);

        ItemsListingTO foundBookListingTO = bookRepository.findAllBooks(0, 5, null, book1.getIsbn());

        assertThat(foundBookListingTO.getItemsCount()).isEqualTo(2);
        assertThat(foundBookListingTO.getItems()).contains(expectedBookTO);
    }

    @Test
    public void givenTwoBooks_countBooks_returnBooksNumber() {
        Book book1 = bookRepository.createBook(bookWithOneAuthorAndOneCategory);
        Book book2 = bookRepository.createBook(bookWithFourAuthorsAndThreeCategories);

        Long count = bookRepository.countBooks();

        assertThat(count).isEqualTo(2);
    }

    @Test
    public void givenABookId_findBookById_returnsTheCorrectBook() {
        Book book = bookRepository.createBook(bookWithOneAuthorAndOneCategory);

        Book foundBook = bookRepository.findBookById(book.getId());

        assertThat(foundBook).isEqualTo(book);
    }

    @Test
    public void givenTwoBooks_getAllBookTitles_returnsTheCorrectBookTitlesList() {
        bookRepository.createBook(bookWithOneAuthorAndOneCategory);
        bookRepository.createBook(bookWithFourAuthorsAndThreeCategories);

        List<StringTO> bookTitles = bookRepository.findAllBookTitles();

        assertThat(bookTitles.size()).isEqualTo(2);
    }

    @Test
    public void givenTwoBooks_getAllBookIsbnCodes_returnsTheCorrectBookIsbnCodesList() {
        bookRepository.createBook(bookWithOneAuthorAndOneCategory);
        bookRepository.createBook(bookWithFourAuthorsAndThreeCategories);

        List<StringTO> bookIsbnCodes = bookRepository.findAllBookIsbnCodes();

        assertThat(bookIsbnCodes.size()).isEqualTo(1);
    }

}
