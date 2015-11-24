package cgk.bibliothouris.learning.service.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = Book.LIST_ALL_BOOKS, query = "SELECT b FROM Book b"),
        @NamedQuery(name = Book.DELETE_ALL_BOOKS, query = "DELETE FROM Book b")
})
public class Book {

    public static final String LIST_ALL_BOOKS = "LIST_ALL_BOOKS";
    public static final String DELETE_ALL_BOOKS = "DELETE_ALL_BOOKS";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_sequence")
    @SequenceGenerator(name = "book_sequence", sequenceName = "BOOK_SEQUENCE", allocationSize = 1)
    @Column(name = "BOOK_ID")
    private Integer id;

    @Column(name = "ISBN")
    private String isbn;

    @Column(name = "TITLE")
    private String title;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "BOOK_AUTHOR",
            joinColumns = @JoinColumn(name = "BOOK_ID"),
            inverseJoinColumns = @JoinColumn(name = "AUTHOR_ID")
    )
    private List<Author> authors;

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

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> listOfAuthors) {
        this.authors = listOfAuthors;
    }

    public static class BookBuilder {
        private Book book;

        private BookBuilder() {
            book = new Book();
            book.setAuthors(new ArrayList<>());
        }

        public BookBuilder withIsbn(String isbn) {
            book.isbn = isbn;
            return this;
        }

        public BookBuilder withTitle(String title) {
            book.title = title;
            return this;
        }

        public BookBuilder withAuthors(List<Author> authors) {
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
