package cgk.bibliothouris.learning.repository;

import cgk.bibliothouris.learning.service.entity.Author;

import java.util.List;

public interface AuthorRepository {

    List<Author> findAllAuthors();
}
