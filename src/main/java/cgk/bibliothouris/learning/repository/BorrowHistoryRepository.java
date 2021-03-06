package cgk.bibliothouris.learning.repository;

import cgk.bibliothouris.learning.application.transferobject.DetailedBorrowHistoryTO;
import cgk.bibliothouris.learning.application.transferobject.ItemsListingTO;
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

    public BorrowHistoryItem updateBorrowedBook(BorrowHistoryItem borrowHistoryItem) {
        entityManager.persist(borrowHistoryItem);

        return borrowHistoryItem;
    }

    public BorrowHistoryItem findBorrowHistoryItemById(Integer historyItemId) {
        return entityManager.find(BorrowHistoryItem.class, historyItemId);
    }

    public ItemsListingTO<MemberBorrowHistoryTO> findBorrowedBooksByMember(String memberUID, Integer start, Integer end) {
        List<BorrowHistoryItem> borrowHistoryItems = queryBorrowedBooksByMember(memberUID, start, end);
        Long countBorrowedBooksByMember = countBorrowedBooksByMember(memberUID);
        List<MemberBorrowHistoryTO> borrowedBooksByMember =
                createListOfMemberBorrowHistoryTOsWithDatesSet(borrowHistoryItems);
        return new ItemsListingTO(borrowedBooksByMember, countBorrowedBooksByMember);
    }

    private List<BorrowHistoryItem> queryBorrowedBooksByMember(String memberUID, Integer start, Integer end) {
        TypedQuery<BorrowHistoryItem> query = entityManager.createNamedQuery(
                BorrowHistoryItem.LIST_ALL_MEMBER_BORROWED_BOOKS, BorrowHistoryItem.class);
        query.setParameter("uuid", memberUID)
                .setMaxResults(end - start)
                .setFirstResult(start);
        return query.getResultList();
    }

    private List<MemberBorrowHistoryTO> createListOfMemberBorrowHistoryTOsWithDatesSet(
            List<BorrowHistoryItem> borrowHistoryItems){
        return borrowHistoryItems
                        .stream()
                        .map(item -> {
                            MemberBorrowHistoryTO memberBorrowHistoryTO = new MemberBorrowHistoryTO(item);
                            memberBorrowHistoryTO.setDueDate(item.getStartDate()
                                    .plusDays(ALLOWED_BORROW_DAYS_NUMBER.longValue()));
                            if (memberBorrowHistoryTO.getEndLendDate() == null)
                                memberBorrowHistoryTO.setOverdue(memberBorrowHistoryTO.getDueDate()
                                    .until(LocalDate.now(), ChronoUnit.DAYS));
                            else
                                memberBorrowHistoryTO.setOverdue(memberBorrowHistoryTO.getDueDate()
                                    .until(memberBorrowHistoryTO.getEndLendDate(), ChronoUnit.DAYS));
                            return memberBorrowHistoryTO;
                        })
                        .collect(Collectors.toList());
    }

    public Long countBorrowedBooksByMember(String memberUID) {
        TypedQuery<Long> query = entityManager.createNamedQuery(BorrowHistoryItem.COUNT_ALL_MEMBER_BORROWED_BOOKS,
                Long.class);
        query.setParameter("uuid", memberUID);

        return query.getSingleResult();
    }

    public ItemsListingTO<DetailedBorrowHistoryTO> getBorrowedBooks(Integer start, Integer end,
                                                                    String sort, String order) {
        List<BorrowHistoryItem> borrowHistoryItems = queryBooksWhereEndDateIsNull(start, end, sort, order);
        Long countBorrowedBooks = countBorrowedBooks();

        List<DetailedBorrowHistoryTO> borrowedBookTOS =
                createListOfDetailedBorrowHistoryTOsWithDatesSet(borrowHistoryItems);

        return new ItemsListingTO(borrowedBookTOS, countBorrowedBooks);
    }

    private List<BorrowHistoryItem> queryBooksWhereEndDateIsNull(Integer start, Integer end,
                                                                 String sort, String order) {
        TypedQuery<BorrowHistoryItem> query = entityManager.createQuery(
                        "SELECT b from BorrowHistoryItem b where (b.endDate IS NULL)" +
                        generateSortQueryClause(sort, order), BorrowHistoryItem.class);
        query.setMaxResults(end - start).setFirstResult(start);
        return query.getResultList();
    }

    private List<DetailedBorrowHistoryTO> createListOfDetailedBorrowHistoryTOsWithDatesSet(
            List<BorrowHistoryItem> borrowHistoryItems) {
        return borrowHistoryItems
                    .stream()
                    .map(item -> {
                        DetailedBorrowHistoryTO detailedBorrowHistoryTO = new DetailedBorrowHistoryTO(item);
                        detailedBorrowHistoryTO.setDueDate(item.getStartDate()
                                .plusDays(ALLOWED_BORROW_DAYS_NUMBER.longValue()));
                        detailedBorrowHistoryTO.setOverdue(detailedBorrowHistoryTO.getDueDate()
                                .until(LocalDate.now(), ChronoUnit.DAYS));
                        return detailedBorrowHistoryTO;
                    })
                    .collect(Collectors.toList());
    }

    public ItemsListingTO<DetailedBorrowHistoryTO> getOverdueBooks(Integer start, Integer end,
                                                                   String sort, String order) {
        List<BorrowHistoryItem> overdueBooks = queryOverdueBooks(start, end, sort, order);
        Long overdueBooksCount = countOverdueBooks();

        List<DetailedBorrowHistoryTO> overdueBooksTOS = createListOfDetailedBorrowHistoryTOsWithDatesSet(overdueBooks);

        return new ItemsListingTO<>(overdueBooksTOS, overdueBooksCount);
    }

    private List<BorrowHistoryItem> queryOverdueBooks(Integer start, Integer end,
                                                                 String sort, String order) {
        TypedQuery<BorrowHistoryItem> query = entityManager.createQuery(
                "SELECT b from BorrowHistoryItem b " +
                        "where b.endDate IS NULL " + "AND (CURRENT_DATE - to_date(b.startDate) > :daysNo)"
                        + generateSortQueryClause(sort, order), BorrowHistoryItem.class);
        query.setParameter("daysNo", ALLOWED_BORROW_DAYS_NUMBER);
        query.setMaxResults(end - start).setFirstResult(start);

        return query.getResultList();
    }

    //TODO make the methods private
    public Long countBorrowedBooks() {
        TypedQuery<Long> query = entityManager.createNamedQuery(BorrowHistoryItem.COUNT_ALL_BORROWED_BOOKS,
                Long.class);

        return query.getSingleResult();
    }

    private Long countOverdueBooks() {
        TypedQuery<Long> query = entityManager.createNamedQuery(BorrowHistoryItem.COUNT_ALL_OVERDUE_BOOKS,
                Long.class);
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