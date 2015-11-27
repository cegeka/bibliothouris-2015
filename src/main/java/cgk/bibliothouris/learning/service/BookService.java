package cgk.bibliothouris.learning.service;

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

    public List<Book> findAllBooks(String start, String end) {
        Integer startPosition = null;
        Integer endPosition = null;

        if (start == null || isNegative(start)) {
            startPosition = 0;
        } else if (end == null || isNegative(end)) {
            endPosition = Integer.valueOf(countBooks().intValue());
        } else {
            startPosition = Integer.valueOf(start);
            endPosition = Integer.valueOf(end);
        }
        return bookRepository.findAllBooks(startPosition, endPosition);
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

    public void deleteAllBooks() {
        bookRepository.deleteAllBooks();
    }

    public Long countBooks() {
        return bookRepository.countBooks();
    }
}
