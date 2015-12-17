package fixture;

import cgk.bibliothouris.learning.service.entity.Member;
import net.sf.cglib.core.Local;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;
import java.util.UUID;

public class MemberTestFixture {

    public static Member createMember() {
        LocalDate date = LocalDate.of(2015,4,4);
        return Member.MemberBuilder.member()
                .withAddress("DefaultAdress")
                .withCity("DefaultCity")
                .withEmail("DefaultEmail")
                .withFirstName("Stumpy")
                .withLastName("Lumpy")
                .withNationalNumber(String.valueOf(RandomStringUtils.randomNumeric(12)))
                .withPostalCode(123123)
                .withUUID(UUID.randomUUID().toString())
                .withBirthDate(date)
                .withPhoneNumber(07673322222)
                .build();
    }

    public static Member createMemberWithNoUUID() {
        return Member.MemberBuilder.member()
                .withAddress("DefaultAdress")
                .withCity("DefaultCity")
                .withEmail("DefaultEmail")
                .withFirstName("Stumpy")
                .withNationalNumber("123123123")
                .withPostalCode(123123)
                .withBirthDate(LocalDate.now())
                .withPhoneNumber(07673322222)
                .build();
    }

    public static Member createInvalidMemberWithNoLastName() {
        return Member.MemberBuilder.member()
                .withAddress("DefaultAdress")
                .withCity("DefaultCity")
                .withEmail("DefaultEmail")
                .withFirstName("Stumpy")
                .withNationalNumber("123123123")
                .withPostalCode(123123)
                .withBirthDate(LocalDate.now())
                .withPhoneNumber(07673322222)
                .build();
    }

    public static Member createInvalidMemberWithNoFirstName() {
        return Member.MemberBuilder.member()
                .withAddress("DefaultAdress")
                .withCity("DefaultCity")
                .withEmail("DefaultEmail")
                .withLastName("Lumpy")
                .withNationalNumber("123123123")
                .withPostalCode(123123)
                .withBirthDate(LocalDate.now())
                .withPhoneNumber(07673322222)
                .build();
    }

    public static Member createInvalidMemberWithNoBirthDate() {
        return Member.MemberBuilder.member()
                .withAddress("DefaultAdress")
                .withCity("DefaultCity")
                .withEmail("DefaultEmail")
                .withFirstName("Stumpy")
                .withLastName("Lumpy")
                .withNationalNumber("123123123")
                .withPostalCode(123123)
                .withPhoneNumber(07673322222)
                .build();
    }

    public static Member createInvalidMemberWithNoNationalNumber() {
        return Member.MemberBuilder.member()
                .withAddress("DefaultAdress")
                .withCity("DefaultCity")
                .withEmail("DefaultEmail")
                .withFirstName("Stumpy")
                .withLastName("Lumpy")
                .withPostalCode(123123)
                .withBirthDate(LocalDate.now())
                .withPhoneNumber(07673322222)
                .build();
    }
}
