package cgk.bibliothouris.learning.application.transferobject;

import cgk.bibliothouris.learning.service.entity.Book;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement()
public class EntityContainer {

    private String message;

    private List<Book> books;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
