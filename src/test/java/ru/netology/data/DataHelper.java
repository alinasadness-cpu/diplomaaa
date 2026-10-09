package ru.netology.data;

import lombok.AllArgsConstructor;
import lombok.Data;

public class DataHelper {

    private DataHelper() {}

    public static CardInfo getApprovedCard() {
        return new CardInfo("4444 4444 4444 4441", "12", "27", "IVAN IVANOV", "123");
    }

    public static CardInfo getDeclinedCard() {
        return new CardInfo("4444 4444 4444 4442", "12", "27", "IVAN IVANOV", "123");
    }

    public static CardInfo getInvalidCard15Digits() {
        return new CardInfo("4444 4444 4444 444", "12", "27", "IVAN IVANOV", "123");
    }

    public static CardInfo getInvalidCardWithLetters() {
        return new CardInfo("4444 4444 4444 444A", "12", "27", "IVAN IVANOV", "123");
    }

    public static CardInfo getInvalidMonth13() {
        return new CardInfo("4444 4444 4444 4441", "13", "27", "IVAN IVANOV", "123");
    }

    public static CardInfo getInvalidMonth00() {
        return new CardInfo("4444 4444 4444 4441", "00", "27", "IVAN IVANOV", "123");
    }

    public static CardInfo getExpiredYear() {
        return new CardInfo("4444 4444 4444 4441", "12", "20", "IVAN IVANOV", "123");
    }

    public static CardInfo getInvalidCvc2Digits() {
        return new CardInfo("4444 4444 4444 4441", "12", "27", "IVAN IVANOV", "12");
    }

    public static CardInfo getInvalidCvc4Digits() {
        return new CardInfo("4444 4444 4444 4441", "12", "27", "IVAN IVANOV", "1234");
    }

    public static CardInfo getCyrillicHolder() {
        return new CardInfo("4444 4444 4444 4441", "12", "27", "ИВАН ИВАНОВ", "123");
    }

    public static CardInfo getHolderWithDigits() {
        return new CardInfo("4444 4444 4444 4441", "12", "27", "IVAN123", "123");
    }

    public static CardInfo getHolderWithSpecialChars() {
        return new CardInfo("4444 4444 4444 4441", "12", "27", "IVAN@IVANOV", "123");
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