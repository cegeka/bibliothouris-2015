package cgk.bibliothouris.learning.repository;

import cgk.bibliothouris.learning.service.entity.Book;
import cgk.bibliothouris.learning.service.entity.BorrowedHistory;
import cgk.bibliothouris.learning.service.entity.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager entityManager;


    public Member createMember(Member member) {
        member.setUUID(UUID.randomUUID().toString());
        entityManager.persist(member);

        return member;
    }

    public BorrowedHistory addBorrowedBook(Book book, Member member, BorrowedHistory history){
        history.setBook(book);
        history.setMember(member);
        member.getHistory().add(history);
        entityManager.persist(history);
        return history;
    }

    public Member getMember(String uuid) {
        return entityManager.find(Member.class, uuid);
    }
}
