package cgk.bibliothouris.learning.repository;

import cgk.bibliothouris.learning.application.transferobject.MemberListingTO;
import cgk.bibliothouris.learning.application.transferobject.MemberTO;
import cgk.bibliothouris.learning.service.entity.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager entityManager;


    public Member createMember(Member member) {
        member.setUUID(UUID.randomUUID().toString());
        entityManager.persist(member);

        return member;
    }

    public Member getMember(String uuid) {
        return entityManager.find(Member.class, uuid);
    }

    public MemberListingTO findAllMembers(Integer start, Integer end){
        List<Member> members = findMembers(start, end);
        Long membersCount = countMembers();

        List<MemberTO> memberTOS = members.stream().map(MemberTO::new).collect(Collectors.toList());

        return new MemberListingTO(memberTOS, membersCount);
    }

    private List<Member> findMembers(Integer start, Integer end) {
        String selectStatement = "SELECT m FROM Member m";

        String sortClause = generateSortQueryClause();

        TypedQuery<Member> selectAllQuery = entityManager.createQuery(selectStatement + sortClause, Member.class)
                .setMaxResults(end - start)
                .setFirstResult(start);
        return selectAllQuery.getResultList();
    }

    //TODO extract in a query in Member
    private String generateSortQueryClause() {
        String sortClause = " ORDER BY m.memberSince";

        return sortClause;
    }

    public Long countMembers(){
        TypedQuery<Long> countQuery = entityManager.createNamedQuery(Member.COUNT_MEMBERS, Long.class);
        return countQuery.getSingleResult();
    }

    public void deleteAllMembers(){
        Query deleteAllMembers = entityManager.createNamedQuery(Member.DELETE_ALL_MEMBERS);
        deleteAllMembers.executeUpdate();
    }
}
