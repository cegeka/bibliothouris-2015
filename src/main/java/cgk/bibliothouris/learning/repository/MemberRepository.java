package cgk.bibliothouris.learning.repository;

import cgk.bibliothouris.learning.application.transferobject.ItemsListingTO;
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

    public ItemsListingTO findAllMembers(Integer start, Integer end, String sort, String order){
        List<Member> members = findMembers(start, end, sort, order);
        Long membersCount = countMembers();

        List<MemberTO> memberTOS = members.stream().map(MemberTO::new).collect(Collectors.toList());

        return new ItemsListingTO(memberTOS, membersCount);
    }

    private List<Member> findMembers(Integer start, Integer end, String sort, String order) {
        String selectStatement = "SELECT m FROM Member m" + generateSortQueryClause(sort, order);

        TypedQuery<Member> selectAllQuery = entityManager.createQuery(selectStatement, Member.class)
                .setMaxResults(end - start)
                .setFirstResult(start);
        return selectAllQuery.getResultList();
    }

    private String generateSortQueryClause(String sortCriteria, String sortOrder) {
        String sortClause = " ORDER BY ";

        if (sortCriteria == null)
            return sortClause + "m.memberSince DESC NULLS LAST";

        switch (sortCriteria) {
            case "firstName":
                return sortClause + "lower(m.firstName)" + " " + sortOrder;
            case "lastName":
                return sortClause + "lower(m.lastName)" + " " + sortOrder;
            case "address":
                return sortClause + "lower(m.address)" + " " + sortOrder;
            case "city":
                return sortClause + "lower(m.city)" + " " + sortOrder;
            default:
                return sortClause + "m.memberSince DESC NULLS LAST";
        }
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
