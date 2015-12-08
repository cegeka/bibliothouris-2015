package cgk.bibliothouris.learning.repository;

import cgk.bibliothouris.learning.application.transferobject.BookListingTO;
import cgk.bibliothouris.learning.application.transferobject.BookTO;
import cgk.bibliothouris.learning.application.transferobject.BookTitleTO;
import cgk.bibliothouris.learning.service.entity.Author;
import cgk.bibliothouris.learning.service.entity.Book;
import cgk.bibliothouris.learning.service.entity.BookCategory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.Collector;
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

    @Override
    public BookListingTO findAllBooks(Integer start, Integer end, String title){
        List<Book> books = findBooks(start, end, title);
        Long booksCount = countBooks(title);

        List<BookTO> bookTOS = books.stream().map(book -> new BookTO(book)).collect(Collectors.toList());

        return new BookListingTO(bookTOS, booksCount);
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
    public Book findBookById(Integer bookId) {
        return entityManager.find(Book.class, bookId);
    }

    @Override
    public List<BookTitleTO> findAllBookTitles() {
        return entityManager.createNamedQuery(Book.GET_BOOK_TITLES, BookTitleTO.class).getResultList();
    }

    private Book getBookWithPersistedAuthors(Book book){
        Set<Author> persistedAuthors = new HashSet<>();

        for (Author author : book.getAuthors()) {
            TypedQuery<Author> query = entityManager.createNamedQuery(Author.FIND_AUHTORS_BY_FIRSTNAME_AND_LASTNAME, Author.class);
            query.setParameter("firstName", author.getFirstName());
            query.setParameter("lastName", author.getLastName());
            List<Author> authors = query.getResultList();

            if (!authors.isEmpty()) {
                persistedAuthors.add(authors.get(0));
            } else {
                entityManager.persist(author);
                persistedAuthors.add(author);
            }
        }

        book.setAuthors(persistedAuthors);

        return book;
    }

    private Book getBookWithPersistedCategories(Book book) {
        Set<BookCategory> persistedCategories = new HashSet<>();

        for(BookCategory category : book.getCategories()) {
            TypedQuery<BookCategory> query = entityManager.createNamedQuery(BookCategory.FIND_CATEGORY_BY_TYPE, BookCategory.class);
            query.setParameter("category", category.getCategory());
            List<BookCategory> categories = query.getResultList();

            if(!categories.isEmpty()) {
                persistedCategories.add(categories.get(0));
            } else {
                entityManager.persist(category);
                persistedCategories.add(category);
            }
        }

        book.setCategories(persistedCategories);

        return book;
    }

    private List<Book> findBooks(Integer start, Integer end, String title) {
        String selectStatement = "SELECT b FROM Book b";

        String filterClause = generateFilterQueryClause(title);
        String sortClause = generateSortQueryClause();

        TypedQuery<Book> selectAllQuery = entityManager.createQuery(selectStatement + " " + filterClause + " " + sortClause, Book.class)
                .setMaxResults(end - start)
                .setFirstResult(start);
        return selectAllQuery.getResultList();
    }

    private Long countBooks(String title) {
        String statement = "SELECT COUNT(*) FROM Book b";

        String filterClause = generateFilterQueryClause(title);

        TypedQuery<Long> countQuery = entityManager.createQuery(statement + " " + filterClause, Long.class);
        return countQuery.getSingleResult();
    }

    private String generateFilterQueryClause(String title) {
        String conditionalClause = "WHERE 1 = 1";

        if (title != null)
            conditionalClause += " AND b.title = '" + title + "'";

        return conditionalClause;
    }

    private String generateSortQueryClause() {
        String sortClause = "ORDER BY lower(b.title), b.id";

        return sortClause;
    }
}
