package cgk.bibliothouris.learning.service;

import cgk.bibliothouris.learning.application.transferobject.BookBorrowerTO;
import cgk.bibliothouris.learning.application.transferobject.BookFilterValueTO;
import cgk.bibliothouris.learning.application.transferobject.BookListingTO;
import cgk.bibliothouris.learning.repository.BookRepository;
import cgk.bibliothouris.learning.service.entity.Book;
import cgk.bibliothouris.learning.service.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public Book createBook(Book book) {
        validateBook(book);

        return bookRepository.createBook(book);
    }

    @Transactional(readOnly = true)
    public BookListingTO findAllBooks(String start, String end, String title, String isbn) {
        Integer startPosition = null;
        Integer endPosition = null;

        if (start == null || isNegative(start)) {
            startPosition = 0;
        } else {
            startPosition = Integer.valueOf(start);
        }
        if (end == null || isNegative(end)) {
            endPosition = countBooks().intValue();
        } else {
            endPosition = Integer.valueOf(end);
        }
        return bookRepository.findAllBooks(startPosition, endPosition, title, isbn);
    }

    @Transactional(readOnly = true)
    public Long countBooks() {
        return bookRepository.countBooks();
    }

    @Transactional(readOnly = true)
    public Book findBookById(Integer bookId) {
        return bookRepository.findBookById(bookId);
    }

    @Transactional(readOnly = true)
    public List<BookFilterValueTO> findAllBookTitles() {
        return bookRepository.findAllBookTitles();
    }

    @Transactional(readOnly = true)
    public List<BookFilterValueTO> findAllBookIsbnCodes() {
        return bookRepository.findAllBookIsbnCodes();
    }

    @Transactional(readOnly = true)
    public BookBorrowerTO findBookBorrowerDetails(Integer bookId) {
        if (bookRepository.findBookById(bookId) == null)
            throw new ValidationException("The book was not found in the library!");

        return bookRepository.findBookBorrowerDetails(bookId);
    }

    public void deleteAllBooks() {
        bookRepository.deleteAllBooks();
    }

    private boolean isNegative(String number) {
        try {
            if (Integer.parseInt(number) < 0)
                return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }

    private void validateBook(Book book) {
        Set<ConstraintViolation<Book>> bookConstraintViolations = validator.validate(book);
        if (!bookConstraintViolations.isEmpty())
            throw new ValidationException(bookConstraintViolations.iterator().next().getMessage());
    }
}
