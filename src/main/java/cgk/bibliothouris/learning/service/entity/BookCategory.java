package cgk.bibliothouris.learning.service.entity;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "CATEGORY")
@NamedQueries({
        @NamedQuery(name = BookCategory.FIND_ALL_CATEGORIES, query = "SELECT bc FROM BookCategory bc ORDER BY bc.category"),
        @NamedQuery(name = BookCategory.FIND_CATEGORY_BY_TYPE, query = "SELECT bc FROM BookCategory bc WHERE bc.category = :category")
})
@XmlRootElement
public class BookCategory {

    public static final String FIND_ALL_CATEGORIES = "FIND_ALL_CATEGORIES";
    public static final String FIND_CATEGORY_BY_TYPE = "FIND_CATEGORY_BY_TYPE";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_sequence")
    @SequenceGenerator(name = "category_sequence", sequenceName = "CATEGORY_SEQUENCE", allocationSize = 1)
    @Column(name = "CATEGORY_ID")
    private Integer id;

    @Column(name = "TYPE")
    @NotBlank(message = "Book category type is empty or is missing")
    @Size(max = 255, message = "Book category type is too long")
    private String category;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public static class CategoryBuilder {
        private BookCategory bookCategory;

        private CategoryBuilder() {
            bookCategory = new BookCategory();
        }

        public CategoryBuilder withId(Integer id) {
            bookCategory.id = id;
            return this;
        }

        public CategoryBuilder withCategory(String category) {
            bookCategory.category = category;
            return this;
        }

        public static CategoryBuilder category() {
            return new CategoryBuilder();
        }

        public BookCategory build() {
            return bookCategory;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookCategory that = (BookCategory) o;

        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }


}
