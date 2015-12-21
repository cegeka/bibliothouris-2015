package cgk.bibliothouris.learning.repository;

import cgk.bibliothouris.learning.application.transferobject.BookListingTO;
import cgk.bibliothouris.learning.application.transferobject.DetailedBorrowHistoryTO;
import cgk.bibliothouris.learning.application.transferobject.MemberBorrowHistoryTO;
import cgk.bibliothouris.learning.service.entity.BorrowHistoryItem;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BorrowHistoryRepository {

    public static final Double ALLOWED_BORROW_DAYS_NUMBER = 21.0;

    @PersistenceContext
    private EntityManager entityManager;

    public BorrowHistoryItem addBorrowedBook(BorrowHistoryItem historyItem) {
        historyItem.getMember().getHistory().add(historyItem);

        entityManager.persist(historyItem);

        return historyItem;
    }

    public List<MemberBorrowHistoryTO> findBorrowedBooksByMember(String memberUID, Integer start, Integer end) {
        TypedQuery<BorrowHistoryItem> query = entityManager.createNamedQuery(BorrowHistoryItem.LIST_ALL_MEMBER_BORROWED_BOOKS, BorrowHistoryItem.class);
        query.setParameter("uuid", memberUID)
             .setMaxResults(end - start)
             .setFirstResult(start);

        List<BorrowHistoryItem> borrowHistoryItems = query.getResultList();

        return borrowHistoryItems.stream().map(MemberBorrowHistoryTO::new).collect(Collectors.toList());
    }

    public Long countBorrowedBooksByMember(String memberUID) {
        TypedQuery<Long> query = entityManager.createNamedQuery(BorrowHistoryItem.COUNT_ALL_MEMBER_BORROWED_BOOKS, Long.class);
        query.setParameter("uuid", memberUID);

        return query.getSingleResult();
    }

    public List<DetailedBorrowHistoryTO> getBorrowedBooks(Integer start, Integer end, String sort, String order) {
        TypedQuery<BorrowHistoryItem> query = entityManager.createQuery("SELECT b from BorrowHistoryItem b where (b.endDate IS NULL)" + generateSortQueryClause(sort, order), BorrowHistoryItem.class);
        query.setMaxResults(end - start).setFirstResult(start);

        List<BorrowHistoryItem> borrowHistoryItems = query.getResultList();

        return borrowHistoryItems.stream().map(DetailedBorrowHistoryTO::new).collect(Collectors.toList());
    }

    public Long countBorrowedBooks() {
        TypedQuery<Long> query = entityManager.createNamedQuery(BorrowHistoryItem.COUNT_ALL_BORROWED_BOOKS, Long.class);

        return query.getSingleResult();
    }

    public BookListingTO<DetailedBorrowHistoryTO> getOverdueBooks(Integer start, Integer end, String sort, String order) {
        TypedQuery<BorrowHistoryItem> query = entityManager.createQuery("SELECT b from BorrowHistoryItem b where (CURRENT_DATE - to_date(b.startDate) > :daysNo)" + generateSortQueryClause(sort, order), BorrowHistoryItem.class);
        query.setParameter("daysNo", ALLOWED_BORROW_DAYS_NUMBER);
        query.setMaxResults(end - start).setFirstResult(start);


        List<BorrowHistoryItem> overdueBooks = query.getResultList();
        Long overdueBooksCount = countOverdueBooks();

        List<DetailedBorrowHistoryTO> bookTOS = overdueBooks.stream().map(book -> new DetailedBorrowHistoryTO(book)).collect(Collectors.toList());

        return new BookListingTO(bookTOS, overdueBooksCount);
    }

    public Long countOverdueBooks() {
        TypedQuery<Long> query = entityManager.createNamedQuery(BorrowHistoryItem.COUNT_ALL_OVERDUE_BOOKS, Long.class);
        query.setParameter("daysNo", ALLOWED_BORROW_DAYS_NUMBER);

        return query.getSingleResult();
    }

    private String generateSortQueryClause(String sortCriteria, String sortOrder) {
        String sortClause = "ORDER BY ";

        if (sortCriteria == null)
            return sortClause + "lower(b.book.title)";

        switch (sortCriteria) {
            case "borrower":
                return sortClause + "lower(b.member.firstName)" + " " + sortOrder + ", lower(b.member.lastName)" + " " + sortOrder;
            case "title":
                return sortClause + "lower(b.book.title)" + " " + sortOrder;
            case "isbn":
                return sortClause + "b.book.isbn" + " " + sortOrder;
            case "date":
                return sortClause + "b.startDate" + " " + sortOrder;
            default:
                return sortClause + "lower(b.book.title)";
        }
    }
}