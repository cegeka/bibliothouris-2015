package fixture;

import cgk.bibliothouris.learning.service.entity.Author;

public class AuthorTestFixture {

    public static Author createAuthorWithOneFirstAndOneLastName() {
        return Author.AuthorBuilder.author()
                .withFirstName("J. K.")
                .withLastName("Rowling")
                .build();
    }
}
