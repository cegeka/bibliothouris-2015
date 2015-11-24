package cgk.bibliothouris.learning.service.entity;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(name = Book.LIST_ALL_BOOKS, query = "SELECT b FROM Book b")
})
@XmlRootElement
public class Book {

    public static final String LIST_ALL_BOOKS = "LIST_ALL_BOOKS";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_sequence")
    @SequenceGenerator(name = "book_sequence", sequenceName = "BOOK_SEQUENCE", allocationSize = 1)
    @Column(name = "BOOK_ID")
    private Integer id;

    @Column(name = "ISBN")
    @NotBlank(message = "Book ISBN is empty or is missing")
    @Size(max = 32, message = "Book ISBN is too long")
    private String isbn;

    @Column(name = "TITLE")
    @NotBlank(message = "Book title is empty or is missing")
    @Size(max = 255, message = "Book title is too long")
    private String title;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "BOOK_AUTHOR",
            joinColumns = @JoinColumn(name = "BOOK_ID"),
            inverseJoinColumns = @JoinColumn(name = "AUTHOR_ID"))
    @Valid
    private Set<Author> authors;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> listOfAuthors) {
        this.authors = listOfAuthors;
    }

    public static class BookBuilder {
        private Book book;

        private BookBuilder() {
            book = new Book();
            book.setAuthors(new HashSet<>());
        }

        public BookBuilder withIsbn(String isbn) {
            book.isbn = isbn;
            return this;
        }

        public BookBuilder withTitle(String title) {
            book.title = title;
            return this;
        }

        public BookBuilder withAuthors(Set<Author> authors) {
            book.authors = authors;
            return this;
        }

        public static BookBuilder book() {
            return new BookBuilder();
        }

        public Book build() {
            return book;
        }
    }
}
