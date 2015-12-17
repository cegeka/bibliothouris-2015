package cgk.bibliothouris.learning.application.transferobject;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class BookListingTO<T> {

    private List<T> books = new ArrayList<>();

    private Long booksCount;

    public BookListingTO() {}

    public BookListingTO(List<T> books, Long booksCount) {
        this.books = books;
        this.booksCount = booksCount;
    }

    public List<T> getBooks() {
        return books;
    }

    public void setBooks(List<T> books) {
        this.books = books;
    }

    public Long getBooksCount() {
        return booksCount;
    }

    public void setBooksCount(Long booksCount) {
        this.booksCount = booksCount;
    }
}