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
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ITBookResource extends JerseyTest {

    private TestIntegrationClient client;
    private Book bookWithOneAuthorAndOneCategory, bookWithOneAuthorAndThreeCategories;
    private static String PATH = "/books";


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
        bookWithOneAuthorAndThreeCategories = BookTestFixture.createBookWithOneAuthorAndThreeCategories();
    }

    @After
    public void tearDown() {
        // TODO: Leave the DB clean after the tests execution

    }

    @Test
    public void givenABook_POST_createsANewBook() {
        Book newBook = client.post(PATH, bookWithOneAuthorAndOneCategory).readEntity(Book.class);

        assertThat(newBook.getId()).isNotNull();
    }

    @Test
    public void givenAListOfBooks_GET_returnsTheListOfBooks() {
        Book newBook = client.post(PATH, bookWithOneAuthorAndOneCategory).readEntity(Book.class);
        BookWithStatusTO expectedBookTO = new BookWithStatusTO(newBook);

        ItemsListingTO<BookWithStatusTO> bookListingTO = client.get(PATH).readEntity(new GenericType<ItemsListingTO<BookWithStatusTO>>() {});

        assertThat(bookListingTO.getItems()).contains(expectedBookTO);
    }

    @Test
    public void givenAListOfBooksAndPaginationParams_GET_returnsTheListOfBooks() {
        client.post(PATH, bookWithOneAuthorAndOneCategory).readEntity(Book.class);
        client.post(PATH, bookWithOneAuthorAndThreeCategories).readEntity(Book.class);
        client.post(PATH, bookWithOneAuthorAndOneCategory).readEntity(Book.class);

        ItemsListingTO<BookWithStatusTO> bookListingTO = client.getBooksWithParams(PATH, null, null, "0", "1").readEntity(new GenericType<ItemsListingTO<BookWithStatusTO>>() {});

        assertThat(bookListingTO.getItems().size()).isEqualTo(1);
    }

    @Test
    public void givenAListOfBooksAndTitleFilteringParam_GET_returnsTheListOfBooks() {
        Book newBook = client.post(PATH, bookWithOneAuthorAndOneCategory).readEntity(Book.class);
        Book newBook2 = client.post(PATH, bookWithOneAuthorAndThreeCategories).readEntity(Book.class);
        BookWithStatusTO expectedBookTO = new BookWithStatusTO(newBook);
        BookWithStatusTO expectedBookTO2 = new BookWithStatusTO(newBook2);

        ItemsListingTO<BookWithStatusTO> bookListingTO = client.getBooksWithParams(PATH, "Clean Code", null, null, null).readEntity(new GenericType<ItemsListingTO<BookWithStatusTO>>() {
        });

        assertThat(bookListingTO.getItems()).contains(expectedBookTO);
        assertThat(bookListingTO.getItems()).doesNotContain(expectedBookTO2);

    }

    @Test
    public void givenAListOfBooksAndISBNFilteringParam_GET_returnsTheListOfBooks() {
        Book newBook = client.post(PATH, bookWithOneAuthorAndOneCategory).readEntity(Book.class);
        Book newBook2 = client.post(PATH, bookWithOneAuthorAndThreeCategories).readEntity(Book.class);
        BookWithStatusTO expectedBookTO = new BookWithStatusTO(newBook);
        BookWithStatusTO expectedBookTO2 = new BookWithStatusTO(newBook2);

        ItemsListingTO<BookWithStatusTO> bookListingTO = client.getBooksWithParams(PATH, null, "978-0-13-235088-5", null, null).readEntity(new GenericType<ItemsListingTO<BookWithStatusTO>>() {});

        assertThat(bookListingTO.getItems()).contains(expectedBookTO2);
        assertThat(bookListingTO.getItems()).doesNotContain(expectedBookTO);

    }

    @Test
    public void givenABook_GETById_returnsTheCorrectBook() {
        Book book = client.post(PATH, bookWithOneAuthorAndOneCategory).readEntity(Book.class);

        Book foundBook = client.get(PATH + "/" + book.getId()).readEntity(Book.class);

        assertThat(foundBook).isEqualTo(book);
    }

    @Test
    public void givenAListOfBooks_GET_returnsTheCorrectListOfBookTitles() {
        Book book = client.post(PATH, bookWithOneAuthorAndOneCategory).readEntity(Book.class);
        StringTO expectedBookTitle = new StringTO(book.getTitle());

        List<StringTO> foundBookTitles = client.get(PATH + "/titles").readEntity(new GenericType<List<StringTO>>() {});

        assertThat(foundBookTitles).contains(expectedBookTitle);
    }

    @Test
    public void givenAListOfBooks_GET_returnsTheCorrectListOfBookIsbnCodes() {
        Book book = client.post(PATH, bookWithOneAuthorAndOneCategory).readEntity(Book.class);
        StringTO expectedBookIsbnCode = new StringTO(book.getIsbn());

        List<StringTO> foundBookIsbnCodes = client.get(PATH + "/isbnCodes").readEntity(new GenericType<List<StringTO>>() {});

        assertThat(foundBookIsbnCodes).contains(expectedBookIsbnCode);
    }

    @Test
    public void givenABookId_GETBookBorrowerDetails_returnsTheCorrectBorrowerDetails() {
        Book newBook = client.post(PATH, BookTestFixture.createBookWithOneAuthorAndOneCategory()).readEntity(Book.class);
        Member newMember = client.post("/member", MemberTestFixture.createMember()).readEntity(Member.class);
        BorrowHistoryItemTO borrowHistoryItemTO = BorrowHistoryItemTO.BorrowHistoryItemTOBuilder.borrowHistoryItemTO()
                                                                        .withBookId(newBook.getId())
                                                                        .withMemberUuid(newMember.getUUID())
                                                                        .withStartDate(LocalDate.of(2015, Month.DECEMBER, 3)).build();
        client.post("/borrow", borrowHistoryItemTO);

        BookBorrowerTO bookBorrowerTO = client.get(PATH + "/" + newBook.getId() + "/borrowedBy").readEntity(BookBorrowerTO.class);

        assertThat(bookBorrowerTO.getIsBorrowed()).isTrue();
    }

    @Test
    public void givenAListOfImportedBooks_GET_returnTheCorrectList() {

        List<Book> foundBooksFromImport = client.getImportedBooks(PATH + "/import/", "refactoring", null).readEntity(new GenericType<List<Book>>() {});

        assertThat(foundBooksFromImport.get(0).getTitle()).isEqualTo("Refactoring");
    }
}