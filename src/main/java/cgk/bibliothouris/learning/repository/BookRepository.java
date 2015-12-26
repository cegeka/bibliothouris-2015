package cgk.bibliothouris.learning.repository;

import cgk.bibliothouris.learning.application.transferobject.BookBorrowerTO;
import cgk.bibliothouris.learning.application.transferobject.ItemsListingTO;
import cgk.bibliothouris.learning.application.transferobject.StringTO;
import cgk.bibliothouris.learning.service.entity.Book;

import java.util.List;

public interface BookRepository {

    Book createBook(Book book);

    ItemsListingTO findAllBooks(Integer start, Integer end, String title, String isbn);

    void deleteAllBooks();

    Long countBooks();

    Book findBookById(Integer bookId);

    List<StringTO> findAllBookTitles();

    List<StringTO> findAllBookIsbnCodes();

    ItemsListingTO findAllAvailableBooks(Integer start, Integer end, String title, String isbn);

    Long countAvailableBooks();

    BookBorrowerTO findBookBorrowerDetails(Integer bookId);

    Long countCurrentlyBorrowedBooksByMember(String memberId);
}
