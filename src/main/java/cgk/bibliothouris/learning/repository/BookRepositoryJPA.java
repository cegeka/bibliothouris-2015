package cgk.bibliothouris.learning.repository;

import cgk.bibliothouris.learning.service.entity.Book;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class BookRepositoryJPA implements BookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Book createBook(Book book) {
        entityManager.persist(book);

        return book;
    }

    public List<Book> findAllBooks(){
        TypedQuery<Book> selectAllQuery = entityManager.createNamedQuery(Book.LIST_ALL_BOOKS, Book.class);
        return selectAllQuery.getResultList();
    }

    public void deleteAllBooks(){
        Query deleteAllQuery = entityManager.createNamedQuery(Book.DELETE_ALL_BOOKS);
        deleteAllQuery.executeUpdate();
    }
}
