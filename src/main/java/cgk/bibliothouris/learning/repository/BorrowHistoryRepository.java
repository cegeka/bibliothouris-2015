package cgk.bibliothouris.learning.repository;

import cgk.bibliothouris.learning.application.transferobject.BookListingTO;
import cgk.bibliothouris.learning.application.transferobject.DetailedBorrowHistoryTO;
import cgk.bibliothouris.learning.application.transferobject.MemberBorrowHistoryTO;
import cgk.bibliothouris.learning.service.entity.BorrowHistoryItem;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

    public BookListingTO<MemberBorrowHistoryTO> findBorrowedBooksByMember(String memberUID, Integer start, Integer end) {
        TypedQuery<BorrowHistoryItem> query = entityManager.createNamedQuery(BorrowHistoryItem.LIST_ALL_MEMBER_BORROWED_BOOKS, BorrowHistoryItem.class);
        query.setParameter("uuid", memberUID)
             .setMaxResults(end - start)
             .setFirstResult(start);

        List<BorrowHistoryItem> borrowHistoryItems = query.getResultList();
        Long countBorrowedBooksByMember = countBorrowedBooksByMember(memberUID);

        List<MemberBorrowHistoryTO> borrowedBooksByMember =
                borrowHistoryItems
                    .stream()
                    .map(item -> {
                        MemberBorrowHistoryTO memberBorrowHistoryTO = new MemberBorrowHistoryTO(item);
                        memberBorrowHistoryTO.setDueDate(item.getStartDate().plusDays(ALLOWED_BORROW_DAYS_NUMBER.longValue()));
                        memberBorrowHistoryTO.setOverdue(memberBorrowHistoryTO.getDueDate().until(LocalDate.now(), ChronoUnit.DAYS));
                        return memberBorrowHistoryTO;
                    })
                    .collect(Collectors.toList());

        return new BookListingTO(borrowedBooksByMember, countBorrowedBooksByMember);
    }

    public Long countBorrowedBooksByMember(String memberUID) {
        TypedQuery<Long> query = entityManager.createNamedQuery(BorrowHistoryItem.COUNT_ALL_MEMBER_BORROWED_BOOKS, Long.class);
        query.setParameter("uuid", memberUID);

        return query.getSingleResult();
    }

    public BookListingTO<DetailedBorrowHistoryTO> getBorrowedBooks(Integer start, Integer end, String sort, String order) {
        TypedQuery<BorrowHistoryItem> query = entityManager.createQuery("SELECT b from BorrowHistoryItem b where (b.endDate IS NULL)" + generateSortQueryClause(sort, order), BorrowHistoryItem.class);
        query.setMaxResults(end - start).setFirstResult(start);

        List<BorrowHistoryItem> borrowHistoryItems = query.getResultList();
        Long countBorrowedBooks = countBorrowedBooks();

        List<DetailedBorrowHistoryTO> borrowedBookTOS =
                borrowHistoryItems
                    .stream()
                    .map(item -> {
                        DetailedBorrowHistoryTO detailedBorrowHistoryTO = new DetailedBorrowHistoryTO(item);
                        detailedBorrowHistoryTO.setDueDate(item.getStartDate().plusDays(ALLOWED_BORROW_DAYS_NUMBER.longValue()));
                        detailedBorrowHistoryTO.setOverdue(detailedBorrowHistoryTO.getDueDate().until(LocalDate.now(), ChronoUnit.DAYS));
                        return detailedBorrowHistoryTO;
                    })
                    .collect(Collectors.toList());

        return new BookListingTO(borrowedBookTOS, countBorrowedBooks);
    }
    //TODO make the methods private
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

        List<DetailedBorrowHistoryTO> overdueBooksTOS =
                overdueBooks
                    .stream()
                    .map(item -> {
                        DetailedBorrowHistoryTO detailedBorrowHistoryTO = new DetailedBorrowHistoryTO(item);
                        detailedBorrowHistoryTO.setDueDate(item.getStartDate().plusDays(ALLOWED_BORROW_DAYS_NUMBER.longValue()));
                        detailedBorrowHistoryTO.setOverdue(detailedBorrowHistoryTO.getDueDate().until(LocalDate.now(), ChronoUnit.DAYS));
                        return detailedBorrowHistoryTO;
                    })
                    .collect(Collectors.toList());

        return new BookListingTO(overdueBooksTOS, overdueBooksCount);
    }

    private Long countOverdueBooks() {
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