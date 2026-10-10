package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {

    private static final Faker faker = new Faker(new Locale("en"));

    private DataHelper() {}

    public static String getApprovedCardNumber() {
        return "4444 4444 4444 4441";
    }

    public static String getDeclinedCardNumber() {
        return "4444 4444 4444 4442";
    }

    public static String getInvalidShortCardNumber() {
        return "4444 4444 4444 444";
    }

    public static String getInvalidCardNumberWithLetter() {
        return "4444 4444 4444 444A";
    }

    public static String getEmptyCardNumber() {
        return "";
    }

    public static String getValidMonth() {
        int month = faker.number().numberBetween(1, 13);
        return String.format("%02d", month);
    }


    public static String getValidYear() {
        int year = LocalDate.now().plusYears(1).getYear();
        return String.valueOf(year).substring(2);
    }

    public static String getExpiredYear() {
        int year = LocalDate.now().minusYears(1).getYear();
        return String.valueOf(year).substring(2);
    }

    public static String getInvalidMonth00() {
        return "00";
    }

    public static String getInvalidMonth13() {
        return "13";
    }

    public static String getEmptyMonth() {
        return "";
    }

    public static String getEmptyYear() {
        return "";
    }

    public static String getValidHolder() {
        String firstName = faker.name().firstName().toUpperCase();
        String lastName = faker.name().lastName().toUpperCase();
        return firstName + " " + lastName;
    }

    public static String getCyrillicHolder() {
        return "ИВАН ИВАНОВ";
    }

    public static String getHolderWithDigits() {
        return "IVAN123";
    }

    public static String getHolderWithSpecialChars() {
        return "IVAN@IVANOV";
    }

    public static String getEmptyHolder() {
        return "";
    }


    public static String getValidCvc() {
        int cvc = faker.number().numberBetween(100, 1000);
        return String.valueOf(cvc);
    }

    public static String getCvc2Digits() {
        return "12";
    }

    public static String getCvc4Digits() {
        return "1234";
    }


    public static String getEmptyCvc() {
        return "";
    }

    public static CardInfo getValidCard() {
        return new CardInfo(
                getApprovedCardNumber(),
                getValidMonth(),
                getValidYear(),
                getValidHolder(),
                getValidCvc()
        );
    }


    public static CardInfo getApprovedCard() {
        return new CardInfo(
                getApprovedCardNumber(),
                getValidMonth(),
                getValidYear(),
                getValidHolder(),
                getValidCvc()
        );
    }


    public static CardInfo getDeclinedCard() {
        return new CardInfo(
                getDeclinedCardNumber(),
                getValidMonth(),
                getValidYear(),
                getValidHolder(),
                getValidCvc()
        );
    }


    @Data
    @AllArgsConstructor
    public static class CardInfo {
        private String number;
        private String month;
        private String year;
        private String holder;
        private String cvc;
    }
}