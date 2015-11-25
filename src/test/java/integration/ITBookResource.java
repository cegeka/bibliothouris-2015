package integration;

import cgk.bibliothouris.learning.application.resource.BookResource;
import cgk.bibliothouris.learning.config.JpaConfig;
import cgk.bibliothouris.learning.service.entity.Book;
import fixture.BookTestFixture;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.SpringLifecycleListener;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.filter.RequestContextFilter;

import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import java.util.List;

public class ITBookResource extends JerseyTest {

    private BookServiceClient client;
    private Book bookWithOneAuthor;
    private static String PATH = "/books";


    @Override
    protected Application configure() {
        ResourceConfig resourceConfig = new ResourceConfig();
        forceSet(TestProperties.CONTAINER_PORT, "0");
        resourceConfig.register(SpringLifecycleListener.class).register(RequestContextFilter.class);
        resourceConfig.registerClasses(BookResource.class);
        resourceConfig.property("contextConfig", new AnnotationConfigApplicationContext(JpaConfig.class));
        return resourceConfig;
    }

    @Before
    public void setUpTests() {
        client = new BookServiceClient(target());

        bookWithOneAuthor = BookTestFixture.createBookWithOneAuthor();
    }

    @After
    public void tearDown() {
        // TODO: Leave the DB clean after the tests execution

    }

    @Test
    public void givenABook_POST_createsANewBook() {
        Book newBook = client.post(PATH, bookWithOneAuthor).readEntity(Book.class);

        assertThat(newBook.getId()).isNotNull();
    }

    @Test
    public void givenAListOfBooks_GET_returnsTheListOfBooks() {
        Book newBook = client.post(PATH, bookWithOneAuthor).readEntity(Book.class);

        List<Book> returnedList = client.getBooks(PATH).readEntity(new GenericType<List<Book>>() {});

        assertThat(returnedList).contains(newBook);
    }
}