package unit;

import cgk.bibliothouris.learning.application.transferobject.BookBorrowerTO;
import cgk.bibliothouris.learning.application.transferobject.BookFilterValueTO;
import cgk.bibliothouris.learning.application.transferobject.BookListingTO;
import cgk.bibliothouris.learning.repository.BookRepository;
import cgk.bibliothouris.learning.service.BookService;
import cgk.bibliothouris.learning.service.entity.Book;
import cgk.bibliothouris.learning.service.exception.ValidationException;
import fixture.BookTestFixture;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @InjectMocks
    private BookService service;

    @Mock
    private BookRepository mockRepository;

    @Test
    public void givenAValidBook_createBook_returnsTheNewBook() {
        Book book = BookTestFixture.createBookWithOneAuthorAndOneCategory();
        Mockito.when(mockRepository.createBook(book)).thenReturn(book);

        Book newBook = service.createBook(book);

        assertThat(newBook).isEqualTo(book);
    }

    @Test(expected = ValidationException.class)
    public void givenABookWithoutISBN_createBook_throwsValidationException() {
        Book book = BookTestFixture.createBookWithoutISBN();

        service.createBook(book);
    }

    @Test(expected = ValidationException.class)
    public void givenABookWith10DigitsISBN_createBook_throwsValidationException() {
        Book book = BookTestFixture.createBookWith10DigitsISBN();

        service.createBook(book);
    }

    @Test(expected = ValidationException.class)
    public void givenABookWith976FirstDigitsISBN_createBook_throwsValidationException() {
        Book book = BookTestFixture.createBookWith976FirstDigitsISBN();

        service.createBook(book);
    }

    @Test(expected = ValidationException.class)
    public void givenABookWith5LinesISBN_createBook_throwsValidationException() {
        Book book = BookTestFixture.createBookWith5LinesISBN();

        service.createBook(book);
    }

    @Test(expected = ValidationException.class)
    public void givenABookWithoutTitle_createBook_throwsValidationException() {
        Book book = BookTestFixture.createBookWithoutTitle();

        service.createBook(book);
    }

    @Test(expected = ValidationException.class)
    public void givenABookWithOneAuthorWithoutLastName_createBook_throwsValidationException() {
        Book book = BookTestFixture.createBookWithOneAuthorWithoutLastName();

        service.createBook(book);
    }

    @Test(expected = ValidationException.class)
    public void givenABookWithOneAuthorWithoutCategory_createBook_throwsValidationException() {
        Book book = BookTestFixture.createBookWithoutCategory();

        service.createBook(book);
    }

    @Test(expected = ValidationException.class)
    public void givenABookWithOneAuthorWithNegativePagesNumber_createBook_throwsValidationException() {
        Book book = BookTestFixture.createBookWithNegativePageNumber();

        service.createBook(book);
    }

    @Test
    public void givenOneBook_findAllBooks_returnsTheBook() {
        BookListingTO expectedBookListingTO = new BookListingTO();
        Mockito.when(mockRepository.findAllBooks(0, 5, null, null)).thenReturn(expectedBookListingTO);

        BookListingTO bookListingTO = service.findAllBooks("0", "5", null, null);

        assertThat(bookListingTO).isEqualTo(expectedBookListingTO);
    }

    @Test
    public void givenOneAvailableBook_findAllAvailableBooks_returnsTheAvailableBook() {
        BookListingTO expectedBookListingTO = new BookListingTO();
        Mockito.when(mockRepository.findAllAvailableBooks(0, 5, null, null)).thenReturn(expectedBookListingTO);

        BookListingTO bookListingTO = service.findAllAvailableBooks("0", "5", null, null);

        assertThat(bookListingTO).isEqualTo(expectedBookListingTO);
    }

    @Test
    public void givenOneAvailableBook_findAllBooksWithNegativeParams_returnsListOfBooks() {
        BookListingTO expectedBookListingTO = new BookListingTO();
        Mockito.when(mockRepository.findAllAvailableBooks(0, 0, null, null)).thenReturn(expectedBookListingTO);

        BookListingTO bookListingTO = service.findAllAvailableBooks("-1", "-3", null, null);

        assertThat(bookListingTO).isEqualTo(expectedBookListingTO);
    }

    @Test
    public void givenOneBook_findAllBooksWithNegativeParams_returnsListOfBooks() {
        BookListingTO expectedBookListingTO = new BookListingTO();
        Mockito.when(mockRepository.findAllBooks(0, 0, null, null)).thenReturn(expectedBookListingTO);

        BookListingTO bookListingTO = service.findAllBooks("-1", "-3", null, null);

        assertThat(bookListingTO).isEqualTo(expectedBookListingTO);
    }

    @Test
    public void givenOneBook_findAllBooksWithAGivenTitle_returnsListOfBooks() {
        BookListingTO expectedBookListingTO = new BookListingTO();
        Mockito.when(mockRepository.findAllBooks(0, 5, "Clean Code", null)).thenReturn(expectedBookListingTO);

        BookListingTO bookListingTO = service.findAllBooks("0", "5", "Clean Code", null);

        assertThat(bookListingTO).isEqualTo(expectedBookListingTO);
    }

    @Test
    public void givenOneBook_findAllBooksWithAGivenIsbn_returnsListOfBooks() {
        BookListingTO expectedBookListingTO = new BookListingTO();
        Mockito.when(mockRepository.findAllBooks(0, 5, null, "978-0-13-235088-4")).thenReturn(expectedBookListingTO);

        BookListingTO bookListingTO = service.findAllBooks("0", "5", null, "978-0-13-235088-4");

        assertThat(bookListingTO).isEqualTo(expectedBookListingTO);
    }

    @Test
    public void givenABookId_findBookById_returnsTheCorrectBook() {
        Book book = BookTestFixture.createBookWithOneAuthorAndOneCategory();
        Mockito.when(mockRepository.findBookById(book.getId())).thenReturn(book);

        Book foundBook = service.findBookById(book.getId());

        assertThat(foundBook).isEqualTo(book);
    }

    @Test
    public void givenOneBook_findAllBookTitles_returnsTheCorrectBookTitle() {
        Book book = BookTestFixture.createBookWithOneAuthorAndOneCategory();
        Mockito.when(mockRepository.findAllBookTitles()).thenReturn(Arrays.asList(new BookFilterValueTO(book.getTitle())));

        List<BookFilterValueTO> foundBookTitles = service.findAllBookTitles();

        assertThat(foundBookTitles.get(0).getValue()).isEqualTo(book.getTitle());
    }

    @Test
    public void givenOneBook_findAllBookIsbnCodes_returnsTheCorrectBookIsbnCodes() {
        Book book = BookTestFixture.createBookWithOneAuthorAndOneCategory();
        Mockito.when(mockRepository.findAllBookIsbnCodes()).thenReturn(Arrays.asList(new BookFilterValueTO(book.getIsbn())));

        List<BookFilterValueTO> foundBookIsbnCodes = service.findAllBookIsbnCodes();

        assertThat(foundBookIsbnCodes.get(0).getValue()).isEqualTo(book.getIsbn());
    }

    @Test(expected = ValidationException.class)
    public void givenAnInvalidBookId_whenRetrieveBorrowerDetails_throwsValidationException() {
        service.findBookBorrowerDetails(-1);
    }

    @Test
    public void givenABookId_whenRetrieveBorrowerDetails_returnsTheCorrectDetails() {
        BookBorrowerTO expectedBookBorrowerTO = new BookBorrowerTO();
        Mockito.when(mockRepository.findBookById(1)).thenReturn(new Book());
        Mockito.when(mockRepository.findBookBorrowerDetails(1)).thenReturn(expectedBookBorrowerTO);

        BookBorrowerTO foundBookBorrowerTO = service.findBookBorrowerDetails(1);

        Assertions.assertThat(foundBookBorrowerTO).isEqualTo(expectedBookBorrowerTO);
    }



}
