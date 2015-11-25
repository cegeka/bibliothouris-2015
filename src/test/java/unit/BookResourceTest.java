package unit;

import cgk.bibliothouris.learning.application.resource.BookResource;
import cgk.bibliothouris.learning.service.BookService;
import cgk.bibliothouris.learning.service.entity.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class BookResourceTest {

    @InjectMocks
    BookResource bookResource;

    @Mock
    BookService mockService;

    @Test
    public void givenAListOfBooks_findAllBooks_return200OKResponse() {
        List<Book> books = new ArrayList<>();
        books.add(new Book());
        Mockito.when(mockService.findAllBooks()).thenReturn(books);

        Response response = bookResource.getAllBooks();

        Mockito.verify(mockService, times(1)).findAllBooks();
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
    }

    @Test
    public void givenAListOfBooks_findAllBooks_returnCorrectEntity() {
        List<Book> books = new ArrayList<>();
        books.add(new Book());
        Mockito.when(mockService.findAllBooks()).thenReturn(books);

        Response response = bookResource.getAllBooks();

        Mockito.verify(mockService, times(1)).findAllBooks();
        assertThat(response.getEntity()).isEqualTo(books);
    }

    @Test
    public void givenAnEmptyListOfBooks_findAllBooks_return404NotFound() {
        List<Book> books = new ArrayList<>();
        Mockito.when(mockService.findAllBooks()).thenReturn(books);

        Response response = bookResource.getAllBooks();

        Mockito.verify(mockService, times(1)).findAllBooks();
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.NOT_FOUND);
    }
}