package integration;

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

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@Transactional
public class ITBookRepository {

    @Autowired
    private BookRepository bookRepository;
    private Book bookWithOneAuthor, bookWithFourAuthors, bookWithOneAuthorAndOneCategory,
                 bookWithOneAuthorAndThreeCategories;

    @Before
    public void setUp() {
        bookWithOneAuthor = BookTestFixture.createBookWithOneAuthor();
        bookWithFourAuthors = BookTestFixture.createBookWithFourAuthors();
        bookWithOneAuthorAndOneCategory = BookTestFixture.createBookWithOneAuthorAndOneCategory();
        bookWithOneAuthorAndThreeCategories = BookTestFixture.createBookWithOneAuthorAndThreeCategories();
        bookRepository.deleteAllBooks();
    }

    @Test
    public void givenANewBookWithOneAuthor_createBook_createsNewBookWithOneAuthor() {
        Book newBook = bookRepository.createBook(bookWithOneAuthor);

        assertThat(newBook.getId()).isNotNull();
    }

    @Test
    public void givenANewBookWithFourAuthors_createBook_createsNewBookWithFourAuthors() {
        Book newBook = bookRepository.createBook(bookWithFourAuthors);

        assertThat(newBook.getId()).isNotNull();
    }

    @Test
    public void givenANewBookWithOneAuthorAndOneCategory_createBook_createsNewBookWithOneAuthorAndOneCategory() {
        Book newBook = bookRepository.createBook(bookWithOneAuthorAndOneCategory);

        assertThat(newBook.getId()).isNotNull();
    }

    @Test
    public void givenANewBookWithOneAuthorAndOneCategory_createBook_createsNewBookWithOneAuthorAndThreeCategories() {
        Book newBook = bookRepository.createBook(bookWithOneAuthorAndThreeCategories);

        assertThat(newBook.getId()).isNotNull();
    }

    @Test
    public void givenOneBook_findBooks_findTheBook() {
        Book book1 = bookRepository.createBook(bookWithOneAuthor);

        List<Book> foundBooks = bookRepository.findAllBooks(0,5);

        assertThat(foundBooks.size()).isEqualTo(1);
        assertThat(foundBooks).contains(book1);
    }

    @Test
    public void givenTwoBooks_findBooks_findTwoBooks() {
        Book book1 = bookRepository.createBook(bookWithOneAuthor);
        Book book2 = bookRepository.createBook(bookWithFourAuthors);

        List<Book> foundBooks = bookRepository.findAllBooks(0,5);

        assertThat(foundBooks.size()).isEqualTo(2);
        assertThat(foundBooks).contains(book1);
        assertThat(foundBooks).contains(book2);
    }

    @Test
    public void givenOneBookWithOneAuthorAndOneCategory_findBooks_findTheBook() {
        Book book1 = bookRepository.createBook(bookWithOneAuthorAndOneCategory);

        List<Book> foundBooks = bookRepository.findAllBooks(0,5);

        assertThat(foundBooks.size()).isEqualTo(1);
        assertThat(foundBooks).contains(book1);
    }

    @Test
    public void givenOneBookWithOneAuthorAndThreeCategories_findBooks_findTheBook() {
        Book book1 = bookRepository.createBook(bookWithOneAuthorAndThreeCategories);

        List<Book> foundBooks = bookRepository.findAllBooks(0,5);

        assertThat(foundBooks.size()).isEqualTo(1);
        assertThat(foundBooks).contains(book1);
    }

    @Test
    public void givenTwoBooks_countBooks_returnBooksNumber() {
        Book book1 = bookRepository.createBook(bookWithOneAuthor);
        Book book2 = bookRepository.createBook(bookWithFourAuthors);

        Long count = bookRepository.countBooks();

        assertThat(count).isEqualTo(2);
    }

    @Test
    public void givenABookId_findBookById_returnsTheCorrectBook() {
        Book book = bookRepository.createBook(bookWithOneAuthor);

        Book foundBook = bookRepository.findBookById(book.getId());

        assertThat(foundBook).isEqualTo(book);
    }

}
