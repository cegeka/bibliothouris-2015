package integration;

import cgk.bibliothouris.learning.application.resource.BookResource;
import cgk.bibliothouris.learning.application.resource.BorrowHistoryResource;
import cgk.bibliothouris.learning.application.resource.MemberResource;
import cgk.bibliothouris.learning.application.transferobject.BookFilterValueTO;
import cgk.bibliothouris.learning.application.transferobject.BookListingTO;
import cgk.bibliothouris.learning.application.transferobject.BookTO;
import cgk.bibliothouris.learning.application.transferobject.BorrowHistoryItemTO;
import cgk.bibliothouris.learning.config.JpaConfig;
import cgk.bibliothouris.learning.service.entity.Book;
import cgk.bibliothouris.learning.service.entity.BorrowHistoryItem;
import cgk.bibliothouris.learning.service.entity.Member;
import fixture.BookTestFixture;
import fixture.BorrowedHistoryFixture;
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

import javax.net.ssl.SSLEngineResult;
import javax.swing.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ITBorrowHistoryResource extends JerseyTest{

    private TestIntegrationClient client;
    private Book bookWithOneAuthorAndOneCategory;
    private Member member;
    private BorrowHistoryItem borrowHistoryItem;
    private static String PATH = "/borrowedBooks";


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

        bookWithOneAuthorAndOneCategory =  BookTestFixture.createBookWithOneAuthorAndOneCategory();
        member = MemberTestFixture.createMember();
        borrowHistoryItem = BorrowedHistoryFixture.createHistoryItem();
    }

    @After
    public void tearDown() {
        // TODO: Leave the DB clean after the tests execution
    }

    @Test
    public void givenABorrowHistoryItemTO_POST_createsANewBorrowHistoryItem() {
        Book newBook = client.post("/books", bookWithOneAuthorAndOneCategory).readEntity(Book.class);
        Member newMember = client.post("/member", member).readEntity(Member.class);
        BorrowHistoryItemTO borrowHistoryItemTO = BorrowHistoryItemTO.BorrowHistoryItemTOBuilder.borrowHistoryItemTO()
                                                                    .withBookId(newBook.getId())
                                                                    .withMemberUuid(newMember.getUUID())
                                                                    .withStartDate(LocalDate.of(2015, Month.DECEMBER, 3)).build();

        Response response = client.post(PATH, borrowHistoryItemTO);

        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    }

}
