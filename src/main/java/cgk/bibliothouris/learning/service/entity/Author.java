package cgk.bibliothouris.learning.service.entity;

import javax.persistence.*;

@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_sequence")
    @SequenceGenerator(name = "author_sequence", sequenceName = "AUTHOR_SEQUENCE", allocationSize = 1)
    @Column(name = "AUTHOR_ID")
    private Integer id;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "FIRST_NAME")
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
}
