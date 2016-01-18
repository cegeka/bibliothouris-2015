package cgk.bibliothouris.learning.repository;

import cgk.bibliothouris.learning.service.entity.BookCategory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CategoryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<BookCategory> findAllCategories() {
        TypedQuery<BookCategory> query = entityManager.createNamedQuery(BookCategory.FIND_ALL_CATEGORIES, BookCategory.class);
        return query.getResultList();
    }
}
