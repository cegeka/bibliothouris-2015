package fixture;

import cgk.bibliothouris.learning.service.entity.Author;
import cgk.bibliothouris.learning.service.entity.Book;

import java.util.HashSet;
import java.util.Set;

public class BookTestFixture {

    public static Set<Author> createOneAuthor() {
        Author author = Author.AuthorBuilder.author().withFirstName("Robert C.").withLastName("Martin").build();

        Set<Author> authors = new HashSet<>();
        authors.add(author);

        return authors;
    }

    public static Set<Author> createFourAuthors() {
        Author author1 = Author.AuthorBuilder.author().withFirstName("Erich").withLastName("Gamma").build();
        Author author2 = Author.AuthorBuilder.author().withFirstName("Richard").withLastName("Helm").build();
        Author author3 = Author.AuthorBuilder.author().withFirstName("John").withLastName("Vlissides").build();
        Author author4 = Author.AuthorBuilder.author().withFirstName("Ralph").withLastName("Johnson").build();

        Set<Author> authors = new HashSet<>();
        authors.add(author1);
        authors.add(author2);
        authors.add(author3);
        authors.add(author4);

        return authors;
    }


    public static Book createBookWithOneAuthor() {
        return Book.BookBuilder.book().withTitle("Clean Code")
                .withAuthors(createOneAuthor())
                .withIsbn("978-0-13-235088-4")
                .build();
    }

    public static Book createBookWithFourAuthors() {
        return Book.BookBuilder.book().withTitle("Design Patterns")
                .withAuthors(createFourAuthors())
                .withIsbn("978-0-201-63361-0")
                .build();
    }
}