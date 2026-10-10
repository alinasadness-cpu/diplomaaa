package ru.netology.test;

import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.page.MainPage;
import ru.netology.page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;

@DisplayName("Валидация полей формы оплаты")
public class ValidationTest extends BaseTest {


    @Test
    @DisplayName("Неверный формат номера карты (15 цифр)")
    void shouldShowErrorFor15Digits() {
        var mainPage = open("/", MainPage.class);
        PaymentPage paymentPage = mainPage.buy();
        var valid = DataHelper.getApprovedCard();
        var invalid = new DataHelper.CardInfo(
                DataHelper.getInvalidShortCardNumber(),
                valid.getMonth(), valid.getYear(), valid.getHolder(), valid.getCvc()
        );
        paymentPage.fillForm(invalid);
        paymentPage.verifyFieldError("Неверный формат");
    }

    @Test
    @DisplayName("Номер карты с буквами")
    void shouldShowErrorForLettersInCard() {
        var mainPage = open("/", MainPage.class);
        PaymentPage paymentPage = mainPage.buy();
        var valid = DataHelper.getApprovedCard();
        var invalid = new DataHelper.CardInfo(
                DataHelper.getInvalidCardNumberWithLetter(),
                valid.getMonth(), valid.getYear(), valid.getHolder(), valid.getCvc()
        );
        paymentPage.fillForm(invalid);
        paymentPage.verifyFieldError("Неверный формат");
    }

    @Test
    @DisplayName("Пустой номер карты")
    void shouldShowErrorForEmptyCard() {
        var mainPage = open("/", MainPage.class);
        PaymentPage paymentPage = mainPage.buy();
        var valid = DataHelper.getApprovedCard();
        var invalid = new DataHelper.CardInfo(
                DataHelper.getEmptyCardNumber(),
                valid.getMonth(), valid.getYear(), valid.getHolder(), valid.getCvc()
        );
        paymentPage.fillForm(invalid);
        paymentPage.verifyFieldError("Неверный формат");
    }


    @Test
    @DisplayName("Неверный месяц 13")
    void shouldShowErrorForMonth13() {
        var mainPage = open("/", MainPage.class);
        PaymentPage paymentPage = mainPage.buy();
        var valid = DataHelper.getApprovedCard();
        var invalid = new DataHelper.CardInfo(
                valid.getNumber(),
                DataHelper.getInvalidMonth13(), valid.getYear(), valid.getHolder(), valid.getCvc()
        );
        paymentPage.fillForm(invalid);
        paymentPage.verifyFieldError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Месяц 00")
    void shouldShowErrorForMonth00() {
        var mainPage = open("/", MainPage.class);
        PaymentPage paymentPage = mainPage.buy();
        var valid = DataHelper.getApprovedCard();
        var invalid = new DataHelper.CardInfo(
                valid.getNumber(),
                DataHelper.getInvalidMonth00(), valid.getYear(), valid.getHolder(), valid.getCvc()
        );
        paymentPage.fillForm(invalid);
        paymentPage.verifyFieldError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Пустой месяц")
    void shouldShowErrorForEmptyMonth() {
        var mainPage = open("/", MainPage.class);
        PaymentPage paymentPage = mainPage.buy();
        var valid = DataHelper.getApprovedCard();
        var invalid = new DataHelper.CardInfo(
                valid.getNumber(),
                DataHelper.getEmptyMonth(), valid.getYear(), valid.getHolder(), valid.getCvc()
        );
        paymentPage.fillForm(invalid);
        paymentPage.verifyFieldError("Неверный формат");
    }


    @Test
    @DisplayName("Истёкший год")
    void shouldShowErrorForExpiredYear() {
        var mainPage = open("/", MainPage.class);
        PaymentPage paymentPage = mainPage.buy();
        var valid = DataHelper.getApprovedCard();
        var invalid = new DataHelper.CardInfo(
                valid.getNumber(),
                valid.getMonth(), DataHelper.getExpiredYear(), valid.getHolder(), valid.getCvc()
        );
        paymentPage.fillForm(invalid);
        paymentPage.verifyFieldError("Истёк срок действия карты");
    }

    @Test
    @DisplayName("Пустой год")
    void shouldShowErrorForEmptyYear() {
        var mainPage = open("/", MainPage.class);
        PaymentPage paymentPage = mainPage.buy();
        var valid = DataHelper.getApprovedCard();
        var invalid = new DataHelper.CardInfo(
                valid.getNumber(),
                valid.getMonth(), DataHelper.getEmptyYear(), valid.getHolder(), valid.getCvc()
        );
        paymentPage.fillForm(invalid);
        paymentPage.verifyFieldError("Неверный формат");
    }

    @Test
    @DisplayName("CVC из 2 цифр")
    void shouldShowErrorForCvc2Digits() {
        var mainPage = open("/", MainPage.class);
        PaymentPage paymentPage = mainPage.buy();
        var valid = DataHelper.getApprovedCard();
        var invalid = new DataHelper.CardInfo(
                valid.getNumber(), valid.getMonth(), valid.getYear(),
                valid.getHolder(), DataHelper.getCvc2Digits()
        );
        paymentPage.fillForm(invalid);
        paymentPage.verifyFieldError("Неверный формат");
    }

    @Test
    @DisplayName("Пустой CVC")
    void shouldShowErrorForEmptyCvc() {
        var mainPage = open("/", MainPage.class);
        PaymentPage paymentPage = mainPage.buy();
        var valid = DataHelper.getApprovedCard();
        var invalid = new DataHelper.CardInfo(
                valid.getNumber(), valid.getMonth(), valid.getYear(),
                valid.getHolder(), DataHelper.getEmptyCvc()
        );
        paymentPage.fillForm(invalid);
        paymentPage.verifyFieldError("Поле обязательно для заполнения");
    }


    @Test
    @DisplayName("Владелец на кириллице")
    void shouldShowErrorForCyrillicHolder() {
        var mainPage = open("/", MainPage.class);
        PaymentPage paymentPage = mainPage.buy();
        var valid = DataHelper.getApprovedCard();
        var invalid = new DataHelper.CardInfo(
                valid.getNumber(), valid.getMonth(), valid.getYear(),
                DataHelper.getCyrillicHolder(), valid.getCvc()
        );
        paymentPage.fillForm(invalid);
        paymentPage.verifyFieldError("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Владелец с цифрами")
    void shouldShowErrorForHolderWithDigits() {
        var mainPage = open("/", MainPage.class);
        PaymentPage paymentPage = mainPage.buy();
        var valid = DataHelper.getApprovedCard();
        var invalid = new DataHelper.CardInfo(
                valid.getNumber(), valid.getMonth(), valid.getYear(),
                DataHelper.getHolderWithDigits(), valid.getCvc()
        );
        paymentPage.fillForm(invalid);
        paymentPage.verifyFieldError("Неверный формат");
    }

    @Test
    @DisplayName("Владелец со спецсимволами")
    void shouldShowErrorForHolderWithSpecialChars() {
        var mainPage = open("/", MainPage.class);
        PaymentPage paymentPage = mainPage.buy();
        var valid = DataHelper.getApprovedCard();
        var invalid = new DataHelper.CardInfo(
                valid.getNumber(), valid.getMonth(), valid.getYear(),
                DataHelper.getHolderWithSpecialChars(), valid.getCvc()
        );
        paymentPage.fillForm(invalid);
        paymentPage.verifyFieldError("Неверный формат");
    }

    @Test
    @DisplayName("Пустой владелец")
    void shouldShowErrorForEmptyHolder() {
        var mainPage = open("/", MainPage.class);
        PaymentPage paymentPage = mainPage.buy();
        var valid = DataHelper.getApprovedCard();
        var invalid = new DataHelper.CardInfo(
                valid.getNumber(), valid.getMonth(), valid.getYear(),
                DataHelper.getEmptyHolder(), valid.getCvc()
        );
        paymentPage.fillForm(invalid);
        paymentPage.verifyFieldError("Поле обязательно для заполнения");
    }
}
