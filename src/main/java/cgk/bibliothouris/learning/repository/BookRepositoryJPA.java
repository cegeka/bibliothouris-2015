package cgk.bibliothouris.learning.repository;

import cgk.bibliothouris.learning.application.transferobject.*;
import cgk.bibliothouris.learning.service.entity.Author;
import cgk.bibliothouris.learning.service.entity.Book;
import cgk.bibliothouris.learning.service.entity.BookCategory;
import cgk.bibliothouris.learning.service.entity.BorrowHistoryItem;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class BookRepositoryJPA implements BookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Book createBook(Book book) {
        Book bookWithAuthors = getBookWithPersistedAuthors(book);

        Book bookWithAuthorsAndCategories = getBookWithPersistedCategories(bookWithAuthors);

        entityManager.persist(bookWithAuthorsAndCategories);

        return bookWithAuthors;
    }

    private Book getBookWithPersistedCategories(Book book) {
        Set<BookCategory> persistedCategories = persistCategoryWhenFoundInCategoryTable(book);
        book.setCategories(persistedCategories);

        return book;
    }

    private Set<BookCategory> persistCategoryWhenFoundInCategoryTable(Book book) {
        Set<BookCategory> persistedCategories = new HashSet<>();

        for(BookCategory category : book.getCategories()) {
            List<BookCategory> categories = queryCategoryTypeInCategoryTable(category);

            if(isCategoryTypeFoundInTable(categories)) {
                persistedCategories.add(categories.get(0));
            } else {
                entityManager.persist(category);
                persistedCategories.add(category);
            }
        }
        return persistedCategories;
    }

    private List<BookCategory> queryCategoryTypeInCategoryTable(BookCategory category) {
        TypedQuery<BookCategory> query = entityManager.createNamedQuery(BookCategory.FIND_CATEGORY_BY_TYPE,
                BookCategory.class);
        query.setParameter("category", category.getCategory());
        return query.getResultList();
    }

    private boolean isCategoryTypeFoundInTable(List<BookCategory> categories) {
        return categories.isEmpty() ? false : true;
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



    @Override
    public ItemsListingTO findAllBooks(Integer start, Integer end, String title, String isbn){
        List<Book> books = findBooks(start, end, title, isbn);
        Long booksCount = countBooks(title, isbn);

        List<BookWithStatusTO> bookTOS = books.stream().map(BookWithStatusTO::new).collect(Collectors.toList());
        for (BookWithStatusTO bookTO : bookTOS)
            bookTO.setIsAvailable(!findBookBorrowerDetails(bookTO.getId()).getIsBorrowed());

        return new ItemsListingTO(bookTOS, booksCount);
    }

    private List<Book> findBooks(Integer start, Integer end, String title, String isbn) {
        String selectStatement = "SELECT b FROM Book b";

        String filterClause = generateFilterQueryClause(title, isbn);
        String sortClause = generateSortQueryClause();

        TypedQuery<Book> selectAllQuery = entityManager.createQuery(selectStatement + " WHERE" + filterClause + " " + sortClause, Book.class)
                .setMaxResults(end - start)
                .setFirstResult(start);
        return selectAllQuery.getResultList();
    }

    @Override
    public List<Book> findBooksByIsbn(String isbn) {
        String selectStatement = "SELECT b FROM Book b";

        String filterClause = generateFilterQueryClause(null, isbn);

        TypedQuery<Book> selectAllQuery = entityManager.createQuery(selectStatement + " WHERE" + filterClause, Book.class);

        return selectAllQuery.getResultList();
    }

    private String generateFilterQueryClause(String title, String isbn) {
        String conditionalClause = " 1 = 1";

        if (title != null)
            conditionalClause += " AND lower(b.title) LIKE '%" + title.toLowerCase() + "%'";
        if (isbn != null)
            conditionalClause += " AND b.isbn LIKE '%" + isbn + "%' OR REPLACE(b.isbn, '-', '') LIKE '%" + isbn + "%'";

        return conditionalClause;
    }

    private String generateSortQueryClause() {
        String sortClause = "ORDER BY lower(b.title), b.id";

        return sortClause;
    }

    @Override
    public BookBorrowerTO findBookBorrowerDetails(Integer bookId) {
        TypedQuery<BookBorrowerTO> query = entityManager.createNamedQuery(BorrowHistoryItem.GET_CURRENT_BORROWER_DETAILS_FOR_BOOK, BookBorrowerTO.class);
        query.setParameter("bookId", bookId);

        List<BookBorrowerTO> bookBorrowerTOs = query.getResultList();

        if (bookBorrowerTOs.isEmpty())
            return new BookBorrowerTO();
        else
            return bookBorrowerTOs.get(0);
    }

    @Override
    public ItemsListingTO findAllAvailableBooks(Integer start, Integer end, String title, String isbn){
        List<Book> books = findAvailableBooks(start, end, title, isbn);
        Long booksCount = countAvailableBooks(title, isbn);

        List<BookTO> bookTOS = books.stream().map(book -> new BookTO(book)).collect(Collectors.toList());

        return new ItemsListingTO(bookTOS, booksCount);
    }

    private List<Book> findAvailableBooks(Integer start, Integer end, String title, String isbn) {
        String selectAvailableStatement = "SELECT b FROM Book b WHERE b.id NOT IN (SELECT bHist.book.id FROM BorrowHistoryItem bHist WHERE bHist.endDate IS NULL)";

        String filterClause = generateFilterQueryClause(title, isbn);
        String sortClause = generateSortQueryClause();

        TypedQuery<Book> selectAllQuery = entityManager.createQuery(selectAvailableStatement + " AND" + filterClause + " " + sortClause, Book.class)
                .setMaxResults(end - start)
                .setFirstResult(start);
        return selectAllQuery.getResultList();
    }

    @Override
    public Book findBookById(Integer bookId) {
        return entityManager.find(Book.class, bookId);
    }

    @Override
    public List<StringTO> findAllBookTitles() {
        return entityManager.createNamedQuery(Book.GET_BOOK_TITLES, StringTO.class).getResultList();
    }

    @Override
    public List<StringTO> findAllBookIsbnCodes() {
        return entityManager.createNamedQuery(Book.GET_BOOK_ISBN_CODES, StringTO.class).getResultList();
    }

    @Override
    public void deleteAllBooks(){
        Query deleteAllQuery = entityManager.createNamedQuery(Book.DELETE_ALL_BOOKS);
        deleteAllQuery.executeUpdate();
    }

    @Override
    public Long countBooks(){
        TypedQuery<Long> countQuery = entityManager.createNamedQuery(Book.COUNT_BOOKS, Long.class);
        return countQuery.getSingleResult();
    }

    @Override
    public Long countAvailableBooks(){
        TypedQuery<Long> countQuery = entityManager.createNamedQuery(Book.COUNT_AVAILABLE_BOOKS, Long.class);
        return countQuery.getSingleResult();
    }

    private Long countAvailableBooks(String title, String isbn) {
        String statement = "SELECT COUNT(b.id) FROM Book b WHERE b.id NOT IN (SELECT bHist.book.id FROM BorrowHistoryItem bHist WHERE bHist.endDate IS NULL)";

        String filterClause = generateFilterQueryClause(title, isbn);

        TypedQuery<Long> countQuery = entityManager.createQuery(statement + "AND" + filterClause, Long.class);
        return countQuery.getSingleResult();
    }

    private Long countBooks(String title, String isbn) {
        String statement = "SELECT COUNT(*) FROM Book b";

        String filterClause = generateFilterQueryClause(title, isbn);

        TypedQuery<Long> countQuery = entityManager.createQuery(statement + " WHERE" + filterClause, Long.class);
        return countQuery.getSingleResult();
    }

    @Override
    public Long countCurrentlyBorrowedBooksByMember(String memberId) {
        TypedQuery<Long> countQuery = entityManager.createNamedQuery(Book.COUNT_ALL_CURRENTLY_BORROWED_BOOKS, Long.class);
        countQuery.setParameter("memberId", memberId);

        return countQuery.getSingleResult();
    }

    @Override
    public void deleteBookById(Integer bookId){
        Book book = entityManager.find(Book.class, bookId);

        entityManager.remove(book);
    }
}
