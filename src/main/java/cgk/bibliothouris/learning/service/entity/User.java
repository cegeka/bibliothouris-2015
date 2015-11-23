package cgk.bibliothouris.learning.service.entity;

import javax.persistence.*;

@Entity
@Table(name = "BOOK_USER")
public class User {

    @Id
    @SequenceGenerator(name = "book_user_sequence", sequenceName = "BOOK_USER_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_user_sequence")
    @Column(name = "USER_ID")
    private Integer id;

    @Column(name = "USERNAME")
    private String name;

    @Column(name = "PASSWORD")
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
