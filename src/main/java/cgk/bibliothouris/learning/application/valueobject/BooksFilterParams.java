package cgk.bibliothouris.learning.application.valueobject;

public class BooksFilterParams {

    private String title;
    private String isbn;
    private String firstName;
    private String lastName;

    public BooksFilterParams() {}

    public BooksFilterParams(String title, String isbn, String firstName, String lastName) {
        this.title = title;
        this.isbn = isbn;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public BooksFilterParams(String title, String isbn) {
        this.title = title;
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BooksFilterParams that = (BooksFilterParams) o;

        if (isbn != null ? !isbn.equals(that.isbn) : that.isbn != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
        return result;
    }
}
