package cgk.bibliothouris.learning.repository;

import cgk.bibliothouris.learning.application.transferobject.BookListingTO;
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
    public List<BookListingTO> findAllBooks(Integer start, Integer end){
        TypedQuery<Book> selectAllQuery = entityManager.createNamedQuery(Book.LIST_ALL_BOOKS, Book.class)
                                                       .setMaxResults(end - start)
                                                       .setFirstResult(start);
        List<Book> books = selectAllQuery.getResultList();

        return books.stream().map(book -> new BookListingTO(book)).collect(Collectors.toList());
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
}
