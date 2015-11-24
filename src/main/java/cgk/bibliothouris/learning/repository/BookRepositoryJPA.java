package cgk.bibliothouris.learning.repository;

import cgk.bibliothouris.learning.service.entity.Author;
import cgk.bibliothouris.learning.service.entity.Book;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class BookRepositoryJPA implements BookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Book createBook(Book book) {
        Set<Author> authors = book.getAuthors();
        Set<Author> persistedAuthors = new HashSet<>();

        for (Author author : authors) {
            TypedQuery<Author> query = entityManager.createQuery("select a from Author a where a.lastName = :last_name and a.firstName = :first_name", Author.class);
            query.setParameter("last_name", author.getLastName());
            query.setParameter("first_name", author.getFirstName());
            List<Author> aus = query.getResultList();
            if (!aus.isEmpty()) {
                persistedAuthors.add(aus.get(0));
            } else {
                entityManager.persist(author);
                persistedAuthors.add(author);
            }
        }

        book.setAuthors(persistedAuthors);

        entityManager.persist(book);

        return book;
    }
}
