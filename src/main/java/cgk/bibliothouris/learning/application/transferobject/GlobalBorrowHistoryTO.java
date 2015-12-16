package cgk.bibliothouris.learning.application.transferobject;

import cgk.bibliothouris.learning.service.entity.Author;
import cgk.bibliothouris.learning.service.entity.BorrowHistoryItem;
import cgk.bibliothouris.learning.service.entity.Member;

import java.time.LocalDate;
import java.util.Set;

public class GlobalBorrowHistoryTO {

    private String isbn;

    private String title;

    private String borrowerFirstName;

    private String borrowerLastName;

    private String borrowerUUID;

    private Set<Author> authors;

    private LocalDate startLendDate;

    private LocalDate endLendDate;

    public GlobalBorrowHistoryTO() { }

    public GlobalBorrowHistoryTO(BorrowHistoryItem borrowHistoryItem, Member member) {
        this.isbn = borrowHistoryItem.getBook().getIsbn();
        this.title = borrowHistoryItem.getBook().getTitle();
        this.authors = borrowHistoryItem.getBook().getAuthors();
        this.startLendDate = borrowHistoryItem.getStartDate();
        this.endLendDate = borrowHistoryItem.getEndDate();
        this.borrowerFirstName = member.getFirstName();
        this.borrowerLastName = member.getLastName();
        this.borrowerUUID = member.getUUID();
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

   public static class GlobalBorrowHistoryTOBuilder
    {
        private GlobalBorrowHistoryTO globalBorrowHistoryTO;

        private GlobalBorrowHistoryTOBuilder()
        {
            globalBorrowHistoryTO = new GlobalBorrowHistoryTO();
        }

        public GlobalBorrowHistoryTOBuilder withIsbn(String isbn)
        {
            globalBorrowHistoryTO.isbn = isbn;
            return this;
        }

        public GlobalBorrowHistoryTOBuilder withTitle(String title)
        {
            globalBorrowHistoryTO.title = title;
            return this;
        }

        public GlobalBorrowHistoryTOBuilder withBorrowerFirstName(String borrowerFirstName)
        {
            globalBorrowHistoryTO.borrowerFirstName = borrowerFirstName;
            return this;
        }

        public GlobalBorrowHistoryTOBuilder withBorrowerLastName(String borrowerLastName)
        {
            globalBorrowHistoryTO.borrowerLastName = borrowerLastName;
            return this;
        }

        public GlobalBorrowHistoryTOBuilder withBorrowerUUID(String borrowerUUID)
        {
            globalBorrowHistoryTO.borrowerUUID = borrowerUUID;
            return this;
        }

        public GlobalBorrowHistoryTOBuilder withAuthors(Set<Author> authors)
        {
            globalBorrowHistoryTO.authors = authors;
            return this;
        }

        public GlobalBorrowHistoryTOBuilder withStartLendDate(LocalDate startLendDate)
        {
            globalBorrowHistoryTO.startLendDate = startLendDate;
            return this;
        }

        public GlobalBorrowHistoryTOBuilder withEndLendDate(LocalDate endLendDate)
        {
            globalBorrowHistoryTO.endLendDate = endLendDate;
            return this;
        }

        public static GlobalBorrowHistoryTOBuilder globalBorrowHistoryTO()
        {
            return new GlobalBorrowHistoryTOBuilder();
        }

        public GlobalBorrowHistoryTO build()
        {
            return globalBorrowHistoryTO;
        }
    }
}
