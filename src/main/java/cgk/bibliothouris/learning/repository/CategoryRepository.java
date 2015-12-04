package cgk.bibliothouris.learning.repository;

import cgk.bibliothouris.learning.service.entity.Book;
import cgk.bibliothouris.learning.service.entity.BookCategory;

import java.util.List;

public interface CategoryRepository {

    BookCategory createCategory(BookCategory category);

    List<BookCategory> findAllCategories();

    Long countAllCategories();
}
