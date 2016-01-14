package cgk.bibliothouris.learning.repository;

import cgk.bibliothouris.learning.application.transferobject.ItemsListingTO;
import cgk.bibliothouris.learning.application.transferobject.MemberNameTO;
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

    public ItemsListingTO<MemberTO> findAllMembers(Integer start, Integer end, String name, String sort, String order){
        List<Member> members = findMembers(start, end, name, sort, order);

        List<MemberTO> memberTOS = members.stream().map(MemberTO::new).collect(Collectors.toList());

        Integer membersCount = memberTOS.size();
        memberTOS = memberTOS.subList(start, Integer.min(end, membersCount));

        return new ItemsListingTO(memberTOS, new Long(membersCount));
    }

    private List<Member> findMembers(Integer start, Integer end, String name, String sort, String order) {
        String selectStatement = "SELECT m FROM Member m";

        String filterClause = generateFilterQueryClause(name);
        String sortClause = generateSortQueryClause(sort, order);

        selectStatement += " WHERE" + filterClause + sortClause;

        TypedQuery<Member> selectAllQuery = entityManager.createQuery(selectStatement, Member.class);
        return selectAllQuery.getResultList();
    }

    private String generateFilterQueryClause(String name) {
        String conditionalClause = " 1 = 1";

        if (name != null)
            conditionalClause += " AND LOWER(CONCAT(m.firstName, ' ', m.lastName)) LIKE '%" + name.toLowerCase() + "%'";

        return conditionalClause;
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
            case "birthDate":
                return sortClause + "m.birthDate" + " " + sortOrder;
            default:
                return sortClause + "m.memberSince DESC NULLS LAST";
        }
    }

    public Long countMembers(){
        TypedQuery<Long> countQuery = entityManager.createNamedQuery(Member.COUNT_MEMBERS, Long.class);
        return countQuery.getSingleResult();
    }

    public List<MemberNameTO> findAllMembersNames() {
        TypedQuery<MemberNameTO> query = entityManager.createNamedQuery(Member.GET_ALL_MEMBERS_NAMES, MemberNameTO.class);

        return query.getResultList();
    }

    public void deleteAllMembers(){
        Query deleteAllMembers = entityManager.createNamedQuery(Member.DELETE_ALL_MEMBERS);
        deleteAllMembers.executeUpdate();
    }
}
