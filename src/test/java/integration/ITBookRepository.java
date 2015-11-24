package integration;

import cgk.bibliothouris.learning.config.AppConfig;
import cgk.bibliothouris.learning.repository.BookRepository;
import cgk.bibliothouris.learning.service.entity.Book;
import fixture.BookTestFixture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@Transactional
public class ITBookRepository {

    @Autowired
    private BookRepository bookRepository;
    private Book bookWithOneAuthor, bookWithFourAuthors;

    @Before
    public void setUp() {
        bookWithOneAuthor = BookTestFixture.createBookWithOneAuthor();
        bookWithFourAuthors = BookTestFixture.createBookWithFourAuthors();
    }

    @Test
    public void givenANewBookWithOneAuthor_createBook_createsNewBookWithOneAuthor() {
        Book newBook = bookRepository.createBook(bookWithOneAuthor);

        assertThat(newBook.getId()).isNotNull();
    }

    @Test
    public void givenANewBookWithFourAuthors_createBook_createsNewBookWithFourAuthors() {
        Book newBook = bookRepository.createBook(bookWithFourAuthors);

        assertThat(newBook.getId()).isNotNull();
    }
}
