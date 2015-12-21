package cgk.bibliothouris.learning.application.transferobject;

import java.util.ArrayList;
import java.util.List;

public class MemberListingTO<T> {
    private List<T> members = new ArrayList<>();

    private Long membersCount;

    public MemberListingTO() {}

    public MemberListingTO(List<T> members, Long membersCount) {
        this.members = members;
        this.membersCount = membersCount;
    }

    public List<T> getMembers() {
        return members;
    }

    public void setMembers(List<T> members) {
        this.members = members;
    }

    public Long getMembersCount() {
        return membersCount;
    }

    public void setMembersCount(Long membersCount) {
        this.membersCount = membersCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemberListingTO that = (MemberListingTO) o;

        if (members != null ? !members.equals(that.members) : that.members != null) return false;
        if (membersCount != null ? !membersCount.equals(that.membersCount) : that.membersCount != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = members != null ? members.hashCode() : 0;
        result = 31 * result + (membersCount != null ? membersCount.hashCode() : 0);
        return result;
    }
}
