package cgk.bibliothouris.learning.repository;

import cgk.bibliothouris.learning.application.transferobject.DetailedBorrowHistoryTO;
import cgk.bibliothouris.learning.application.transferobject.ItemsListingTO;
import cgk.bibliothouris.learning.application.transferobject.MemberBorrowHistoryTO;
import cgk.bibliothouris.learning.application.valueobject.PaginationParams;
import cgk.bibliothouris.learning.application.valueobject.SortParams;
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

    public ItemsListingTO<MemberBorrowHistoryTO> findBorrowedBooksByMember(String memberUID, PaginationParams pagination) {
        List<BorrowHistoryItem> borrowHistoryItems = queryBorrowedBooksByMember(memberUID, pagination);
        Long countBorrowedBooksByMember = countBorrowedBooksByMember(memberUID);
        List<MemberBorrowHistoryTO> borrowedBooksByMember =
                createListOfMemberBorrowHistoryTOsWithDatesSet(borrowHistoryItems);
        return new ItemsListingTO(borrowedBooksByMember, countBorrowedBooksByMember);
    }

    private List<BorrowHistoryItem> queryBorrowedBooksByMember(String memberUID, PaginationParams pagination) {
        TypedQuery<BorrowHistoryItem> query = entityManager.createNamedQuery(
                BorrowHistoryItem.LIST_ALL_MEMBER_BORROWED_BOOKS, BorrowHistoryItem.class);
        query.setParameter("uuid", memberUID)
                .setMaxResults(Integer.valueOf(pagination.getEnd()) - Integer.valueOf(pagination.getStart()))
                .setFirstResult(Integer.valueOf(pagination.getStart()));
        return query.getResultList();
    }

    private List<MemberBorrowHistoryTO> createListOfMemberBorrowHistoryTOsWithDatesSet(List<BorrowHistoryItem> borrowHistoryItems){
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

    public ItemsListingTO<DetailedBorrowHistoryTO> getBorrowedBooks(PaginationParams pagination, SortParams sortParams) {
        List<BorrowHistoryItem> borrowHistoryItems = queryBooksWhereEndDateIsNull(pagination, sortParams);
        Long countBorrowedBooks = countBorrowedBooks();

        List<DetailedBorrowHistoryTO> borrowedBookTOS =
                createListOfDetailedBorrowHistoryTOsWithDatesSet(borrowHistoryItems);

        return new ItemsListingTO(borrowedBookTOS, countBorrowedBooks);
    }

    private List<BorrowHistoryItem> queryBooksWhereEndDateIsNull(PaginationParams pagination, SortParams sortParams) {
        TypedQuery<BorrowHistoryItem> query = entityManager.createQuery(
                        "SELECT b from BorrowHistoryItem b where (b.endDate IS NULL)" +
                        generateSortQueryClause(sortParams), BorrowHistoryItem.class);
        query.setMaxResults(Integer.valueOf(pagination.getEnd()) - Integer.valueOf(pagination.getStart()))
                .setFirstResult(Integer.valueOf(pagination.getStart()));
        return query.getResultList();
    }

    private List<DetailedBorrowHistoryTO> createListOfDetailedBorrowHistoryTOsWithDatesSet(List<BorrowHistoryItem> borrowHistoryItems) {
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

    public ItemsListingTO<DetailedBorrowHistoryTO> getOverdueBooks(PaginationParams paginationParams, SortParams sortParams) {
        List<BorrowHistoryItem> overdueBooks = queryOverdueBooks(paginationParams, sortParams);
        Long overdueBooksCount = countOverdueBooks();

        List<DetailedBorrowHistoryTO> overdueBooksTOS = createListOfDetailedBorrowHistoryTOsWithDatesSet(overdueBooks);

        return new ItemsListingTO<>(overdueBooksTOS, overdueBooksCount);
    }

    private List<BorrowHistoryItem> queryOverdueBooks(PaginationParams paginationParams, SortParams sortParams) {
        TypedQuery<BorrowHistoryItem> query = entityManager.createQuery(
                "SELECT b from BorrowHistoryItem b " +
                        "where b.endDate IS NULL " + "AND (CURRENT_DATE - to_date(b.startDate) > :daysNo)"
                        + generateSortQueryClause(sortParams), BorrowHistoryItem.class);
        query.setParameter("daysNo", ALLOWED_BORROW_DAYS_NUMBER);
        query.setMaxResults(Integer.valueOf(paginationParams.getEnd()) - Integer.valueOf(paginationParams.getStart()))
                .setFirstResult(Integer.valueOf(paginationParams.getStart()));

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

    private String generateSortQueryClause(SortParams sortParams) {
        String sortClause = "ORDER BY ";

        if (sortParams.getSortBy() == null)
            return sortClause + "lower(b.book.title)";

        switch (sortParams.getSortBy()) {
            case "borrower":
                return sortClause + "lower(b.member.firstName)" + " " + sortParams.getOrder() + ", lower(b.member.lastName)" + " " + sortParams.getOrder();
            case "title":
                return sortClause + "lower(b.book.title)" + " " + sortParams.getOrder();
            case "isbn":
                return sortClause + "b.book.isbn" + " " + sortParams.getOrder();
            case "date":
                return sortClause + "b.startDate" + " " + sortParams.getOrder();
            default:
                return sortClause + "lower(b.book.title)";
        }
    }
}