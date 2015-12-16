package cgk.bibliothouris.learning.application.transferobject;

import cgk.bibliothouris.learning.service.dateconverter.LocalDateAdapter;
import cgk.bibliothouris.learning.service.entity.Author;
import cgk.bibliothouris.learning.service.entity.BorrowHistoryItem;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.Set;

@XmlRootElement
public class GlobalBorrowHistoryTO {

    private String isbn;

    private String title;

    private String borrowerFirstName;

    private String borrowerLastName;

    private String borrowerUUID;

    private Set<Author> authors;

    private LocalDate startLendDate;

    private LocalDate endLendDate;

    public GlobalBorrowHistoryTO() {
    }

    public GlobalBorrowHistoryTO(BorrowHistoryItem borrowHistoryItem) {
        this.isbn = borrowHistoryItem.getBook().getIsbn();
        this.title = borrowHistoryItem.getBook().getTitle();
        this.authors = borrowHistoryItem.getBook().getAuthors();
        this.startLendDate = borrowHistoryItem.getStartDate();
        this.endLendDate = borrowHistoryItem.getEndDate();
        this.borrowerFirstName = borrowHistoryItem.getMember().getFirstName();
        this.borrowerLastName = borrowHistoryItem.getMember().getLastName();
        this.borrowerUUID = borrowHistoryItem.getMember().getUUID();
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

    public String getBorrowerFirstName() {
        return borrowerFirstName;
    }

    public void setBorrowerFirstName(String borrowerFirstName) {
        this.borrowerFirstName = borrowerFirstName;
    }

    public String getBorrowerLastName() {
        return borrowerLastName;
    }

    public void setBorrowerLastName(String borrowerLastName) {
        this.borrowerLastName = borrowerLastName;
    }

    public String getBorrowerUUID() {
        return borrowerUUID;
    }

    public void setBorrowerUUID(String borrowerUUID) {
        this.borrowerUUID = borrowerUUID;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public void setEndLendDate(LocalDate endLendDate) {
        this.endLendDate = endLendDate;
    }

    public static class GlobalBorrowHistoryTOBuilder {
        private GlobalBorrowHistoryTO globalBorrowHistoryTO;

        private GlobalBorrowHistoryTOBuilder() {
            globalBorrowHistoryTO = new GlobalBorrowHistoryTO();
        }

        public GlobalBorrowHistoryTOBuilder withIsbn(String isbn) {
            globalBorrowHistoryTO.isbn = isbn;
            return this;
        }

        public GlobalBorrowHistoryTOBuilder withTitle(String title) {
            globalBorrowHistoryTO.title = title;
            return this;
        }

        public GlobalBorrowHistoryTOBuilder withBorrowerFirstName(String borrowerFirstName) {
            globalBorrowHistoryTO.borrowerFirstName = borrowerFirstName;
            return this;
        }

        public GlobalBorrowHistoryTOBuilder withBorrowerLastName(String borrowerLastName) {
            globalBorrowHistoryTO.borrowerLastName = borrowerLastName;
            return this;
        }

        public GlobalBorrowHistoryTOBuilder withBorrowerUUID(String borrowerUUID) {
            globalBorrowHistoryTO.borrowerUUID = borrowerUUID;
            return this;
        }

        public GlobalBorrowHistoryTOBuilder withAuthors(Set<Author> authors) {
            globalBorrowHistoryTO.authors = authors;
            return this;
        }

        public GlobalBorrowHistoryTOBuilder withStartLendDate(LocalDate startLendDate) {
            globalBorrowHistoryTO.startLendDate = startLendDate;
            return this;
        }

        public GlobalBorrowHistoryTOBuilder withEndLendDate(LocalDate endLendDate) {
            globalBorrowHistoryTO.endLendDate = endLendDate;
            return this;
        }

        public static GlobalBorrowHistoryTOBuilder globalBorrowHistoryTO() {
            return new GlobalBorrowHistoryTOBuilder();
        }

        public GlobalBorrowHistoryTO build() {
            return globalBorrowHistoryTO;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GlobalBorrowHistoryTO that = (GlobalBorrowHistoryTO) o;

        if (authors != null ? !authors.equals(that.authors) : that.authors != null) return false;
        if (borrowerFirstName != null ? !borrowerFirstName.equals(that.borrowerFirstName) : that.borrowerFirstName != null)
            return false;
        if (borrowerLastName != null ? !borrowerLastName.equals(that.borrowerLastName) : that.borrowerLastName != null)
            return false;
        if (borrowerUUID != null ? !borrowerUUID.equals(that.borrowerUUID) : that.borrowerUUID != null) return false;
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
        result = 31 * result + (borrowerFirstName != null ? borrowerFirstName.hashCode() : 0);
        result = 31 * result + (borrowerLastName != null ? borrowerLastName.hashCode() : 0);
        result = 31 * result + (borrowerUUID != null ? borrowerUUID.hashCode() : 0);
        result = 31 * result + (authors != null ? authors.hashCode() : 0);
        result = 31 * result + (startLendDate != null ? startLendDate.hashCode() : 0);
        result = 31 * result + (endLendDate != null ? endLendDate.hashCode() : 0);
        return result;
    }
}
