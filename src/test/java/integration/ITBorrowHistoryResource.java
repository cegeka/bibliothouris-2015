package integration;

import cgk.bibliothouris.learning.application.resource.BookResource;
import cgk.bibliothouris.learning.application.resource.BorrowHistoryResource;
import cgk.bibliothouris.learning.application.resource.MemberResource;
import cgk.bibliothouris.learning.application.transferobject.*;
import cgk.bibliothouris.learning.config.JpaConfig;
import cgk.bibliothouris.learning.service.entity.Book;
import cgk.bibliothouris.learning.service.entity.Member;
import fixture.BookTestFixture;
import fixture.MemberTestFixture;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.SpringLifecycleListener;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.filter.RequestContextFilter;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ITBorrowHistoryResource extends JerseyTest{

    private TestIntegrationClient client;
    private BorrowHistoryItemTO borrowHistoryItemTO;
    private static String PATH = "/borrow";


    @Override
    protected Application configure() {
        ResourceConfig resourceConfig = new ResourceConfig();
        forceSet(TestProperties.CONTAINER_PORT, "0");
        resourceConfig.register(SpringLifecycleListener.class).register(RequestContextFilter.class);
        resourceConfig.registerClasses(BookResource.class);
        resourceConfig.registerClasses(MemberResource.class);
        resourceConfig.registerClasses(BorrowHistoryResource.class);
        resourceConfig.property("contextConfig", new AnnotationConfigApplicationContext(JpaConfig.class));
        return resourceConfig;
    }

    @Before
    public void setUpTests() {
        client = new TestIntegrationClient(target());

        Book newBook = client.post("/books", BookTestFixture.createBookWithOneAuthorAndOneCategory()).readEntity(Book.class);
        Member newMember = client.post("/member", MemberTestFixture.createMember()).readEntity(Member.class);
        borrowHistoryItemTO = BorrowHistoryItemTO.BorrowHistoryItemTOBuilder.borrowHistoryItemTO()
                                                                            .withBookId(newBook.getId())
                                                                            .withMemberUuid(newMember.getUUID())
                                                                            .withStartDate(LocalDate.of(2015, Month.DECEMBER, 3)).build();
        borrowHistoryItemTO = BorrowHistoryItemTO.BorrowHistoryItemTOBuilder.borrowHistoryItemTO()
                .withBookId(newBook.getId())
                .withMemberUuid(newMember.getUUID())
                .withStartDate(LocalDate.of(2015, Month.DECEMBER, 3)).build();
    }

    @After
    public void tearDown() {
        // TODO: Leave the DB clean after the tests execution
    }

    @Test
    public void givenABorrowHistoryItemTO_POST_createsANewBorrowHistoryItem() {
        Response response = client.post(PATH, borrowHistoryItemTO);

        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    public void givenAMemberId_GET_returnsTheListOfBorrowedHistoryItems() {
        client.post(PATH, borrowHistoryItemTO);

        BookListingTO<MemberBorrowHistoryTO> memberBorrowHistoryTOs = client.get(PATH + "/" + borrowHistoryItemTO.getMemberUuid()).readEntity(new GenericType<BookListingTO<MemberBorrowHistoryTO>>() {});

        assertThat(memberBorrowHistoryTOs.getBooks().size()).isEqualTo(1);
    }

    @Test
    public void givenAMemberId_COUNT_returnsTheNumberOfBorrowedHistoryItems() {
        client.post(PATH, borrowHistoryItemTO);

        Long count = client.getText(PATH + "/" + borrowHistoryItemTO.getMemberUuid() + "/size").readEntity(new GenericType<Long>() {});

        assertThat(count).isEqualTo(1);
    }

   /* @Test
    public void givenAListOfOverdueBooks_GET_returnsTheListOfOverdueBooks() {
        client.post(PATH, borrowHistoryItemTO);

        BookListingTO<DetailedBorrowHistoryTO> overdueBooks = client.get(PATH + "/overdue").readEntity(new GenericType<BookListingTO<DetailedBorrowHistoryTO>>(){}) ;

        assertThat(overdueBooks.getBooks().size()).isEqualTo(1);
    }*/
}