package cgk.bibliothouris.learning.service.converter;

import cgk.bibliothouris.learning.service.entity.Author;
import cgk.bibliothouris.learning.service.entity.BookCategory;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;

public class ITImportedBookConverter {
    //@Autowired
    ImportedBookConverter bookConverter;
    Volume.VolumeInfo info = new Volume.VolumeInfo();

    public void createBookConverterObject(String publishedDate){
        info.setTitle("Clean Code");
        List<String> authors = new ArrayList<>();
        authors.add("Robert C. Martin");
        authors.add("Annon.");
        authors.add("Robert C. F. Klein");
        info.setAuthors(authors);
        List<String> categories = new ArrayList<>();
        categories.add("Computers");
        categories.add("Science");
        info.setCategories(categories);
        info.setPublishedDate(publishedDate);
        info.setPublisher("BOD-Books On Demand");
        Volume.VolumeInfo.IndustryIdentifiers identifier = new Volume.VolumeInfo.IndustryIdentifiers();
        identifier.setIdentifier("9780132350884");
        identifier.setType("ISBN_13");
        List<Volume.VolumeInfo.IndustryIdentifiers> listOfIdentifiers = new ArrayList<>();
        listOfIdentifiers.add(identifier);
        info.setIndustryIdentifiers(listOfIdentifiers);
        bookConverter = new ImportedBookConverter(info);
    }

    @Test
    public void givenAVolumeInfo_importContent_returnCorrectISBN() throws GeneralSecurityException, IOException {
        createBookConverterObject("1955*");
        String ISBN = bookConverter.getISBNFromImportedContent();

        assertThat(ISBN).isEqualTo("9780132350884");
    }

    @Test
    public void givenAVolumeInfo_importContent_returnCorrectSetOfAuthors() throws GeneralSecurityException, IOException {
        createBookConverterObject("1955*");
        Set<Author> expectedAuthors = new HashSet<>();
        Author expectedAuthor = Author.AuthorBuilder.author().withFirstName("Robert").withLastName("C. Martin").build();
        Author expectedAuthor2 = Author.AuthorBuilder.author().withFirstName("Robert").withLastName("C. F. Klein").build();
        Author expectedAuthor3 = Author.AuthorBuilder.author().withLastName("Annon.").build();
        expectedAuthors.add(expectedAuthor);
        expectedAuthors.add(expectedAuthor2);
        expectedAuthors.add(expectedAuthor3);

        Set<Author> authors = bookConverter.getAuthorsFromImportedContent();

        assertThat(authors).isEqualTo(expectedAuthors);
    }

    @Test
    public void givenAVolumeInfo_importContent_returnCorrectSetOfCategories() throws GeneralSecurityException, IOException {
        createBookConverterObject("1955*");
        Set<BookCategory> expectedCategories = new HashSet<>();
        BookCategory expectedCategory = BookCategory.CategoryBuilder.category().withCategory("Computers").build();
        BookCategory expectedCategory2 = BookCategory.CategoryBuilder.category().withCategory("Science").build();
        expectedCategories.add(expectedCategory);
        expectedCategories.add(expectedCategory2);

        Set<BookCategory> categories = bookConverter.getCategoriesFromImportedContent();

        assertThat(categories).isEqualTo(expectedCategories);
    }

    @Test
    public void givenAVolumeInfoWithAStarSeparator_importContent_returnCorrectPublishedDate() throws GeneralSecurityException, IOException {
        createBookConverterObject("1955*");
        LocalDate expectedPublishedDate = LocalDate.of(1955, Month.JANUARY, 01);

        LocalDate publishedDate = bookConverter.getPublishedDateFromImportedContent();

        assertThat(publishedDate).isEqualTo(expectedPublishedDate);
    }

    @Test
    public void givenAVolumeInfoWithQuestionMarkSeparator_importContent_returnCorrectPublishedDate() throws GeneralSecurityException, IOException {
        createBookConverterObject("19??");

        LocalDate publishedDate = bookConverter.getPublishedDateFromImportedContent();

        assertNull(publishedDate);
    }

    @Test
    public void givenAVolumeInfoWithYearMonthDayDate_importContent_returnCorrectPublishedDate() throws GeneralSecurityException, IOException {
        createBookConverterObject("1930-12-03");
        LocalDate expectedPublishedDate = LocalDate.of(1930, Month.DECEMBER, 03);

        LocalDate publishedDate = bookConverter.getPublishedDateFromImportedContent();

        assertThat(publishedDate).isEqualTo(expectedPublishedDate);
    }

    @Test
    public void givenAVolumeInfoWithYearMonthDate_importContent_returnCorrectPublishedDate() throws GeneralSecurityException, IOException {
        createBookConverterObject("1930-12");
        LocalDate expectedPublishedDate = LocalDate.of(1930, Month.DECEMBER, 01);

        LocalDate publishedDate = bookConverter.getPublishedDateFromImportedContent();

        assertThat(publishedDate).isEqualTo(expectedPublishedDate);
    }

    @Test
    public void givenAVolumeInfoWithYearDate_importContent_returnCorrectPublishedDate() throws GeneralSecurityException, IOException {
        createBookConverterObject("1930");
        LocalDate expectedPublishedDate = LocalDate.of(1930, Month.JANUARY, 01);

        LocalDate publishedDate = bookConverter.getPublishedDateFromImportedContent();

        assertThat(publishedDate).isEqualTo(expectedPublishedDate);
    }

    @Test
    public void givenAVolumeInfo_importContent_returnCorrectPublisher() throws GeneralSecurityException, IOException {
        createBookConverterObject("1955*");
        String expectedPublisher = "BOD-Books On Demand";

        assertThat(info.getPublisher()).isEqualTo(expectedPublisher);
    }
}