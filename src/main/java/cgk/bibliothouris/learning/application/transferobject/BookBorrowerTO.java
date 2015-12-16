package cgk.bibliothouris.learning.application.transferobject;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BookBorrowerTO {

    private String uuid;

    private String firstName;

    private String lastName;

    private Boolean isBorrowed;

    public BookBorrowerTO() {
        this.isBorrowed = false;
    }

    public BookBorrowerTO(String uuid, String firstName, String lastName) {
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isBorrowed = true;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public Boolean getIsBorrowed() {
        return isBorrowed;
    }

    public void setIsBorrowed(Boolean isBorrowed) {
        this.isBorrowed = isBorrowed;
    }
}
