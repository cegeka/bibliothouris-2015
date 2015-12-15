package cgk.bibliothouris.learning.service.entity;

import cgk.bibliothouris.learning.service.dateconverter.LocalDateAdapter;
import cgk.bibliothouris.learning.service.dateconverter.LocalDateAttributeConverter;
import org.eclipse.persistence.oxm.annotations.XmlInverseReference;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@Entity
@XmlRootElement
@Table(name = "BORROWED_HISTORY")
public class BorrowHistoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "borrowed_history_sequence")
    @SequenceGenerator(name = "borrowed_history_sequence", sequenceName = "BORROWED_HISTORY_SEQUENCE", allocationSize = 1)
    @Column(name = "HISTORY_ID")
    private Integer id;

    @Column(name = "START_DATE")
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate startDate;

    @Column(name = "END_DATE")
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name="BOOK_ID", referencedColumnName="BOOK_ID")
    private Book book;

    @ManyToOne
    @JoinColumn(name="MEMBER_ID", referencedColumnName="U_ID")
    private Member member;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public static class BorrowedHistoryBuilder
    {
        private BorrowHistoryItem borrowHistoryItem;

        private BorrowedHistoryBuilder()
        {
            borrowHistoryItem = new BorrowHistoryItem();
        }

        public BorrowedHistoryBuilder withId(Integer id)
        {
            borrowHistoryItem.id = id;
            return this;
        }

        public BorrowedHistoryBuilder withStartDate(LocalDate startDate)
        {
            borrowHistoryItem.startDate = startDate;
            return this;
        }

        public BorrowedHistoryBuilder withEndDate(LocalDate endDate)
        {
            borrowHistoryItem.endDate = endDate;
            return this;
        }

        public BorrowedHistoryBuilder withBook(Book book)
        {
            borrowHistoryItem.book = book;
            return this;
        }

        public BorrowedHistoryBuilder withMember(Member member)
        {
            borrowHistoryItem.member = member;
            return this;
        }

        public static BorrowedHistoryBuilder borrowedHistory()
        {
            return new BorrowedHistoryBuilder();
        }

        public BorrowHistoryItem build()
        {
            return borrowHistoryItem;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BorrowHistoryItem that = (BorrowHistoryItem) o;

        if (book != null ? !book.equals(that.book) : that.book != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (member != null ? !member.equals(that.member) : that.member != null) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (book != null ? book.hashCode() : 0);
        result = 31 * result + (member != null ? member.hashCode() : 0);
        return result;
    }
}
