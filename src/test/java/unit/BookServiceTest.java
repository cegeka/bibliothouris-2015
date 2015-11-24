package unit;

import cgk.bibliothouris.learning.repository.BookRepository;
import cgk.bibliothouris.learning.service.BookService;
import cgk.bibliothouris.learning.service.entity.Author;
import cgk.bibliothouris.learning.service.entity.Book;
import cgk.bibliothouris.learning.service.exception.ValidationException;
import fixture.BookTestFixture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @InjectMocks
    private BookService service;

    @Mock
    private BookRepository mockRepository;

    private Book bookWithOneAuthor;

    @Before
    public void setUp() {
        bookWithOneAuthor = BookTestFixture.createBookWithOneAuthor();
    }

    @Test
    public void givenANewValidBook_createBook_returnsTheNewBook() {
        Mockito.when(mockRepository.createBook(bookWithOneAuthor)).thenReturn(bookWithOneAuthor);

        Book newBook = service.createBook(bookWithOneAuthor);

        assertThat(newBook).isEqualTo(bookWithOneAuthor);
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
        Author authorWithoutLastName = Author.AuthorBuilder.author().withFirstName("Robert C.").build();
        Set<Author> authors = new HashSet<>();
        authors.add(authorWithoutLastName);
        Book book = Book.BookBuilder.book().withTitle("Clean Code").withIsbn("978-0-13-235088-4").withAuthors(authors).build();

        service.createBook(book);
    }
}
