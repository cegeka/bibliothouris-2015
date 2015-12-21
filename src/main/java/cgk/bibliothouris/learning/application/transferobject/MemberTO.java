package cgk.bibliothouris.learning.application.transferobject;

import cgk.bibliothouris.learning.service.entity.Member;

import java.time.LocalDate;

public class MemberTO {

    private String UUID;

    private LocalDate birthDate;

    private String firstName;

    private String lastName;

    private String address;

    private String city;

    public MemberTO() {}

    public MemberTO(Member member) {
        this.UUID = member.getUUID();
        this.birthDate = member.getBirthDate();
        this.firstName = member.getFirstName();
        this.lastName = member.getLastName();
        this.address = member.getAddress();
        this.city = member.getCity();
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemberTO memberTO = (MemberTO) o;

        if (UUID != null ? !UUID.equals(memberTO.UUID) : memberTO.UUID != null) return false;

        return true;
    }
}
