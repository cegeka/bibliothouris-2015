package unit;

import cgk.bibliothouris.learning.repository.BookRepository;
import cgk.bibliothouris.learning.service.BookService;
import cgk.bibliothouris.learning.service.entity.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @InjectMocks
    private BookService service;

    @Mock
    private BookRepository mockRepository;

    @Test
    public void givenABook_createBook_returnsTheNewBook() {
        Book book = new Book();
        Mockito.when(mockRepository.createBook(book)).thenReturn(book);

        Book newBook = service.createBook(book);

        assertThat(newBook).isEqualTo(book);
    }

    @Test
    public void givenOneBook_findAllBooks_returnsTheBook() {
        List<Book> listOfBooks = new ArrayList<>();
        Mockito.when(mockRepository.findAllBooks()).thenReturn(listOfBooks);

        List<Book> books = service.findAllBooks();

        assertThat(books).isEqualTo(listOfBooks);
    }


}
