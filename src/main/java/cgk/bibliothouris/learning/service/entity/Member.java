package cgk.bibliothouris.learning.service.entity;

import cgk.bibliothouris.learning.service.dateconverter.LocalDateAttributeConverter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

@Entity
@XmlRootElement
@Table(name = "LIBRARY_MEMBER")
public class Member {

    @Id
    @Column(name = "U_ID")
    @NotBlank(message = "UID for member is missing")
    @Size(max = 36, message = "UID should be 36 characters long")
    private String UUID;

    @Column(name = "BIRTH_DATE")
    @NotBlank(message = "Birth date is missing")
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate birthDate;

    @Column(name = "NATIONAL_NUMBER")
    @NotBlank(message = "National number is missing")
    @Size(max = 32, message = "National Number should be at most 32 characters long")
    private String nationalNumber;

    @NotBlank(message = "First name is missing")
    @Size(max = 35, message = "First name should be at most 35 characters long")
    @Column(name = "FIRST_NAME")
    private String firstName;

    @NotBlank(message = "Last name is missing")
    @Size(max = 35, message = "Last name should be at most 35 characters long")
    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "ADDRESS")
    private String adress;

    @Column(name = "POSTAL_CODE")
    private Integer postalCode;

    @Column(name = "CITY")
    private String city;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE_NUMBER")
    private Integer phoneNumber;

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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
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

    public static class MemberBuilder
    {
        private Member member;

        private MemberBuilder()
        {
            member = new Member();
        }

        public MemberBuilder withUUID(String UUID)
        {
            member.UUID = UUID;
            return this;
        }

        public MemberBuilder withNationalNumber(String nationalNumber)
        {
            member.nationalNumber = nationalNumber;
            return this;
        }

        public MemberBuilder withFirstName(String firstName)
        {
            member.firstName = firstName;
            return this;
        }

        public MemberBuilder withLastName(String lastName)
        {
            member.lastName = lastName;
            return this;
        }

        public MemberBuilder withAdress(String adress)
        {
            member.adress = adress;
            return this;
        }

        public MemberBuilder withPostalCode(Integer postalCode)
        {
            member.postalCode = postalCode;
            return this;
        }

        public MemberBuilder withCity(String city)
        {
            member.city = city;
            return this;
        }

        public MemberBuilder withEmail(String email)
        {
            member.email = email;
            return this;
        }

        public MemberBuilder withPhoneNumber(Integer phoneNumber)
        {
            member.phoneNumber = phoneNumber;
            return this;
        }

        public static MemberBuilder member()
        {
            return new MemberBuilder();
        }

        public Member build()
        {
            return member;
        }
    }
}
