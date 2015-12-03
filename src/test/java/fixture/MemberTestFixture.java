package fixture;

import cgk.bibliothouris.learning.service.entity.Member;

import java.time.LocalDate;
import java.util.UUID;

public class MemberTestFixture {

    public static Member createMember() {
        return Member.MemberBuilder.member()
                .withAdress("DefaultAdress")
                .withCity("DefaultCity")
                .withEmail("DefaultEmail")
                .withFirstName("Stumpy")
                .withLastName("Lumpy")
                .withNationalNumber("123123123")
                .withPostalCode(123123)
                .withUUID(UUID.randomUUID().toString())
                .withBirthDate(LocalDate.now())
                .withPhoneNumber(07673322222)
                .build();
    }

    public static Member createInvalidMemberWithNoLastName() {
        return Member.MemberBuilder.member()
                .withAdress("DefaultAdress")
                .withCity("DefaultCity")
                .withEmail("DefaultEmail")
                .withFirstName("Stumpy")
                .withNationalNumber("123123123")
                .withPostalCode(123123)
                .withUUID(UUID.randomUUID().toString())
                .withBirthDate(LocalDate.now())
                .withPhoneNumber(07673322222)
                .build();
    }

    public static Member createInvalidMemberWithNoFirstName() {
        return Member.MemberBuilder.member()
                .withAdress("DefaultAdress")
                .withCity("DefaultCity")
                .withEmail("DefaultEmail")
                .withLastName("Lumpy")
                .withNationalNumber("123123123")
                .withPostalCode(123123)
                .withUUID(UUID.randomUUID().toString())
                .withBirthDate(LocalDate.now())
                .withPhoneNumber(07673322222)
                .build();
    }

    public static Member createInvalidMemberWithNoBirthDate() {
        return Member.MemberBuilder.member()
                .withAdress("DefaultAdress")
                .withCity("DefaultCity")
                .withEmail("DefaultEmail")
                .withFirstName("Stumpy")
                .withLastName("Lumpy")
                .withNationalNumber("123123123")
                .withPostalCode(123123)
                .withUUID(UUID.randomUUID().toString())
                .withPhoneNumber(07673322222)
                .build();
    }

    public static Member createInvalidMemberWithNoNationalNumber() {
        return Member.MemberBuilder.member()
                .withAdress("DefaultAdress")
                .withCity("DefaultCity")
                .withEmail("DefaultEmail")
                .withFirstName("Stumpy")
                .withLastName("Lumpy")
                .withPostalCode(123123)
                .withUUID(UUID.randomUUID().toString())
                .withBirthDate(LocalDate.now())
                .withPhoneNumber(07673322222)
                .build();
    }
}
