package cgk.bibliothouris.learning.repository;

import cgk.bibliothouris.learning.service.entity.Author;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class AuthorRepositoryJPA implements AuthorRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Author> findAllAuthors() {
        TypedQuery<Author> query = entityManager.createNamedQuery(Author.FIND_ALL_AUTHORS, Author.class);
        return query.getResultList();
    }
}
