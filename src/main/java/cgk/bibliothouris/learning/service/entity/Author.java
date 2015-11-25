package cgk.bibliothouris.learning.service.entity;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = Author.FIND_AUHTORS_BY_FIRSTNAME_AND_LASTNAME,
                    query = "SELECT a FROM Author a WHERE a.firstName = :firstName AND a.lastName = :lastName")
})
public class Author {

    public static final String FIND_AUHTORS_BY_FIRSTNAME_AND_LASTNAME = "FIND_AUHTORS_BY_FIRSTNAME_AND_LASTNAME";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_sequence")
    @SequenceGenerator(name = "author_sequence", sequenceName = "AUTHOR_SEQUENCE", allocationSize = 1)
    @Column(name = "AUTHOR_ID")
    private Integer id;

    @Column(name = "LAST_NAME")
    @NotBlank(message = "Author last name is empty or is missing")
    @Size(max = 255, message = "Author last name is too long")
    private String lastName;

    @Column(name = "FIRST_NAME")
    @Size(max = 255, message = "Author first name is too long")
    private String firstName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public static class AuthorBuilder {
        private Author author;

        private AuthorBuilder() {
            author = new Author();
        }

        public AuthorBuilder withLastName(String lastName) {
            author.lastName = lastName;
            return this;
        }

        public AuthorBuilder withFirstName(String firstName) {
            author.firstName = firstName;
            return this;
        }

        public static AuthorBuilder author() {
            return new AuthorBuilder();
        }

        public Author build() {
            return author;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        if (firstName != null ? !firstName.equals(author.firstName) : author.firstName != null) return false;
        if (lastName != null ? !lastName.equals(author.lastName) : author.lastName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = lastName != null ? lastName.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        return result;
    }
}
