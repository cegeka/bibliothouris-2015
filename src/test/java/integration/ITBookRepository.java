package integration;

import cgk.bibliothouris.learning.application.transferobject.BookFilterValueTO;
import cgk.bibliothouris.learning.application.transferobject.BookListingTO;
import cgk.bibliothouris.learning.application.transferobject.BookTO;
import cgk.bibliothouris.learning.config.AppConfig;
import cgk.bibliothouris.learning.repository.BookRepository;
import cgk.bibliothouris.learning.service.entity.Book;
import fixture.BookTestFixture;
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
        BookTO expectedBookTO = new BookTO(book1);

        BookListingTO foundBookListingTO = bookRepository.findAllBooks(0, 5, null, null);

        assertThat(foundBookListingTO.getBooksCount()).isEqualTo(1);
        assertThat(foundBookListingTO.getBooks()).contains(expectedBookTO);
    }

    @Test
    public void givenTwoBooks_findBooks_findTwoBooks() {
        Book book1 = bookRepository.createBook(bookWithFourAuthorsAndThreeCategories);
        Book book2 = bookRepository.createBook(bookWithOneAuthorAndOneCategory);
        BookTO expectedBookTO1 = new BookTO(book1);
        BookTO expectedBookTO2 = new BookTO(book2);

        BookListingTO foundBookListingTO = bookRepository.findAllBooks(0, 5, null, null);

        assertThat(foundBookListingTO.getBooksCount()).isEqualTo(2);
        assertThat(foundBookListingTO.getBooks()).contains(expectedBookTO1);
        assertThat(foundBookListingTO.getBooks()).contains(expectedBookTO2);
    }

    @Test
    public void givenTwoBooks_findBooksFilteredByTitle_findTheCorrectBooks() {
        Book book1 = bookRepository.createBook(bookWithFourAuthorsAndThreeCategories);
        Book book2 = bookRepository.createBook(bookWithOneAuthorAndOneCategory);
        BookTO expectedBookTO = new BookTO(book1);

        BookListingTO foundBookListingTO = bookRepository.findAllBooks(0, 5, "Clean Code", null);

        assertThat(foundBookListingTO.getBooksCount()).isEqualTo(1);
        assertThat(foundBookListingTO.getBooks()).contains(expectedBookTO);
    }

    @Test
    public void givenTwoBooks_findBooksFilteredByIsbn_findTheCorrectBooks() {
        Book book1 = bookRepository.createBook(bookWithFourAuthorsAndThreeCategories);
        Book book2 = bookRepository.createBook(bookWithOneAuthorAndOneCategory);
        BookTO expectedBookTO = new BookTO(book1);

        BookListingTO foundBookListingTO = bookRepository.findAllBooks(0, 5, null, book1.getIsbn());

        assertThat(foundBookListingTO.getBooksCount()).isEqualTo(2);
        assertThat(foundBookListingTO.getBooks()).contains(expectedBookTO);
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

        List<BookFilterValueTO> bookTitles = bookRepository.findAllBookTitles();

        assertThat(bookTitles.size()).isEqualTo(2);
    }

    @Test
    public void givenTwoBooks_getAllBookIsbnCodes_returnsTheCorrectBookIsbnCodesList() {
        bookRepository.createBook(bookWithOneAuthorAndOneCategory);
        bookRepository.createBook(bookWithFourAuthorsAndThreeCategories);

        List<BookFilterValueTO> bookIsbnCodes = bookRepository.findAllBookIsbnCodes();

        assertThat(bookIsbnCodes.size()).isEqualTo(1);
    }

}
