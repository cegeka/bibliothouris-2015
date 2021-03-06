package cgk.bibliothouris.learning.service.entity;

import cgk.bibliothouris.learning.service.converter.LocalDateAdapter;
import cgk.bibliothouris.learning.service.converter.LocalDateAttributeConverter;
import org.eclipse.persistence.oxm.annotations.XmlInverseReference;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@XmlRootElement
@Table(name = "LIBRARY_MEMBER")
@NamedQueries({
        @NamedQuery(name = Member.COUNT_MEMBERS, query = "SELECT COUNT(m.UUID) FROM Member m"),
        @NamedQuery(name = Member.DELETE_ALL_MEMBERS, query = "DELETE FROM Member m")
})
public class Member {

    public static final String COUNT_MEMBERS = "COUNT_MEMBERS";
    public static final String DELETE_ALL_MEMBERS = "DELETE_ALL_MEMBERS";

    @Id
    @Column(name = "U_ID")
    private String UUID;

    @Column(name = "BIRTH_DATE")
    @NotNull(message = "Birth date is missing")
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate birthDate;

    @Column(name = "NATIONAL_NUMBER")
    @NotBlank(message = "National number is missing")
    @NotNull(message = "National number is missing")
    @Size(max = 32, message = "National Number should be at most 32 characters long")
    private String nationalNumber;

    @NotBlank(message = "First name is missing")
    @NotNull(message = "First name is missing")
    @Size(max = 35, message = "First name should be at most 35 characters long")
    @Column(name = "FIRST_NAME")
    private String firstName;

    @NotBlank(message = "Last name is missing")
    @NotNull(message = "Last name is missing")
    @Size(max = 35, message = "Last name should be at most 35 characters long")
    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "POSTAL_CODE")
    private Integer postalCode;

    @Column(name = "CITY")
    private String city;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE_NUMBER")
    private Integer phoneNumber;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @XmlInverseReference(mappedBy="member")
    private Set<BorrowHistoryItem> history = new HashSet<>();

    @Column(name = "MEMBER_SINCE")
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate memberSince;

    public LocalDate getMemberSince() {
        return memberSince;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public void setMemberSince(LocalDate memberSince) {
        this.memberSince = memberSince;
    }

    public Set<BorrowHistoryItem> getHistory() {
        return history;
    }

    public void setHistory(Set<BorrowHistoryItem> history) {
        this.history = history;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getNationalNumber() {
        return nationalNumber;
    }

    public void setNationalNumber(String nationalNumber) {
        this.nationalNumber = nationalNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Integer getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Member member = (Member) o;

        if (UUID != null ? !UUID.equals(member.UUID) : member.UUID != null) return false;
        if (address != null ? !address.equals(member.address) : member.address != null) return false;
        if (birthDate != null ? !birthDate.equals(member.birthDate) : member.birthDate != null) return false;
        if (city != null ? !city.equals(member.city) : member.city != null) return false;
        if (email != null ? !email.equals(member.email) : member.email != null) return false;
        if (firstName != null ? !firstName.equals(member.firstName) : member.firstName != null) return false;
        if (lastName != null ? !lastName.equals(member.lastName) : member.lastName != null) return false;
        if (nationalNumber != null ? !nationalNumber.equals(member.nationalNumber) : member.nationalNumber != null)
            return false;
        if (phoneNumber != null ? !phoneNumber.equals(member.phoneNumber) : member.phoneNumber != null) return false;
        if (postalCode != null ? !postalCode.equals(member.postalCode) : member.postalCode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = UUID != null ? UUID.hashCode() : 0;
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + (nationalNumber != null ? nationalNumber.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        return result;
    }

    public static class MemberBuilder {
        private Member member;

        private MemberBuilder() {
            member = new Member();
        }

        public MemberBuilder withUUID(String UUID) {
            member.UUID = UUID;
            return this;
        }

        public MemberBuilder withBirthDate(LocalDate birthDate) {
            member.birthDate = birthDate;
            return this;
        }

        public MemberBuilder withNationalNumber(String nationalNumber) {
            member.nationalNumber = nationalNumber;
            return this;
        }

        public MemberBuilder withFirstName(String firstName) {
            member.firstName = firstName;
            return this;
        }

        public MemberBuilder withLastName(String lastName) {
            member.lastName = lastName;
            return this;
        }

        public MemberBuilder withAddress(String address) {
            member.address = address;
            return this;
        }

        public MemberBuilder withPostalCode(Integer postalCode) {
            member.postalCode = postalCode;
            return this;
        }

        public MemberBuilder withCity(String city) {
            member.city = city;
            return this;
        }

        public MemberBuilder withEmail(String email) {
            member.email = email;
            return this;
        }

        public MemberBuilder withPhoneNumber(Integer phoneNumber) {
            member.phoneNumber = phoneNumber;
            return this;
        }

        public static MemberBuilder member() {
            return new MemberBuilder();
        }

        public Member build() {
            return member;
        }
    }
}
