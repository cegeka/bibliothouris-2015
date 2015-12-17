package cgk.bibliothouris.learning.application.transferobject;

import cgk.bibliothouris.learning.service.entity.Book;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BookWithStatusTO extends BookTO {

    private Boolean isAvailable;

    public BookWithStatusTO() {}

    public BookWithStatusTO(Book book) {
        super(book);
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        BookWithStatusTO book = (BookWithStatusTO) o;

        return !(getId() != null ? !getId().equals(book.getId()) : book.getId() != null);
    }

    @Override
    public int hashCode() {
        return isAvailable != null ? isAvailable.hashCode() : 0;
    }
}
