package cgk.bibliothouris.learning.repository;

import cgk.bibliothouris.learning.application.transferobject.StringTO;
import cgk.bibliothouris.learning.service.entity.Author;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class AuthorRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Author> findAllAuthors() {
        TypedQuery<Author> query = entityManager.createNamedQuery(Author.FIND_ALL_AUTHORS, Author.class);
        return query.getResultList();
    }

    public List<StringTO> findAllBookAuthorFirstName() {
        TypedQuery<StringTO> findQuery = entityManager.createNamedQuery(Author.FIND_AUTHORS_BY_FIRSTNAME, StringTO.class);
        return findQuery.getResultList();
    }

    public List<StringTO> findAllBookAuthorLastName() {
        TypedQuery<StringTO> findQuery = entityManager.createNamedQuery(Author.FIND_AUTHORS_BY_LASTNAME, StringTO.class);
        return findQuery.getResultList();
    }

    public void deleteAllAuthors(){
        Query deleteAllQuery = entityManager.createNamedQuery(Author.DELETE_ALL_AUTHORS);
        deleteAllQuery.executeUpdate();
    }

}
