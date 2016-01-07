package fixture;

import cgk.bibliothouris.learning.service.entity.Author;
import cgk.bibliothouris.learning.service.entity.Book;
import cgk.bibliothouris.learning.service.entity.BookCategory;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

public class BookTestFixture {

    public static Set<Author> createOneAuthor() {
        Author author = Author.AuthorBuilder.author().withFirstName("Robert C.").withLastName("Martin").build();

        Set<Author> authors = new HashSet<>();
        authors.add(author);

        return authors;
    }

    public static Set<BookCategory> createOneCategory() {
        BookCategory category = BookCategory.CategoryBuilder.category().withCategory("Science").build();

        Set<BookCategory> categories = new HashSet<>();
        categories.add(category);

        return categories;
    }

    public static Set<BookCategory> createThreeCategories() {
        BookCategory category = BookCategory.CategoryBuilder.category().withCategory("Science").build();
        BookCategory category2 = BookCategory.CategoryBuilder.category().withCategory("Programming").build();
        BookCategory category3 = BookCategory.CategoryBuilder.category().withCategory("Agile").build();

        Set<BookCategory> categories = new HashSet<>();
        categories.add(category);
        categories.add(category2);
        categories.add(category3);

        return categories;
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

    public static Set<Author> createOneAuthorWithoutLastName() {
        Author author = Author.AuthorBuilder.author().withFirstName("Robert C.").build();

        Set<Author> authors = new HashSet<>();
        authors.add(author);

        return authors;
    }

    public static Book createBookWithOneAuthorAndOneCategory() {
        return Book.BookBuilder.book().withTitle("Clean Code - version 1")
                .withAuthors(createOneAuthor())
                .withIsbn("978-0-13-235088-4")
                .withDescription("Great book for learning how to write clean code")
                .withCategories(createOneCategory())
                .withPages(430)
                .withDate(LocalDate.of(2013, Month.MARCH, 23))
                .withPublisher("Prentice Hall")
                .build();
    }

    public static Book createBookWithOneAuthorAndThreeCategories() {
        return Book.BookBuilder.book().withTitle("Refactoring")
                .withAuthors(createOneAuthor())
                .withIsbn("978-0-13-235088-5")
                .withDescription("Great book for learning how to write clean code")
                .withCategories(createThreeCategories())
                .withPages(430)
                .withDate(LocalDate.of(2013, Month.MARCH, 23))
                .withPublisher("Prentice Hall")
                .build();
    }

    public static Book createBookWithoutISBN() {
        return Book.BookBuilder.book().withTitle("Clean Code")
                .withAuthors(createOneAuthor())
                .withDescription("Great book for learning how to write clean code")
                .withCategories(createThreeCategories())
                .withPages(430)
                .withDate(LocalDate.of(2013, Month.MARCH, 23))
                .withPublisher("Prentice Hall")
                .build();
    }

    public static Book createBookWith10DigitsISBN() {
        return Book.BookBuilder.book().withTitle("Clean Code")
                .withAuthors(createOneAuthor())
                .withIsbn("978-0-13-235-4")
                .withDescription("Great book for learning how to write clean code")
                .withCategories(createThreeCategories())
                .withPages(430)
                .withDate(LocalDate.of(2013, Month.MARCH, 23))
                .withPublisher("Prentice Hall")
                .build();
    }

    public static Book createBookWith976FirstDigitsISBN() {
        return Book.BookBuilder.book().withTitle("Clean Code")
                .withAuthors(createOneAuthor())
                .withIsbn("976-0-13-235088-4")
                .withDescription("Great book for learning how to write clean code")
                .withCategories(createThreeCategories())
                .withPages(430)
                .withDate(LocalDate.of(2013, Month.MARCH, 23))
                .withPublisher("Prentice Hall")
                .build();
    }

    public static Book createBookWith5LinesISBN() {
        return Book.BookBuilder.book().withTitle("Clean Code")
                .withAuthors(createOneAuthor())
                .withIsbn("978-0-13-2350-88-4")
                .withDescription("Great book for learning how to write clean code")
                .withCategories(createThreeCategories())
                .withPages(430)
                .withDate(LocalDate.of(2013, Month.MARCH, 23))
                .withPublisher("Prentice Hall")
                .build();
    }

    public static Book createBookWith3LinesISBN() {
        return Book.BookBuilder.book().withTitle("Clean Code")
                .withAuthors(createOneAuthor())
                .withIsbn("978-0-3235088-4")
                .withDescription("Great book for learning how to write clean code")
                .withCategories(createThreeCategories())
                .withPages(430)
                .withDate(LocalDate.of(2013, Month.MARCH, 23))
                .withPublisher("Prentice Hall")
                .build();
    }

    public static Book createBookWithoutLinesISBN() {
        return Book.BookBuilder.book().withTitle("Clean Code")
                .withAuthors(createOneAuthor())
                .withIsbn("9780132350884")
                .withDescription("Great book for learning how to write clean code")
                .withCategories(createThreeCategories())
                .withPages(430)
                .withDate(LocalDate.of(2013, Month.MARCH, 23))
                .withPublisher("Prentice Hall")
                .build();
    }

    public static Book createBookWithoutCategory() {
        return Book.BookBuilder.book().withTitle("Clean Code")
                .withIsbn("978-0-201-63361-0")
                .withAuthors(createOneAuthor())
                .withDescription("Great book for learning how to write clean code")
                .withPages(430)
                .withDate(LocalDate.of(2013, Month.MARCH, 23))
                .withPublisher("Prentice Hall")
                .build();
    }

    public static Book createBookWithNegativePageNumber() {
        return Book.BookBuilder.book().withTitle("Clean Code")
                .withIsbn("978-0-201-63361-0")
                .withAuthors(createOneAuthor())
                .withDescription("Great book for learning how to write clean code")
                .withCategories(createThreeCategories())
                .withPages(-4)
                .withDate(LocalDate.of(2013, Month.MARCH, 23))
                .withPublisher("Prentice Hall")
                .build();
    }

    public static Book createBookWithoutTitle() {
        return Book.BookBuilder.book().withIsbn("978-0-201-63361-0")
                .withAuthors(createOneAuthor())
                .withDescription("Great book for learning how to write clean code")
                .withCategories(createThreeCategories())
                .withPages(430)
                .withDate(LocalDate.of(2013, Month.MARCH, 23))
                .withPublisher("Prentice Hall")
                .build();
    }

    public static Book createBookWithOneAuthorWithoutLastName() {
        return Book.BookBuilder.book().withIsbn("978-0-201-63361-0")
                .withAuthors(createOneAuthorWithoutLastName())
                .withDescription("Great book for learning how to write clean code")
                .withCategories(createThreeCategories())
                .withPages(430)
                .withDate(LocalDate.of(2013, Month.MARCH, 23))
                .withPublisher("Prentice Hall")
                .build();
    }

    public static Book createBookWithFourAuthorsAndThreeCategories() {
        return Book.BookBuilder.book().withTitle("Clean Code")
                .withAuthors(createFourAuthors())
                .withIsbn("978-0-13-235088-4")
                .withDescription("Great book for learning how to write clean code")
                .withCategories(createThreeCategories())
                .withPages(430)
                .withDate(LocalDate.of(2013, Month.MARCH, 23))
                .withPublisher("Prentice Hall")
                .build();
    }

}