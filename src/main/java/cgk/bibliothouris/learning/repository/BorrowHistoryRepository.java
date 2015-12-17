package cgk.bibliothouris.learning.repository;

import cgk.bibliothouris.learning.application.transferobject.BookBorrowerTO;
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

    @PersistenceContext
    private EntityManager entityManager;

    public BorrowHistoryItem addBorrowedBook(BorrowHistoryItem historyItem){
        historyItem.getMember().getHistory().add(historyItem);

        entityManager.persist(historyItem);

        return historyItem;
    }

    public List<MemberBorrowHistoryTO> findBorrowedBooksByMember(String memberUID, Integer start, Integer end) {
        TypedQuery<BorrowHistoryItem> query = entityManager.createNamedQuery(BorrowHistoryItem.LIST_ALL_MEMBER_BORROWED_BOOKS, BorrowHistoryItem.class);
        query.setParameter("uuid", memberUID).setMaxResults(end - start).setFirstResult(start);

        List<BorrowHistoryItem> borrowHistoryItems = query.getResultList();

        return borrowHistoryItems.stream().map(MemberBorrowHistoryTO::new).collect(Collectors.toList());
    }

    public Long countBorrowedBooksByMember(String memberUID) {
        TypedQuery<Long> query = entityManager.createNamedQuery(BorrowHistoryItem.COUNT_ALL_MEMBER_BORROWED_BOOKS, Long.class);
        query.setParameter("uuid", memberUID);

        return query.getSingleResult();
    }

public List<DetailedBorrowHistoryTO> getBorrowedBooks(Integer start, Integer end) {
        TypedQuery<BorrowHistoryItem> query = entityManager.createNamedQuery(BorrowHistoryItem.LIST_ALL_BORROWED_BOOKS, BorrowHistoryItem.class);
        query.setMaxResults(end-start).setFirstResult(start);

        List<BorrowHistoryItem> borrowHistoryItems = query.getResultList();
        return borrowHistoryItems.stream().map(DetailedBorrowHistoryTO::new).collect(Collectors.toList());
    }

    public BookBorrowerTO findBookBorrowerDetails(Integer bookId) {
        TypedQuery<BookBorrowerTO> query = entityManager.createNamedQuery(BorrowHistoryItem.GET_CURRENT_BORROWER_DETAILS_FOR_BOOK, BookBorrowerTO.class);
        query.setParameter("bookId", bookId);

        List<BookBorrowerTO> bookBorrowerTOs = query.getResultList();

        if (bookBorrowerTOs.isEmpty())
            return new BookBorrowerTO();
        else
            return bookBorrowerTOs.get(0);
    }

    public Long countBorrowedBooks() {
        TypedQuery<Long> query = entityManager.createNamedQuery(BorrowHistoryItem.COUNT_ALL_BORROWED_BOOKS, Long.class);

        return query.getSingleResult();

    }
}
