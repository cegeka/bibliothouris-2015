package cgk.bibliothouris.learning.service.entity;

import cgk.bibliothouris.learning.service.converter.LocalDateAdapter;
import cgk.bibliothouris.learning.service.converter.LocalDateAttributeConverter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(name = Book.LIST_ALL_BOOKS, query = "SELECT b FROM Book b ORDER BY LOWER(b.title)"),
        @NamedQuery(name = Book.LIST_ALL_AVAILABLE_BOOKS, query = "SELECT b FROM Book b WHERE b.id NOT IN (SELECT bHist.book.id FROM BorrowHistoryItem bHist WHERE bHist.endDate IS NULL)"),
        @NamedQuery(name = Book.LIST_BOOK_BY_ISBN, query = "SELECT b FROM Book b WHERE b.isbn = :isbn"),
        @NamedQuery(name = Book.DELETE_ALL_BOOKS, query = "DELETE FROM Book b"),
        @NamedQuery(name = Book.COUNT_BOOKS, query = "SELECT COUNT(b.id) FROM Book b"),
        @NamedQuery(name = Book.COUNT_AVAILABLE_BOOKS, query = "SELECT COUNT(b.id) FROM Book b WHERE b.id NOT IN (SELECT bHist.book.id FROM BorrowHistoryItem bHist WHERE bHist.endDate IS NULL)"),
        @NamedQuery(name = Book.GET_BOOK_TITLES, query = "SELECT DISTINCT new cgk.bibliothouris.learning.application.transferobject.StringTO(b.title) FROM Book b"),
        @NamedQuery(name = Book.GET_BOOK_ISBN_CODES, query = "SELECT DISTINCT new cgk.bibliothouris.learning.application.transferobject.StringTO(b.isbn) FROM Book b"),
        @NamedQuery(name = Book.COUNT_ALL_CURRENTLY_BORROWED_BOOKS, query = "SELECT COUNT(bHist.book.id) FROM BorrowHistoryItem bHist WHERE (bHist.member.UUID = :memberId) AND (bHist.endDate IS NULL)")
})
@XmlRootElement
public class Book {

    public static final String LIST_ALL_BOOKS = "LIST_ALL_BOOKS";
    public static final String LIST_ALL_AVAILABLE_BOOKS = "LIST_ALL_AVAILABLE_BOOKS";
    public static final String LIST_BOOK_BY_ISBN = "LIST_BOOK_BY_ISBN";
    public static final String DELETE_ALL_BOOKS = "DELETE_ALL_BOOKS";
    public static final String COUNT_BOOKS = "COUNT_BOOKS";
    public static final String COUNT_AVAILABLE_BOOKS = "COUNT_AVAILABLE_BOOKS";
    public static final String GET_BOOK_TITLES = "GET_BOOK_TITLES";
    public static final String GET_BOOK_ISBN_CODES = "GET_BOOK_ISBN_CODES";
    public static final String COUNT_ALL_CURRENTLY_BORROWED_BOOKS = "COUNT_ALL_CURRENTLY_BORROWED_BOOKS";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_sequence")
    @SequenceGenerator(name = "book_sequence", sequenceName = "BOOK_SEQUENCE", allocationSize = 1)
    @Column(name = "BOOK_ID")
    private Integer id;

    @Column(name = "ISBN")
    @NotBlank(message = "Book ISBN is empty or is missing")
    @Pattern(regexp="^(?=[0-9]{13}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)97[89][- ][0-9]{1,5}[- ][0-9]+[- ][0-9]+[- ][0-9]$", message="Book ISBN is wrong")
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

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "BOOK_CATEGORY",
            joinColumns = @JoinColumn(name = "BOOK_ID"),
            inverseJoinColumns = @JoinColumn(name = "CATEGORY_ID"))
    @NotNull
    private Set<BookCategory> categories;

    @Column(name = "PAGES")
    @Range(min=1, max=99999)
    private Integer pages;

    @Column(name = "PUBLICATION_DATE")
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate date;

    @Column(name = "PUBLISHER")
    @Size(max = 250, message = "Book publisher name is too long")
    private String publisher;

    @Column(name = "COVER")
    private String cover;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<BookCategory> getCategories() {
        return categories;
    }

    public void setCategories(Set<BookCategory> categories) {
        this.categories = categories;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public LocalDate getDate() {
        return date;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (id != null ? !id.equals(book.id) : book.id != null) return false;

        return true;
    }

   public static class BookBuilder
    {
        private Book book;

        private BookBuilder()
        {
            book = new Book();
        }

        public BookBuilder withId(Integer id)
        {
            book.id = id;
            return this;
        }

        public BookBuilder withIsbn(String isbn)
        {
            book.isbn = isbn;
            return this;
        }

        public BookBuilder withTitle(String title)
        {
            book.title = title;
            return this;
        }

        public BookBuilder withAuthors(Set<Author> authors)
        {
            book.authors = authors;
            return this;
        }

        public BookBuilder withDescription(String description)
        {
            book.description = description;
            return this;
        }

        public BookBuilder withCategories(Set<BookCategory> categories)
        {
            book.categories = categories;
            return this;
        }

        public BookBuilder withPages(Integer pages)
        {
            book.pages = pages;
            return this;
        }

        public BookBuilder withDate(LocalDate date)
        {
            book.date = date;
            return this;
        }

        public BookBuilder withPublisher(String publisher)
        {
            book.publisher = publisher;
            return this;
        }

        public BookBuilder withCover(String cover)
        {
            book.cover = cover;
            return this;
        }

        public static BookBuilder book()
        {
            return new BookBuilder();
        }

        public Book build()
        {
            return book;
        }
    }
}
