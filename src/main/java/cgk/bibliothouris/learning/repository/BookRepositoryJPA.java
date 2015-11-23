package cgk.bibliothouris.learning.repository;

import cgk.bibliothouris.learning.service.entity.Book;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class BookRepositoryJPA implements BookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Book createBook(Book book) {
        entityManager.persist(book);

        return book;
    }
}
