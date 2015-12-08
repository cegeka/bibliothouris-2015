package cgk.bibliothouris.learning.application.transferobject;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class BookListingTO {

    private List<BookTO> books;

    private Long booksCount;

    public BookListingTO() {}

    public BookListingTO(List<BookTO> books, Long booksCount) {
        this.books = books;
        this.booksCount = booksCount;
    }

    public List<BookTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookTO> books) {
        this.books = books;
    }

    public Long getBooksCount() {
        return booksCount;
    }

    public void setBooksCount(Long booksCount) {
        this.booksCount = booksCount;
    }
}