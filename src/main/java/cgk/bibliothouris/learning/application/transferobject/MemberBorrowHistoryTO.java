package cgk.bibliothouris.learning.application.transferobject;

import cgk.bibliothouris.learning.service.dateconverter.LocalDateAdapter;
import cgk.bibliothouris.learning.service.entity.Author;
import cgk.bibliothouris.learning.service.entity.BorrowHistoryItem;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.Set;

@XmlRootElement
public class MemberBorrowHistoryTO {

    private String isbn;

    private String title;

    private Set<Author> authors;

    private LocalDate startLendDate;

    private LocalDate endLendDate;

    private LocalDate dueDate;

    private Long overdue;

    public MemberBorrowHistoryTO() {}

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

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public void setStartLendDate(LocalDate startLendDate) {
        this.startLendDate = startLendDate;
    }

    public LocalDate getEndLendDate() {
        return endLendDate;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public void setEndLendDate(LocalDate endLendDate) {
        this.endLendDate = endLendDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Long getOverdue() {
        return overdue;
    }

    public void setOverdue(Long overdue) {
        this.overdue = overdue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemberBorrowHistoryTO that = (MemberBorrowHistoryTO) o;

        if (authors != null ? !authors.equals(that.authors) : that.authors != null) return false;
        if (endLendDate != null ? !endLendDate.equals(that.endLendDate) : that.endLendDate != null) return false;
        if (isbn != null ? !isbn.equals(that.isbn) : that.isbn != null) return false;
        if (startLendDate != null ? !startLendDate.equals(that.startLendDate) : that.startLendDate != null)
            return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = isbn != null ? isbn.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (authors != null ? authors.hashCode() : 0);
        result = 31 * result + (startLendDate != null ? startLendDate.hashCode() : 0);
        result = 31 * result + (endLendDate != null ? endLendDate.hashCode() : 0);
        return result;
    }

    public static class MemberBorrowHistoryTOBuilder {

        private MemberBorrowHistoryTO memberBorrowHistoryTO;

        private MemberBorrowHistoryTOBuilder()
        {
            memberBorrowHistoryTO = new MemberBorrowHistoryTO();
        }

        public MemberBorrowHistoryTOBuilder withIsbn(String isbn)
        {
            memberBorrowHistoryTO.isbn = isbn;
            return this;
        }

        public MemberBorrowHistoryTOBuilder withTitle(String title)
        {
            memberBorrowHistoryTO.title = title;
            return this;
        }

        public MemberBorrowHistoryTOBuilder withAuthors(Set<Author> authors)
        {
            memberBorrowHistoryTO.authors = authors;
            return this;
        }

        public MemberBorrowHistoryTOBuilder withStartLendDate(LocalDate startLendDate)
        {
            memberBorrowHistoryTO.startLendDate = startLendDate;
            return this;
        }

        public MemberBorrowHistoryTOBuilder withEndLendDate(LocalDate endLendDate)
        {
            memberBorrowHistoryTO.endLendDate = endLendDate;
            return this;
        }

        public MemberBorrowHistoryTOBuilder withDueDate(LocalDate dueDate)
        {
            memberBorrowHistoryTO.dueDate = dueDate;
            return this;
        }

        public MemberBorrowHistoryTOBuilder withOverdue(Long overdue)
        {
            memberBorrowHistoryTO.overdue = overdue;
            return this;
        }

        public static MemberBorrowHistoryTOBuilder memberBorrowHistoryTO()
        {
            return new MemberBorrowHistoryTOBuilder();
        }

        public MemberBorrowHistoryTO build()
        {
            return memberBorrowHistoryTO;
        }
    }
}
