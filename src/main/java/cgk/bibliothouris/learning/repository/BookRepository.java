package cgk.bibliothouris.learning.repository;

import cgk.bibliothouris.learning.application.transferobject.BookBorrowerTO;
import cgk.bibliothouris.learning.application.transferobject.ItemsListingTO;
import cgk.bibliothouris.learning.application.transferobject.StringTO;
import cgk.bibliothouris.learning.application.valueobject.BooksFilterParams;
import cgk.bibliothouris.learning.application.valueobject.PaginationParams;
import cgk.bibliothouris.learning.service.entity.Book;

import java.util.List;

public interface BookRepository {

    Book createBook(Book book);

    ItemsListingTO findAllBooks(PaginationParams paginationParams, BooksFilterParams filter);

    void deleteAllBooks();

    Long countBooks();

    Book findBookById(Integer bookId);

    List<StringTO> findAllBookTitles();

    List<StringTO> findAllBookIsbnCodes();

    ItemsListingTO findAllAvailableBooks(PaginationParams pagination, BooksFilterParams filter);

    Long countAvailableBooks();

    List<Book> findBooksByIsbn(String isbn);

    BookBorrowerTO findBookBorrowerDetails(Integer bookId);

    Long countCurrentlyBorrowedBooksByMember(String memberId);

    void deleteBookById(Integer bookId);
}
