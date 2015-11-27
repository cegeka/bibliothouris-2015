package unit;

import cgk.bibliothouris.learning.application.resource.BookResource;
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
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class BookResourceTest {

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

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.BAD_REQUEST);
    }

    @Test
    public void givenAValidBook_createBook_returns200OK() {
        Book book = BookTestFixture.createBookWithOneAuthor();
        Mockito.when(mockBookService.createBook(book)).thenReturn(book);

        Response response = bookResource.createBook(book);

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
    }

    @Test
    public void givenAValidBook_createBook_returnsCorrectBook() {
        Book book = BookTestFixture.createBookWithOneAuthor();
        Mockito.when(mockBookService.createBook(book)).thenReturn(book);

        Response response = bookResource.createBook(book);

        assertThat(response.getEntity()).isEqualTo(book);
    }

    @Test
    public void givenAValidBook_createBook_returnsLinkToTheNewBook() {
        Book book = BookTestFixture.createBookWithOneAuthor();
        book.setId(1);
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
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
    }

    @Test
    public void givenAListOfBooks_findAllBooks_returnCorrectEntity() {
        List<Book> books = new ArrayList<>();
        books.add(new Book());
        Mockito.when(mockBookService.findAllBooks("0","5")).thenReturn(books);

        Response response = bookResource.getAllBooks(Integer.toString(0),Integer.toString(5));

        Mockito.verify(mockBookService, times(1)).findAllBooks("0","5");
        assertThat(response.getEntity()).isEqualTo(books);
    }

    @Test
    public void givenAnEmptyListOfBooks_findAllBooks_return404NotFound() {
        List<Book> books = new ArrayList<>();
        Mockito.when(mockBookService.findAllBooks("0","5")).thenReturn(books);

        Response response = bookResource.getAllBooks(Integer.toString(0),Integer.toString(5));

        Mockito.verify(mockBookService, times(1)).findAllBooks("0","5");
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.NOT_FOUND);
    }
}