package cgk.bibliothouris.learning.repository;

import cgk.bibliothouris.learning.service.entity.Book;

import java.util.List;

public interface BookRepository {

    Book createBook(Book book);

    List<Book> findAllBooks();

    void deleteAllBooks();
}