package cgk.bibliothouris.learning.application.transferobject;

import cgk.bibliothouris.learning.service.entity.Author;
import cgk.bibliothouris.learning.service.entity.BorrowHistoryItem;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.Set;

@XmlRootElement
public class MemberBorrowHistoryTO {

    private String isbn;

    private String title;

    private Set<Author> authors;

    private LocalDate startLendDate;

    private LocalDate endLendDate;

    public MemberBorrowHistoryTO() { }

    public MemberBorrowHistoryTO(BorrowHistoryItem borrowHistoryItem) {
        this.isbn = borrowHistoryItem.getBook().getIsbn();
        this.title = borrowHistoryItem.getBook().getTitle();
        this.authors = borrowHistoryItem.getBook().getAuthors();
        this.startLendDate = borrowHistoryItem.getStartDate();
        this.endLendDate = borrowHistoryItem.getEndDate();
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

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public LocalDate getStartLendDate() {
        return startLendDate;
    }

    public void setStartLendDate(LocalDate startLendDate) {
        this.startLendDate = startLendDate;
    }

    public LocalDate getEndLendDate() {
        return endLendDate;
    }

    public void setEndLendDate(LocalDate endLendDate) {
        this.endLendDate = endLendDate;
    }
}
