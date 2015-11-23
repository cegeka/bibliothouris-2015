package cgk.bibliothouris.learning.service.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_sequence")
    @SequenceGenerator(name = "book_sequence", sequenceName = "BOOK_SEQUENCE", allocationSize = 1)
    @Column(name = "BOOK_ID")
    private Integer id;

    @Column(name = "TITLE")
    private String title;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "BOOK_AUTHOR",
            joinColumns = @JoinColumn(name = "BOOK_ID"),
            inverseJoinColumns = @JoinColumn(name = "AUTHOR_ID")
    )
    private List<Author> authors;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> listOfAuthors) {
        this.authors = listOfAuthors;
    }
}
