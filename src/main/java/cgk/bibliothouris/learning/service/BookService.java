package cgk.bibliothouris.learning.service;

import cgk.bibliothouris.learning.application.transferobject.BookBorrowerTO;
import cgk.bibliothouris.learning.application.transferobject.ItemsListingTO;
import cgk.bibliothouris.learning.application.transferobject.StringTO;
import cgk.bibliothouris.learning.application.valueobject.BooksFilterParams;
import cgk.bibliothouris.learning.application.valueobject.PaginationParams;
import cgk.bibliothouris.learning.application.valueobject.SortParams;
import cgk.bibliothouris.learning.repository.BookRepository;
import cgk.bibliothouris.learning.service.converter.ImportedBookConverter;
import cgk.bibliothouris.learning.service.entity.Book;
import cgk.bibliothouris.learning.service.exception.ValidationException;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;
import org.glassfish.grizzly.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public Book createBook(Book book) {
        book.setIsbn(splitContinuousISBN(book.getIsbn()));

        validateBook(book);

        return bookRepository.createBook(book);
    }

    private String splitContinuousISBN(String isbn) {
        if (isbn == null || isbn.split("-").length > 1)
            return isbn;

        List<String> isbnParts  = new ArrayList<>();
        isbnParts.add(isbn.substring(0, 3));
        isbnParts.add(isbn.substring(3, 4));
        isbnParts.add(isbn.substring(4, 7));
        isbnParts.add(isbn.substring(7, 12));
        isbnParts.add(isbn.substring(12, 13));

        return isbnParts.stream().collect(Collectors.joining("-"));
    }

    @Transactional(readOnly = true)
    public ItemsListingTO findAllBooks(PaginationParams pagination, BooksFilterParams filter, SortParams sortParams) {
        BiblioUtilityService.validatePaginationParams(pagination);
        PaginationParams paginationParams = BiblioUtilityService.findPaginationParameters(pagination, () -> countBooks());

        return bookRepository.findAllBooks(paginationParams, filter, sortParams);
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
    public List<StringTO> findAllBookTitles() {
        return bookRepository.findAllBookTitles();
    }

    @Transactional(readOnly = true)
    public List<StringTO> findAllBookIsbnCodes() {
        return bookRepository.findAllBookIsbnCodes();
    }

    @Transactional(readOnly = true)
    public BookBorrowerTO findBookBorrowerDetails(Integer bookId) {
        if (bookRepository.findBookById(bookId) == null)
            throw new ValidationException("The book was not found in the library!");

        return bookRepository.findBookBorrowerDetails(bookId);
    }

    public void deleteBookById(Integer bookId) {
        bookRepository.deleteBookById(bookId);
    }

    private void validateBook(Book book) {
        Set<ConstraintViolation<Book>> bookConstraintViolations = validator.validate(book);
        if (!bookConstraintViolations.isEmpty())
            throw new ValidationException(bookConstraintViolations.iterator().next().getMessage());
    }

    @Transactional(readOnly = true)
    public ItemsListingTO findAllAvailableBooks(PaginationParams pagination, BooksFilterParams filter) {
        BiblioUtilityService.validatePaginationParams(pagination);
        PaginationParams paginationParams = BiblioUtilityService.findPaginationParameters(pagination, () -> countAvailableBooks());

        return bookRepository.findAllAvailableBooks(paginationParams, filter);
    }

    @Transactional(readOnly = true)
    public Long countAvailableBooks() {
        return bookRepository.countAvailableBooks();
    }

    //TODO: show to refactoring reading group
    public List<Book> importContent(BooksFilterParams filter) throws IOException, GeneralSecurityException {
        final Books books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), null).build();
        Volumes volumes = specifyParametersForGoogleBooksSearch(filter, books);
        List<Book> listOfImportedBooks = new ArrayList<>();

        if (volumes.getTotalItems() == 0)
            return listOfImportedBooks;

        for (Volume v : volumes.getItems()) {
            Volume.VolumeInfo info = v.getVolumeInfo();
            listOfImportedBooks = addExistingBooksInDBToListOfImportedBooks(new ImportedBookConverter(info),
                    listOfImportedBooks);
        }
        return listOfImportedBooks;
    }

    private Volumes specifyParametersForGoogleBooksSearch(BooksFilterParams filter, Books books)
            throws IOException {
        if(filter.getTitle() != null) {
            return books.volumes().list("intitle:" + filter.getTitle()).setMaxResults(40L).execute();
        }
        if(filter.getIsbn() != null) {
            return books.volumes().list("isbn:" + filter.getIsbn()).setMaxResults(40L).execute();
        }
        return null;
    }

    private List<Book> addExistingBooksInDBToListOfImportedBooks(ImportedBookConverter bookConverter,
                                                                 List<Book> listOfImportedBooks) throws GeneralSecurityException, IOException {
            List<Book> existingBooks = bookRepository.findBooksByIsbn(bookConverter.convertVolumeInfo().getIsbn());
            if (!bookConverter.convertVolumeInfo().getIsbn().isEmpty()) {
                if (existingBooks.size() > 0) {
                    listOfImportedBooks.add(existingBooks.get(0));
                } else
                    listOfImportedBooks.add(bookConverter.convertVolumeInfo());
            } else
                listOfImportedBooks.add(bookConverter.convertVolumeInfo());

        return listOfImportedBooks;
    }
}