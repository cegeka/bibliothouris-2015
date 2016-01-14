package cgk.bibliothouris.learning.application.valueobject;

public class SortParams {

    private String sortBy;
    private String order;

    public SortParams() {
        this.sortBy = "";
        this.order = "";
    }

    public SortParams(String sortBy, String order) {
        this.sortBy = sortBy;
        this.order = order;
    }

    public String getSortBy() {
        return sortBy;
    }

    public String getOrder() {
        return order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SortParams that = (SortParams) o;

        if (order != null ? !order.equals(that.order) : that.order != null) return false;
        if (sortBy != null ? !sortBy.equals(that.sortBy) : that.sortBy != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sortBy != null ? sortBy.hashCode() : 0;
        result = 31 * result + (order != null ? order.hashCode() : 0);
        return result;
    }
}
