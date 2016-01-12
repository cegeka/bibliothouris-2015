package cgk.bibliothouris.learning.service.converter;

import cgk.bibliothouris.learning.service.entity.Author;
import cgk.bibliothouris.learning.service.entity.Book;
import cgk.bibliothouris.learning.service.entity.BookCategory;
import com.google.api.services.books.model.Volume;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

//@Component
public class ImportedBookConverter {

    private Volume.VolumeInfo info;

    /* @Autowired
    public void setVolumeInfo(Volume.VolumeInfo info) {
        this.info = info;
    }*/
    
    public ImportedBookConverter(Volume.VolumeInfo info) {
        this.info = info;
    }

    public Book convertVolumeInfo() throws GeneralSecurityException, IOException {
        return Book.BookBuilder.book()
                .withTitle(info.getTitle())
                .withAuthors(getAuthorsFromImportedContent())
                .withPublisher(info.getPublisher())
                .withCover(getCoverFromImportedContent())
                .withDescription(info.getDescription())
                .withIsbn(getISBNFromImportedContent())
                .withCategories(getCategoriesFromImportedContent()).withPages(info.getPageCount())
                .withDate(getPublishedDateFromImportedContent()).build();
    }

    Set<Author> getAuthorsFromImportedContent() {
        Set<Author> authorSet = new HashSet<>();
        Author author;
        if (info.getAuthors() != null)
            for (String inf : info.getAuthors()) {
                if(inf.contains(" ")) {
                    author = Author.AuthorBuilder.author().withFirstName(inf.substring(0, inf.indexOf(" "))).withLastName(inf.substring(inf.indexOf(" ") + 1)).build();
                    authorSet.add(author);
                }
                else {
                    author = Author.AuthorBuilder.author().withLastName(inf.substring(inf.indexOf(" ") + 1)).build();
                    authorSet.add(author);
                }
            }
        return authorSet;
    }

    String getISBNFromImportedContent() {
        String ISBN = "";
        if (info.getIndustryIdentifiers() != null)
            for (Volume.VolumeInfo.IndustryIdentifiers identifiers : info.getIndustryIdentifiers()) {
                if (identifiers.getType().equals("ISBN_13")) {
                    ISBN = identifiers.getIdentifier();
                }
            }
        return ISBN;
    }

    Set<BookCategory> getCategoriesFromImportedContent() {
        Set<BookCategory> categorySet = new HashSet<>();
        if (info.getCategories() != null)
            for (String inf : info.getCategories()) {
                BookCategory category = BookCategory.CategoryBuilder.category().withCategory(inf).build();
                categorySet.add(category);
            }
        return categorySet;
    }

    String getCoverFromImportedContent() {
        String cover = "";
        if (info.getImageLinks() != null)
            cover = info.getImageLinks().getThumbnail();
        return cover;
    }
    //TODO: show to refactoring group
    LocalDate getPublishedDateFromImportedContent() {
        LocalDate date = null;
        if (info.getPublishedDate() != null) {
            String[] parts = info.getPublishedDate().split("-");
            if (parts.length == 3)
                date = LocalDate.of(Integer.parseInt(parts[0]),
                        Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]));
            if (parts.length == 2)
                date = LocalDate.of(Integer.parseInt(parts[0]),
                        Integer.parseInt(parts[1]),
                        01);
            if (parts.length == 1) {
                if (parts[0].contains("*"))
                    date = LocalDate.of(Integer.parseInt(info.getPublishedDate().substring(0, info.getPublishedDate().indexOf("*"))),
                            Month.JANUARY,
                            01);

                else
                    try {
                        date = LocalDate.of(Integer.parseInt(parts[0]),
                                Month.JANUARY,
                                01);
                    } catch(Exception exc){
                        date = null;
                    }
            }
        }

        return date;
    }
}
