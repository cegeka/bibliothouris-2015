package unit;

import cgk.bibliothouris.learning.application.resource.BookResource;
import cgk.bibliothouris.learning.application.transferobject.BookTitleTO;
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

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class BookResourceTest {

    private final static Integer BOOK_ID = 1;

    @InjectMocks
    private BookResource bookResource;

    @Mock
    private BookService mockBookService;

    @Mock
    private UriInfo mockUriInfo;

    @Test
    public void givenAnInvalidBook_createBook_returns400BADREQUEST() {
        Book book = BookTestFixture.createBookWithoutTitle();
        Mockito.when(mockBookService.createBook(book)).thenThrow(ValidationException.class);

        Response response = bookResource.createBook(book);

        assertThat(response.getStatusInfo()).isEqualTo(Status.BAD_REQUEST);
    }

    @Test
    public void givenAValidBook_createBook_returns200OK() {
        Book book = BookTestFixture.createBookWithOneAuthorAndOneCategory();
        Mockito.when(mockBookService.createBook(book)).thenReturn(book);

        Response response = bookResource.createBook(book);

        assertThat(response.getStatusInfo()).isEqualTo(Status.OK);
    }

    @Test
    public void givenAValidBook_createBook_returnsCorrectBook() {
        Book book = BookTestFixture.createBookWithOneAuthorAndOneCategory();
        Mockito.when(mockBookService.createBook(book)).thenReturn(book);

        Response response = bookResource.createBook(book);

        assertThat(response.getEntity()).isEqualTo(book);
    }

    @Test
    public void givenAValidBook_createBook_returnsLinkToTheNewBook() {
        Book book = BookTestFixture.createBookWithOneAuthorAndOneCategory();
        book.setId(BOOK_ID);
        Mockito.when(mockBookService.createBook(book)).thenReturn(book);
        Mockito.when(mockUriInfo.getAbsolutePath()).thenReturn(URI.create("http://localhost:8080/webapi/books"));

        Response response = bookResource.createBook(book);

        assertThat(response.getLocation()).isEqualTo(URI.create("http://localhost:8080/webapi/books/1"));
    }

    @Test
    public void givenAListOfBooks_findAllBooks_return200OKResponse() {
        List<Book> books = new ArrayList<>();
        books.add(new Book());
        Mockito.when(mockBookService.findAllBooks("0","5")).thenReturn(books);

        Response response = bookResource.getAllBooks(Integer.toString(0),Integer.toString(5));

        Mockito.verify(mockBookService, times(1)).findAllBooks("0","5");
        assertThat(response.getStatusInfo()).isEqualTo(Status.OK);
    }

    @Test
    public void givenAListOfBooks_findAllBooksWithoutParams_returnCorrectEntity() {
        List<Book> books = new ArrayList<>();
        books.add(new Book());
        Mockito.when(mockBookService.findAllBooks("","")).thenReturn(books);

        Response response = bookResource.getAllBooks("","");

        Mockito.verify(mockBookService, times(1)).findAllBooks("","");
        assertThat(response.getEntity()).isEqualTo(books);
    }

    @Test
    public void givenAnEmptyListOfBooks_findAllBooks_return404NotFound() {
        List<Book> books = new ArrayList<>();
        Mockito.when(mockBookService.findAllBooks("0","5")).thenReturn(books);

        Response response = bookResource.getAllBooks(Integer.toString(0),Integer.toString(5));

        Mockito.verify(mockBookService, times(1)).findAllBooks("0","5");
        assertThat(response.getStatusInfo()).isEqualTo(Status.NOT_FOUND);
    }

    @Test
    public void givenAWrongBookId_getBookById_returns404NOTFOUND() {
        Mockito.when(mockBookService.findBookById(BOOK_ID)).thenReturn(null);

        Response response = bookResource.getBook(BOOK_ID);

        assertThat(response.getStatusInfo()).isEqualTo(Status.NOT_FOUND);
        assertThat(response.getEntity()).isEqualTo(null);
    }

    @Test
    public void givenAValidBookId_getBookById_returns200OK() {
        Mockito.when(mockBookService.findBookById(BOOK_ID)).thenReturn(new Book());

        Response response = bookResource.getBook(BOOK_ID);

        assertThat(response.getStatusInfo()).isEqualTo(Status.OK);
    }

    @Test
    public void givenAValidBookId_getBookById_returnsCorrectBook() {
        Book expectedBook = new Book();
        Mockito.when(mockBookService.findBookById(BOOK_ID)).thenReturn(expectedBook);

        Response response = bookResource.getBook(BOOK_ID);

        assertThat(response.getEntity()).isEqualTo(expectedBook);
    }

    @Test
    public void givenAnEmptyListOfBookTitles_getBookTitles_returns404NotFound() {
        List<BookTitleTO> bookTitles = new ArrayList<>();
        Mockito.when(mockBookService.findAllBookTitles()).thenReturn(bookTitles);

        Response response = bookResource.getBookTitles();

        assertThat(response.getStatusInfo()).isEqualTo(Status.NOT_FOUND);
    }

    @Test
    public void givenAListOfBookTitles_getBookTitles_returns200OK() {
        List<BookTitleTO> bookTitles = Arrays.asList(new BookTitleTO("Clean Code"));
        Mockito.when(mockBookService.findAllBookTitles()).thenReturn(bookTitles);

        Response response = bookResource.getBookTitles();

        assertThat(response.getStatusInfo()).isEqualTo(Status.OK);
    }

    @Test
    public void givenAListOfBookTitles_getBookTitles_returnsTheCorrectListOfBookTitles() {
        List<BookTitleTO> bookTitles = Arrays.asList(new BookTitleTO("Clean Code"));
        Mockito.when(mockBookService.findAllBookTitles()).thenReturn(bookTitles);

        Response response = bookResource.getBookTitles();

        assertThat(response.getEntity()).isEqualTo(bookTitles);
    }
}