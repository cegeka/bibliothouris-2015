package cgk.bibliothouris.learning.repository;

import cgk.bibliothouris.learning.application.transferobject.BookListingTO;
import cgk.bibliothouris.learning.application.transferobject.BookTitleTO;
import cgk.bibliothouris.learning.service.entity.Book;

import java.util.List;

public interface BookRepository {

    Book createBook(Book book);

    List<BookListingTO> findAllBooks(Integer start, Integer end);

    void deleteAllBooks();

    Long countBooks();

    Book findBookById(Integer bookId);

    List<BookTitleTO> findAllBookTitles();
}
