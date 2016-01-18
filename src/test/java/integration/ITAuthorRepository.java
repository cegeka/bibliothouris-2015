package integration;
import cgk.bibliothouris.learning.application.transferobject.BookWithStatusTO;
import cgk.bibliothouris.learning.application.transferobject.ItemsListingTO;
import cgk.bibliothouris.learning.config.AppConfig;
import cgk.bibliothouris.learning.repository.AuthorRepository;
import cgk.bibliothouris.learning.repository.BookRepository;
import cgk.bibliothouris.learning.service.entity.Author;
import cgk.bibliothouris.learning.service.entity.Book;
import fixture.AuthorTestFixture;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


import cgk.bibliothouris.learning.application.transferobject.StringTO;
import cgk.bibliothouris.learning.config.AppConfig;
import fixture.BookTestFixture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@Transactional
public class ITAuthorRepository {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    private Author author;
    private Book bookWithOneAuthorAndThreeCategories, bookWithFourAuthorsAndThreeCategories;

    @Before
    public void setUp() {
        author = AuthorTestFixture.createAuthorWithOneFirstAndOneLastName();
        bookWithOneAuthorAndThreeCategories = BookTestFixture.createBookWithOneAuthorAndThreeCategories();
        bookWithFourAuthorsAndThreeCategories = BookTestFixture.createBookWithFourAuthorsAndThreeCategories();
        authorRepository.deleteAllAuthors();
    }

    @Test
    public void givenOneAuthor_findAllAuthors_findTheAuthors() {
        bookRepository.createBook(bookWithOneAuthorAndThreeCategories);
        author = Author.AuthorBuilder.author().withFirstName("Robert C.").withLastName("Martin").build();

        List<Author> expectedList = authorRepository.findAllAuthors();

        assertThat(expectedList).contains(author);
        assertThat(expectedList.size()).isEqualTo(1);
    }

    @Test
    public void givenTwoAuthors_findAllBookAuthorFirstName_findTheAuthors() {
        bookRepository.createBook(bookWithOneAuthorAndThreeCategories);
        bookRepository.createBook(bookWithFourAuthorsAndThreeCategories);

        List<StringTO> foundListOfAuthors = authorRepository.findAllBookAuthorFirstName();

        assertThat(foundListOfAuthors.size()).isEqualTo(5);
    }

    @Test
    public void givenTwoAuthors_findAllBookAuthorLastName_findTheAuthors() {
        bookRepository.createBook(bookWithOneAuthorAndThreeCategories);
        bookRepository.createBook(bookWithFourAuthorsAndThreeCategories);

        List<StringTO> foundListOfAuthors = authorRepository.findAllBookAuthorLastName();

        assertThat(foundListOfAuthors.size()).isEqualTo(5);
    }
}