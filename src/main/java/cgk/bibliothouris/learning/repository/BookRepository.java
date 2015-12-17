package cgk.bibliothouris.learning.repository;

import cgk.bibliothouris.learning.application.transferobject.BookBorrowerTO;
import cgk.bibliothouris.learning.application.transferobject.BookFilterValueTO;
import cgk.bibliothouris.learning.application.transferobject.BookListingTO;
import cgk.bibliothouris.learning.service.entity.Book;

import java.util.List;

public interface BookRepository {

    Book createBook(Book book);

    BookListingTO findAllBooks(Integer start, Integer end, String title, String isbn);

    void deleteAllBooks();

    Long countBooks();

    Book findBookById(Integer bookId);

    List<BookFilterValueTO> findAllBookTitles();

    List<BookFilterValueTO> findAllBookIsbnCodes();

    BookListingTO findAllAvailableBooks(Integer start, Integer end, String title, String isbn);

    Long countAvailableBooks();

    BookBorrowerTO findBookBorrowerDetails(Integer bookId);
}
