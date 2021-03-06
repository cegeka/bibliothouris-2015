package unit;

import cgk.bibliothouris.learning.application.resource.BookResource;
import cgk.bibliothouris.learning.application.transferobject.BookBorrowerTO;
import cgk.bibliothouris.learning.application.transferobject.BookTO;
import cgk.bibliothouris.learning.application.transferobject.ItemsListingTO;
import cgk.bibliothouris.learning.application.transferobject.StringTO;
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
import java.io.IOException;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

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
        ItemsListingTO bookListingTO = new ItemsListingTO();
        bookListingTO.setItems(Arrays.asList(new BookTO()));
        Mockito.when(mockBookService.findAllBooks("0", "5", null, null)).thenReturn(bookListingTO);

        Response response = bookResource.getAllBooks(Integer.toString(0), Integer.toString(5), null, null);

        Mockito.verify(mockBookService, times(1)).findAllBooks("0", "5", null, null);
        assertThat(response.getStatusInfo()).isEqualTo(Status.OK);
    }

    @Test
    public void givenAListOfBooks_findAllAvailableBooks_return200OKResponse() {
        ItemsListingTO bookListingTO = new ItemsListingTO();
        bookListingTO.setItems(Arrays.asList(new BookTO()));
        Mockito.when(mockBookService.findAllAvailableBooks("0", "5", null, null)).thenReturn(bookListingTO);

        Response response = bookResource.getAllAvailableBooks(Integer.toString(0), Integer.toString(5), null, null);

        Mockito.verify(mockBookService, times(1)).findAllAvailableBooks("0", "5", null, null);
        assertThat(response.getStatusInfo()).isEqualTo(Status.OK);
    }

    @Test
    public void givenAListOfBooks_findAllBooksWithoutParams_returnCorrectEntity() {
        ItemsListingTO bookListingTO = new ItemsListingTO();
        bookListingTO.setItems(Arrays.asList(new BookTO()));
        Mockito.when(mockBookService.findAllBooks("", "", null, null)).thenReturn(bookListingTO);

        Response response = bookResource.getAllBooks("", "", null, null);

        Mockito.verify(mockBookService, times(1)).findAllBooks("", "", null, null);
        assertThat(response.getEntity()).isEqualTo(bookListingTO);
    }

    @Test
    public void givenAListOfBooks_findAllAvailableBooksWithoutParams_returnCorrectEntity() {
        ItemsListingTO bookListingTO = new ItemsListingTO();
        bookListingTO.setItems(Arrays.asList(new BookTO()));
        Mockito.when(mockBookService.findAllAvailableBooks("", "", null, null)).thenReturn(bookListingTO);

        Response response = bookResource.getAllAvailableBooks("", "", null, null);

        Mockito.verify(mockBookService, times(1)).findAllAvailableBooks("", "", null, null);
        assertThat(response.getEntity()).isEqualTo(bookListingTO);
    }

    @Test
    public void givenAnEmptyListOfBooks_findAllBooks_return404NotFound() {
        ItemsListingTO bookListingTO = new ItemsListingTO();
        bookListingTO.setItems(new ArrayList<>());
        Mockito.when(mockBookService.findAllBooks("0", "5", null, null)).thenReturn(bookListingTO);

        Response response = bookResource.getAllBooks(Integer.toString(0),Integer.toString(5), null, null);

        Mockito.verify(mockBookService, times(1)).findAllBooks("0", "5", null, null);
        assertThat(response.getStatusInfo()).isEqualTo(Status.NOT_FOUND);
    }

    @Test
    public void givenAnEmptyListOfBooks_findAllAvailableBooks_return404NotFound() {
        ItemsListingTO bookListingTO = new ItemsListingTO();
        bookListingTO.setItems(new ArrayList<>());
        when(mockBookService.findAllAvailableBooks("0", "5", null, null)).thenReturn(bookListingTO);

        Response response = bookResource.getAllAvailableBooks(Integer.toString(0),Integer.toString(5), null, null);

        verify(mockBookService, times(1)).findAllAvailableBooks("0", "5", null, null);
        assertThat(response.getStatusInfo()).isEqualTo(Status.NOT_FOUND);
    }

    @Test
    public void givenAListOfImportedBooks_getAllImportedBooks_returnTheListOfImportedBooks() throws IOException, GeneralSecurityException {
        List<Book> listOfImportedBooks = new ArrayList<>();
        listOfImportedBooks.add(BookTestFixture.createBookWithOneAuthorAndOneCategory());
        when(mockBookService.importContent("refactoring", null)).thenReturn(listOfImportedBooks);

        Response response = bookResource.getAllImportedBooks("refactoring", null);

        verify(mockBookService, times(1)).importContent("refactoring", null);
        assertThat(response.getEntity()).isEqualTo(listOfImportedBooks);
    }

    @Test
    public void givenAnEmptyListOfImportedBooks_getAllImportedBooks_return404NotFound() throws IOException, GeneralSecurityException {
        List<Book> listOfImportedBooks = new ArrayList<>();
        when(mockBookService.importContent("refactoring", null)).thenReturn(listOfImportedBooks);

        Response response = bookResource.getAllImportedBooks("refactoring", null);

        verify(mockBookService, times(1)).importContent("refactoring", null);
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
        List<StringTO> bookTitles = new ArrayList<>();
        Mockito.when(mockBookService.findAllBookTitles()).thenReturn(bookTitles);

        Response response = bookResource.getBookTitles();

        assertThat(response.getStatusInfo()).isEqualTo(Status.NOT_FOUND);
    }

    @Test
    public void givenAListOfBookTitles_getBookTitles_returns200OK() {
        List<StringTO> bookTitles = Arrays.asList(new StringTO("Clean Code"));
        Mockito.when(mockBookService.findAllBookTitles()).thenReturn(bookTitles);

        Response response = bookResource.getBookTitles();

        assertThat(response.getStatusInfo()).isEqualTo(Status.OK);
    }

    @Test
    public void givenAListOfBookTitles_getBookTitles_returnsTheCorrectListOfBookTitles() {
        List<StringTO> bookTitles = Arrays.asList(new StringTO("Clean Code"));
        Mockito.when(mockBookService.findAllBookTitles()).thenReturn(bookTitles);

        Response response = bookResource.getBookTitles();

        assertThat(response.getEntity()).isEqualTo(bookTitles);
    }

    @Test
    public void givenAnEmptyListOfBookIsbnCodes_getBookIsbnCodes_returns404NotFound() {
        List<StringTO> bookIsbnCodes = new ArrayList<>();
        Mockito.when(mockBookService.findAllBookIsbnCodes()).thenReturn(bookIsbnCodes);

        Response response = bookResource.getBookIsbnCodes();

        assertThat(response.getStatusInfo()).isEqualTo(Status.NOT_FOUND);
    }

    @Test
    public void givenAListOfBookIsbnCodes_getBookIsbnCodes_returns200OK() {
        List<StringTO> bookIsbnCodes = Arrays.asList(new StringTO("978-0-13-235088-4"));
        Mockito.when(mockBookService.findAllBookIsbnCodes()).thenReturn(bookIsbnCodes);

        Response response = bookResource.getBookIsbnCodes();

        assertThat(response.getStatusInfo()).isEqualTo(Status.OK);
    }

    @Test
    public void givenAListOfBookIsbnCodes_getBookIsbnCodes_returnsTheCorrectListOfBookIsbnCodes() {
        List<StringTO> bookIsbnCodes = Arrays.asList(new StringTO("978-0-13-235088-4"));
        Mockito.when(mockBookService.findAllBookIsbnCodes()).thenReturn(bookIsbnCodes);

        Response response = bookResource.getBookIsbnCodes();

        assertThat(response.getEntity()).isEqualTo(bookIsbnCodes);
    }

    @Test
    public void givenABookId_getBookBorrowerDetails_returns200OK() {
        BookBorrowerTO bookBorrowerTO = new BookBorrowerTO();
        Mockito.when(mockBookService.findBookBorrowerDetails(1)).thenReturn(bookBorrowerTO);

        Response response = bookResource.getBorrowerDetails(1);

        assertThat(response.getStatusInfo()).isEqualTo(Status.OK);
    }

    @Test
    public void givenABookId_getBookBorrowerDetails_returnsTheCorrectBorrowerDetails() {
        BookBorrowerTO bookBorrowerTO = new BookBorrowerTO();
        Mockito.when(mockBookService.findBookBorrowerDetails(1)).thenReturn(bookBorrowerTO);

        Response response = bookResource.getBorrowerDetails(1);

        assertThat(response.getEntity()).isEqualTo(bookBorrowerTO);
    }
}