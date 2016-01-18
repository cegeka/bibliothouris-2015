package integration;

import cgk.bibliothouris.learning.application.resource.AuthorResource;
import cgk.bibliothouris.learning.application.resource.BookResource;
import cgk.bibliothouris.learning.application.transferobject.StringTO;
import cgk.bibliothouris.learning.config.JpaConfig;
import cgk.bibliothouris.learning.service.entity.Author;
import cgk.bibliothouris.learning.service.entity.Book;
import fixture.BookTestFixture;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.SpringLifecycleListener;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.filter.RequestContextFilter;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ITAuthorResource extends JerseyTest {
    private TestIntegrationClient client;
    private Book bookWithOneAuthorAndOneCategory;
    private static String PATH = "/authors";


    @Override
    protected Application configure() {
        ResourceConfig resourceConfig = new ResourceConfig();
        forceSet(TestProperties.CONTAINER_PORT, "0");
        resourceConfig.register(SpringLifecycleListener.class).register(RequestContextFilter.class);
        resourceConfig.registerClasses(BookResource.class);
        resourceConfig.registerClasses(AuthorResource.class);
        resourceConfig.property("contextConfig", new AnnotationConfigApplicationContext(JpaConfig.class));
        return resourceConfig;
    }

    @Before
    public void setUpTests() {
        client = new TestIntegrationClient(target());
        bookWithOneAuthorAndOneCategory =  BookTestFixture.createBookWithOneAuthorAndOneCategory();
    }

    @Test
    public void givenAListOfBooks_GET_returnsTheListOfAuthors() {
        client.post(PATH, bookWithOneAuthorAndOneCategory).readEntity(Book.class);
        List<Author> bookListing = client.get(PATH).readEntity(new GenericType<ArrayList<Author>>() {
        });
        Author author = Author.AuthorBuilder.author().withFirstName("Robert C.").withLastName("Martin").build();

        assertThat(bookListing).contains(author);
    }

    @Test
    public void givenAListOfBooks_GET_returnsTheCorrectListOfBookAuthorsFirstNames() {
        client.post(PATH, bookWithOneAuthorAndOneCategory).readEntity(Book.class);
        StringTO expectedBookAuthorsFirstName = new StringTO("Robert C.");

        List<StringTO> foundBookAuthorsFirstName = client.get(PATH + "/firstnames").readEntity(new GenericType<List<StringTO>>() {});

        assertThat(foundBookAuthorsFirstName).contains(expectedBookAuthorsFirstName);
    }

    @Test
    public void givenAListOfBooks_GET_returnsTheCorrectListOfBookAuthorsLastNames() {
        client.post(PATH, bookWithOneAuthorAndOneCategory).readEntity(Book.class);
        StringTO expectedBookAuthorsLastName = new StringTO("Martin");

        List<StringTO> foundBookAuthorsLastName = client.get(PATH + "/lastnames").readEntity(new GenericType<List<StringTO>>() {});

        assertThat(foundBookAuthorsLastName).contains(expectedBookAuthorsLastName);
    }


}