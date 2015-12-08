package cgk.bibliothouris.learning.application.transferobject;

import cgk.bibliothouris.learning.service.entity.Author;
import cgk.bibliothouris.learning.service.entity.Book;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement
public class BookListingTO {

    private Integer id;

    private String isbn;

    private String title;

    private Set<Author> authors;

    public BookListingTO() {}

    public BookListingTO(Book book) {
        this.id = book.getId();
        this.isbn = book.getIsbn();
        this.title = book.getTitle();
        this.authors = book.getAuthors();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookListingTO book = (BookListingTO) o;

        return !(id != null ? !id.equals(book.id) : book.id != null);
    }

    public static class BookListingTOBuilder {
        private BookListingTO bookListingTO;

        private BookListingTOBuilder() {
            bookListingTO = new BookListingTO();
        }

        public BookListingTOBuilder withId(Integer id) {
            bookListingTO.id = id;
            return this;
        }

        public BookListingTOBuilder withIsbn(String isbn) {
            bookListingTO.isbn = isbn;
            return this;
        }

        public BookListingTOBuilder withTitle(String title) {
            bookListingTO.title = title;
            return this;
        }

        public BookListingTOBuilder withAuthors(Set<Author> authors) {
            bookListingTO.authors = authors;
            return this;
        }

        public static BookListingTOBuilder bookListingTO() {
            return new BookListingTOBuilder();
        }

        public BookListingTO build() {
            return bookListingTO;
        }
    }
}