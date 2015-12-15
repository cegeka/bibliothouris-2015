package cgk.bibliothouris.learning.application.transferobject;

import cgk.bibliothouris.learning.service.dateconverter.LocalDateAdapter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlRootElement
public class BorrowHistoryItemTO {

    private Integer bookId;

    private String memberUuid;

    private LocalDate startDate;

    private LocalDate endDate;

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

    public LocalDate getStartDate() {
        return startDate;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public static class BorrowHistoryItemTOBuilder
    {
        private BorrowHistoryItemTO borrowHistoryItemTO;

        private BorrowHistoryItemTOBuilder()
        {
            borrowHistoryItemTO = new BorrowHistoryItemTO();
        }

        public BorrowHistoryItemTOBuilder withBookId(Integer bookId)
        {
            borrowHistoryItemTO.bookId = bookId;
            return this;
        }

        public BorrowHistoryItemTOBuilder withMemberUuid(String memberUuid)
        {
            borrowHistoryItemTO.memberUuid = memberUuid;
            return this;
        }

        public BorrowHistoryItemTOBuilder withStartDate(LocalDate startDate)
        {
            borrowHistoryItemTO.startDate = startDate;
            return this;
        }

        public BorrowHistoryItemTOBuilder withEndDate(LocalDate endDate)
        {
            borrowHistoryItemTO.endDate = endDate;
            return this;
        }

        public static BorrowHistoryItemTOBuilder borrowHistoryItemTO()
        {
            return new BorrowHistoryItemTOBuilder();
        }

        public BorrowHistoryItemTO build()
        {
            return borrowHistoryItemTO;
        }
    }
}
