package cgk.bibliothouris.learning.repository;

import cgk.bibliothouris.learning.service.entity.Book;
import cgk.bibliothouris.learning.service.entity.BorrowHistoryItem;
import cgk.bibliothouris.learning.service.entity.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class BorrowHistoryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public BorrowHistoryItem addBorrowedBook(Book book, Member member, BorrowHistoryItem historyItem){
        historyItem.setBook(book);
        historyItem.setMember(member);
        member.getHistory().add(historyItem);

        entityManager.persist(historyItem);

        return historyItem;
    }
}
