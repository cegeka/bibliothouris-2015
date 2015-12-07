package cgk.bibliothouris.learning.application.transferobject;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BookTitleTO {

    private String title;

    public BookTitleTO(){}

    public BookTitleTO(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookTitleTO that = (BookTitleTO) o;

        return !(title != null ? !title.equals(that.title) : that.title != null);
    }

    @Override
    public int hashCode() {
        return title != null ? title.hashCode() : 0;
    }
}
