package cgk.bibliothouris.learning.service.entity;

import cgk.bibliothouris.learning.service.dateconverter.LocalDateAdapter;
import cgk.bibliothouris.learning.service.dateconverter.LocalDateAttributeConverter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@Entity
@XmlRootElement
@Table(name = "BORROWED_HISTORY")
public class BorrowedHistory {

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
        private BorrowedHistory borrowedHistory;

        private BorrowedHistoryBuilder()
        {
            borrowedHistory = new BorrowedHistory();
        }

        public BorrowedHistoryBuilder withId(Integer id)
        {
            borrowedHistory.id = id;
            return this;
        }

        public BorrowedHistoryBuilder withStartDate(LocalDate startDate)
        {
            borrowedHistory.startDate = startDate;
            return this;
        }

        public BorrowedHistoryBuilder withEndDate(LocalDate endDate)
        {
            borrowedHistory.endDate = endDate;
            return this;
        }

        public BorrowedHistoryBuilder withBook(Book book)
        {
            borrowedHistory.book = book;
            return this;
        }

        public BorrowedHistoryBuilder withMember(Member member)
        {
            borrowedHistory.member = member;
            return this;
        }

        public static BorrowedHistoryBuilder borrowedHistory()
        {
            return new BorrowedHistoryBuilder();
        }

        public BorrowedHistory build()
        {
            return borrowedHistory;
        }
    }
}
