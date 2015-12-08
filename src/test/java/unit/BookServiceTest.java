package unit;

import cgk.bibliothouris.learning.application.transferobject.BookListingTO;
import cgk.bibliothouris.learning.application.transferobject.BookTitleTO;
import cgk.bibliothouris.learning.repository.BookRepository;
import cgk.bibliothouris.learning.service.BookService;
import cgk.bibliothouris.learning.service.entity.Book;
import cgk.bibliothouris.learning.service.exception.ValidationException;
import fixture.BookTestFixture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

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
        List<BookListingTO> listOfBooks = new ArrayList<>();
        Mockito.when(mockRepository.findAllBooks(0,5)).thenReturn(listOfBooks);

        List<BookListingTO> books = service.findAllBooks("0","5");

        assertThat(books).isEqualTo(listOfBooks);
    }

    @Test
    public void givenOneBook_findAllBooksWithNegativeParams_returnsListOfBooks() {
        List<BookListingTO> listOfBooks = new ArrayList<>();
        Mockito.when(mockRepository.findAllBooks(-1,-3)).thenReturn(listOfBooks);

        List<BookListingTO> books = service.findAllBooks("-1","-3");

        assertThat(books).isEqualTo(listOfBooks);
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
        Mockito.when(mockRepository.findAllBookTitles()).thenReturn(Arrays.asList(new BookTitleTO(book.getTitle())));

        List<BookTitleTO> foundBookTitles = service.findAllBookTitles();

        assertThat(foundBookTitles.get(0).getTitle()).isEqualTo(book.getTitle());
    }

}
