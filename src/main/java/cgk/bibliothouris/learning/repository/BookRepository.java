package cgk.bibliothouris.learning.repository;

import cgk.bibliothouris.learning.application.transferobject.*;
import cgk.bibliothouris.learning.application.valueobject.BooksFilterParams;
import cgk.bibliothouris.learning.application.valueobject.PaginationParams;
import cgk.bibliothouris.learning.service.entity.Author;
import cgk.bibliothouris.learning.service.entity.Book;
import cgk.bibliothouris.learning.service.entity.BookCategory;
import cgk.bibliothouris.learning.service.entity.BorrowHistoryItem;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class BookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Book createBook(Book book) {
        Book bookWithAuthors = getBookWithPersistedAuthors(book);

        entityManager.persist(bookWithAuthors);

        return bookWithAuthors;
    }

    private Book getBookWithPersistedAuthors(Book book){
        Set<Author> persistedAuthors = persistAuthorWhenFoundInAuthorTable(book);
        book.setAuthors(persistedAuthors);

        return book;
    }

    private Set<Author> persistAuthorWhenFoundInAuthorTable(Book book) {
        Set<Author> persistedAuthors = new HashSet<>();

        for (Author author : book.getAuthors()) {
            List<Author> authors = queryAuthors(author);

            if (isAuthorFoundInAuthorTable(authors)) {
                persistedAuthors.add(authors.get(0));
            } else {
                entityManager.persist(author);
                persistedAuthors.add(author);
            }
        }
        return persistedAuthors;
    }

    private List<Author> queryAuthors(Author author) {
        TypedQuery<Author> query = entityManager.createNamedQuery(Author.FIND_AUHTORS_BY_FIRSTNAME_AND_LASTNAME, Author.class);
        query.setParameter("firstName", author.getFirstName());
        query.setParameter("lastName", author.getLastName());

        return query.getResultList();
    }

    private boolean isAuthorFoundInAuthorTable(List<Author> authors) {
        return authors.isEmpty() ? false : true;
    }

    public ItemsListingTO findAllBooks(PaginationParams pagination, BooksFilterParams filter){
        List<Book> books = findBooks(pagination, filter);

        List<BookWithStatusTO> bookTOS = books.stream().map(BookWithStatusTO::new).collect(Collectors.toList());
        for (BookWithStatusTO bookTO : bookTOS)
            bookTO.setIsAvailable(!findBookBorrowerDetails(bookTO.getId()).getIsBorrowed());

        Integer booksCount = bookTOS.size();
        bookTOS = bookTOS.subList(Integer.valueOf(pagination.getStart()), Integer.min(Integer.valueOf(pagination.getEnd()), booksCount));

        return new ItemsListingTO(bookTOS, new Long(booksCount));
    }

    private List<Book> findBooks(PaginationParams pagination, BooksFilterParams filter) {
        String selectStatement = "SELECT b FROM Book b";

        String filterClause = generateFilterQueryClause(filter);
        String sortClause = generateSortQueryClause();

        TypedQuery<Book> selectAllQuery = entityManager.createQuery(selectStatement + " WHERE" + filterClause + " " + sortClause, Book.class);
        return selectAllQuery.getResultList();
    }

    public List<Book> findBooksByIsbn(String isbn) {
        String selectStatement = "SELECT b FROM Book b";

        String filterClause = generateFilterQueryClause(new BooksFilterParams(null, isbn));

        TypedQuery<Book> selectAllQuery = entityManager.createQuery(selectStatement + " WHERE" + filterClause, Book.class);

        return selectAllQuery.getResultList();
    }

    private String generateFilterQueryClause(BooksFilterParams filter) {
        String conditionalClause = " 1 = 1";

        if (filter.getTitle() != null)
            conditionalClause += " AND lower(b.title) LIKE '%" + filter.getTitle().toLowerCase() + "%'";
        if (filter.getIsbn() != null)
            conditionalClause += " AND b.isbn LIKE '%" + filter.getIsbn() + "%' OR REPLACE(b.isbn, '-', '') LIKE '%" + filter.getIsbn() + "%'";

        return conditionalClause;
    }

    private String generateSortQueryClause() {
        String sortClause = "ORDER BY lower(b.title), b.id";

        return sortClause;
    }

    public BookBorrowerTO findBookBorrowerDetails(Integer bookId) {
        TypedQuery<BookBorrowerTO> query = entityManager.createNamedQuery(BorrowHistoryItem.GET_CURRENT_BORROWER_DETAILS_FOR_BOOK, BookBorrowerTO.class);
        query.setParameter("bookId", bookId);

        List<BookBorrowerTO> bookBorrowerTOs = query.getResultList();

        if (bookBorrowerTOs.isEmpty())
            return new BookBorrowerTO();
        else
            return bookBorrowerTOs.get(0);
    }

    public ItemsListingTO findAllAvailableBooks(PaginationParams pagination, BooksFilterParams filter){
        List<Book> books = findAvailableBooks(pagination, filter);
        Long booksCount = countAvailableBooks(filter);

        List<BookTO> bookTOS = books.stream().map(book -> new BookTO(book)).collect(Collectors.toList());

        return new ItemsListingTO(bookTOS, booksCount);
    }

    private List<Book> findAvailableBooks(PaginationParams pagination, BooksFilterParams filter) {
        String selectAvailableStatement = "SELECT b FROM Book b WHERE b.id NOT IN (SELECT bHist.book.id FROM BorrowHistoryItem bHist WHERE bHist.endDate IS NULL)";

        String filterClause = generateFilterQueryClause(filter);
        String sortClause = generateSortQueryClause();

        TypedQuery<Book> selectAllQuery = entityManager.createQuery(selectAvailableStatement + " AND" + filterClause + " " + sortClause, Book.class)
                .setMaxResults(Integer.valueOf(pagination.getEnd()) - Integer.valueOf(pagination.getStart()))
                .setFirstResult(Integer.valueOf(pagination.getStart()));
        return selectAllQuery.getResultList();
    }

    public Book findBookById(Integer bookId) {
        return entityManager.find(Book.class, bookId);
    }

    public List<StringTO> findAllBookTitles() {
        return entityManager.createNamedQuery(Book.GET_BOOK_TITLES, StringTO.class).getResultList();
    }

    public List<StringTO> findAllBookIsbnCodes() {
        return entityManager.createNamedQuery(Book.GET_BOOK_ISBN_CODES, StringTO.class).getResultList();
    }

    public void deleteAllBooks(){
        Query deleteAllQuery = entityManager.createNamedQuery(Book.DELETE_ALL_BOOKS);
        deleteAllQuery.executeUpdate();
    }

    public Long countBooks(){
        TypedQuery<Long> countQuery = entityManager.createNamedQuery(Book.COUNT_BOOKS, Long.class);
        return countQuery.getSingleResult();
    }

    public Long countAvailableBooks(){
        TypedQuery<Long> countQuery = entityManager.createNamedQuery(Book.COUNT_AVAILABLE_BOOKS, Long.class);
        return countQuery.getSingleResult();
    }

    private Long countAvailableBooks(BooksFilterParams filter) {
        String statement = "SELECT COUNT(b.id) FROM Book b WHERE b.id NOT IN (SELECT bHist.book.id FROM BorrowHistoryItem bHist WHERE bHist.endDate IS NULL)";

        String filterClause = generateFilterQueryClause(filter);

        TypedQuery<Long> countQuery = entityManager.createQuery(statement + "AND" + filterClause, Long.class);
        return countQuery.getSingleResult();
    }

    private Long countBooks(BooksFilterParams filter) {
        String statement = "SELECT COUNT(*) FROM Book b";

        String filterClause = generateFilterQueryClause(filter);

        TypedQuery<Long> countQuery = entityManager.createQuery(statement + " WHERE" + filterClause, Long.class);
        return countQuery.getSingleResult();
    }

    public Long countCurrentlyBorrowedBooksByMember(String memberId) {
        TypedQuery<Long> countQuery = entityManager.createNamedQuery(Book.COUNT_ALL_CURRENTLY_BORROWED_BOOKS, Long.class);
        countQuery.setParameter("memberId", memberId);

        return countQuery.getSingleResult();
    }

    public void deleteBookById(Integer bookId){
        Book book = entityManager.find(Book.class, bookId);

        entityManager.remove(book);
    }
}
