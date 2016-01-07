package cgk.bibliothouris.learning.service;

import cgk.bibliothouris.learning.application.transferobject.BookBorrowerTO;
import cgk.bibliothouris.learning.application.transferobject.ItemsListingTO;
import cgk.bibliothouris.learning.application.transferobject.StringTO;
import cgk.bibliothouris.learning.repository.BookRepository;
import cgk.bibliothouris.learning.service.entity.Author;
import cgk.bibliothouris.learning.service.entity.Book;
import cgk.bibliothouris.learning.service.entity.BookCategory;
import cgk.bibliothouris.learning.service.exception.ValidationException;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;
import org.glassfish.grizzly.utils.Pair;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
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
    public ItemsListingTO findAllBooks(String start, String end, String title, String isbn) {
        Pair<Integer, Integer> paginationParams = BiblioUtilityService.findPaginationParameters(start, end, () -> countBooks());

        return bookRepository.findAllBooks(paginationParams.getFirst(), paginationParams.getSecond(), title, isbn);
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
    public ItemsListingTO findAllAvailableBooks(String start, String end, String title, String isbn) {
        Pair<Integer, Integer> paginationParams = BiblioUtilityService.findPaginationParameters(start, end, () -> countAvailableBooks());

        return bookRepository.findAllAvailableBooks(paginationParams.getFirst(), paginationParams.getSecond(), title, isbn);
    }

    @Transactional(readOnly = true)
    public Long countAvailableBooks() {
        return bookRepository.countAvailableBooks();
    }

    //TODO: show to refactoring reading group
    public List<Book> importContent(String title, String isbn) throws IOException, GeneralSecurityException {
        final Books books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), null).build();
        Volumes volumes = null;
        if(title != null) {
            volumes = books.volumes().list("intitle:" + title).setMaxResults(40L).execute();
        }
        if(isbn != null) {
            volumes = books.volumes().list("isbn:" + isbn).setMaxResults(40L).execute();
        }
        List<Book> listOfImportedBooks = new ArrayList<>();
        for (Volume v : volumes.getItems()) {
            Volume.VolumeInfo info = v.getVolumeInfo();
            String ISBN = getISBNFromImportedContent(info);
            Book builtBookFromImportedContent = Book.BookBuilder.book()
                    .withTitle(info.getTitle())
                    .withAuthors(getAuthorsFromImportedContent(info))
                    .withPublisher(info.getPublisher())
                    .withCover(getCoverFromImportedContent(info))
                    .withDescription(info.getDescription())
                    .withIsbn(ISBN)
                    .withCategories(getCategoriesFromImportedContent(info)).withPages(info.getPageCount())
                    .withDate(getPublishedDateFromImportedContent(info)).build();
            List<Book> existingBooks = bookRepository.findBooksByIsbn(ISBN);
            if (!ISBN.isEmpty()) {
                if (existingBooks.size() > 0) {
                    listOfImportedBooks.add(existingBooks.get(0));
                } else
                    listOfImportedBooks.add(builtBookFromImportedContent);
            } else
                listOfImportedBooks.add(builtBookFromImportedContent);
        }
        return listOfImportedBooks;
    }

    private Set<Author> getAuthorsFromImportedContent(Volume.VolumeInfo info) {
        Set<Author> authorSet = new HashSet<>();
        Author author;
            if (info.getAuthors() != null)
            for (String inf : info.getAuthors()) {
                if(inf.contains(" ")) {
                    author = Author.AuthorBuilder.author().withFirstName(inf.substring(0, inf.indexOf(" "))).withLastName(inf.substring(inf.indexOf(" ") + 1)).build();
                    authorSet.add(author);
                }
                else {
                    author = Author.AuthorBuilder.author().withLastName(inf.substring(inf.indexOf(" ") + 1)).build();
                    authorSet.add(author);
                }
            }
        return authorSet;
    }

    private String getISBNFromImportedContent(Volume.VolumeInfo info) {
        String ISBN = "";
        if (info.getIndustryIdentifiers() != null)
            for (Volume.VolumeInfo.IndustryIdentifiers identifiers : info.getIndustryIdentifiers()) {
                if (identifiers.getType().equals("ISBN_13")) {
                    ISBN = identifiers.getIdentifier();
                }
            }
        return ISBN;
    }

    private Set<BookCategory> getCategoriesFromImportedContent(Volume.VolumeInfo info) {
        Set<BookCategory> categorySet = new HashSet<>();
        if (info.getCategories() != null)
            for (String inf : info.getCategories()) {
                BookCategory category = BookCategory.CategoryBuilder.category().withCategory(inf).build();
                categorySet.add(category);
            }
        return categorySet;
    }

    private String getCoverFromImportedContent(Volume.VolumeInfo info) {
        String cover = "";
        if (info.getImageLinks() != null)
            cover = info.getImageLinks().getThumbnail();
        return cover;
    }
    //TODO: show to refactoring group
    private LocalDate getPublishedDateFromImportedContent(Volume.VolumeInfo info) {
        LocalDate date = null;
        if (info.getPublishedDate() != null) {
                String[] parts = info.getPublishedDate().split("-");
                if (parts.length == 3)
                    date = LocalDate.of(Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]),
                            Integer.parseInt(parts[2]));
                if (parts.length == 2)
                    date = LocalDate.of(Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]),
                            01);
                if (parts.length == 1) {
                    if (parts[0].contains("*"))
                        date = LocalDate.of(Integer.parseInt(info.getPublishedDate().substring(0, info.getPublishedDate().indexOf("*"))),
                                Month.JANUARY,
                                01);

                    else
                        try {
                            date = LocalDate.of(Integer.parseInt(parts[0]),
                                    Month.JANUARY,
                                    01);
                        } catch(Exception exc){
                            date = null;
                        }
                }
            }

        return date;
    }
}






