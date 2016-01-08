package cgk.bibliothouris.learning.application.transferobject;

import cgk.bibliothouris.learning.service.converter.LocalDateAdapter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlRootElement
public class BorrowHistoryItemTO {

    private Integer bookId;

    private String memberUuid;

    private LocalDate date;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getMemberUuid() {
        return memberUuid;
    }

    public void setMemberUuid(String memberUuid) {
        this.memberUuid = memberUuid;
    }

    public LocalDate getDate() {
        return date;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public static class BorrowHistoryItemTOBuilder {
        private BorrowHistoryItemTO borrowHistoryItemTO;

        private BorrowHistoryItemTOBuilder() {
            borrowHistoryItemTO = new BorrowHistoryItemTO();
        }

        public BorrowHistoryItemTOBuilder withBookId(Integer bookId) {
            borrowHistoryItemTO.bookId = bookId;
            return this;
        }

        public BorrowHistoryItemTOBuilder withMemberUuid(String memberUuid) {
            borrowHistoryItemTO.memberUuid = memberUuid;
            return this;
        }

        public BorrowHistoryItemTOBuilder withStartDate(LocalDate startDate) {
            borrowHistoryItemTO.date = startDate;
            return this;
        }

        public static BorrowHistoryItemTOBuilder borrowHistoryItemTO() {
            return new BorrowHistoryItemTOBuilder();
        }

        public BorrowHistoryItemTO build() {
            return borrowHistoryItemTO;
        }
    }
}
