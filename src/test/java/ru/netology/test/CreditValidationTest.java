package ru.netology.test;

import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.page.CreditPage;
import ru.netology.page.MainPage;

import static com.codeborne.selenide.Selenide.open;

@DisplayName("Валидация полей формы кредита")
public class CreditValidationTest extends BaseTest {

    @Test
    @DisplayName("Неверный формат номера карты (15 цифр)")
    void shouldShowErrorFor15Digits() {
        var mainPage = open("/", MainPage.class);
        CreditPage creditPage = mainPage.buyInCredit();
        var valid = DataHelper.getApprovedCard();
        var invalid = new DataHelper.CardInfo(
                DataHelper.getInvalidShortCardNumber(),
                valid.getMonth(), valid.getYear(), valid.getHolder(), valid.getCvc()
        );
        creditPage.fillForm(invalid);
        creditPage.verifyFieldError("Неверный формат");
    }

    @Test
    @DisplayName("Номер карты с буквами")
    void shouldShowErrorForLettersInCard() {
        var mainPage = open("/", MainPage.class);
        CreditPage creditPage = mainPage.buyInCredit();
        var valid = DataHelper.getApprovedCard();
        var invalid = new DataHelper.CardInfo(
                DataHelper.getInvalidCardNumberWithLetter(),
                valid.getMonth(), valid.getYear(), valid.getHolder(), valid.getCvc()
        );
        creditPage.fillForm(invalid);
        creditPage.verifyFieldError("Неверный формат");
    }

    @Test
    @DisplayName("Пустой номер карты")
    void shouldShowErrorForEmptyCard() {
        var mainPage = open("/", MainPage.class);
        CreditPage creditPage = mainPage.buyInCredit();
        var valid = DataHelper.getApprovedCard();
        var invalid = new DataHelper.CardInfo(
                DataHelper.getEmptyCardNumber(),
                valid.getMonth(), valid.getYear(), valid.getHolder(), valid.getCvc()
        );
        creditPage.fillForm(invalid);
        creditPage.verifyFieldError("Неверный формат");
    }

    // ===== Месяц =====

    @Test
    @DisplayName("Неверный месяц 13")
    void shouldShowErrorForMonth13() {
        var mainPage = open("/", MainPage.class);
        CreditPage creditPage = mainPage.buyInCredit();
        var valid = DataHelper.getApprovedCard();
        var invalid = new DataHelper.CardInfo(
                valid.getNumber(),
                DataHelper.getInvalidMonth13(), valid.getYear(), valid.getHolder(), valid.getCvc()
        );
        creditPage.fillForm(invalid);
        creditPage.verifyFieldError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Месяц 00")
    void shouldShowErrorForMonth00() {
        var mainPage = open("/", MainPage.class);
        CreditPage creditPage = mainPage.buyInCredit();
        var valid = DataHelper.getApprovedCard();
        var invalid = new DataHelper.CardInfo(
                valid.getNumber(),
                DataHelper.getInvalidMonth00(), valid.getYear(), valid.getHolder(), valid.getCvc()
        );
        creditPage.fillForm(invalid);
        creditPage.verifyFieldError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Пустой месяц")
    void shouldShowErrorForEmptyMonth() {
        var mainPage = open("/", MainPage.class);
        CreditPage creditPage = mainPage.buyInCredit();
        var valid = DataHelper.getApprovedCard();
        var invalid = new DataHelper.CardInfo(
                valid.getNumber(),
                DataHelper.getEmptyMonth(), valid.getYear(), valid.getHolder(), valid.getCvc()
        );
        creditPage.fillForm(invalid);
        creditPage.verifyFieldError("Неверный формат");
    }

    // ===== Год =====

    @Test
    @DisplayName("Истёкший год")
    void shouldShowErrorForExpiredYear() {
        var mainPage = open("/", MainPage.class);
        CreditPage creditPage = mainPage.buyInCredit();
        var valid = DataHelper.getApprovedCard();
        var invalid = new DataHelper.CardInfo(
                valid.getNumber(),
                valid.getMonth(), DataHelper.getExpiredYear(), valid.getHolder(), valid.getCvc()
        );
        creditPage.fillForm(invalid);
        creditPage.verifyFieldError("Истёк срок действия карты");
    }

    @Test
    @DisplayName("Пустой год")
    void shouldShowErrorForEmptyYear() {
        var mainPage = open("/", MainPage.class);
        CreditPage creditPage = mainPage.buyInCredit();
        var valid = DataHelper.getApprovedCard();
        var invalid = new DataHelper.CardInfo(
                valid.getNumber(),
                valid.getMonth(), DataHelper.getEmptyYear(), valid.getHolder(), valid.getCvc()
        );
        creditPage.fillForm(invalid);
        creditPage.verifyFieldError("Неверный формат");
    }

    // ===== CVC =====

    @Test
    @DisplayName("CVC из 2 цифр")
    void shouldShowErrorForCvc2Digits() {
        var mainPage = open("/", MainPage.class);
        CreditPage creditPage = mainPage.buyInCredit();
        var valid = DataHelper.getApprovedCard();
        var invalid = new DataHelper.CardInfo(
                valid.getNumber(), valid.getMonth(), valid.getYear(),
                valid.getHolder(), DataHelper.getCvc2Digits()
        );
        creditPage.fillForm(invalid);
        creditPage.verifyFieldError("Неверный формат");
    }

    @Test
    @DisplayName("Пустой CVC")
    void shouldShowErrorForEmptyCvc() {
        var mainPage = open("/", MainPage.class);
        CreditPage creditPage = mainPage.buyInCredit();
        var valid = DataHelper.getApprovedCard();
        var invalid = new DataHelper.CardInfo(
                valid.getNumber(), valid.getMonth(), valid.getYear(),
                valid.getHolder(), DataHelper.getEmptyCvc()
        );
        creditPage.fillForm(invalid);
        creditPage.verifyFieldError("Поле обязательно для заполнения");
    }

    // ===== Владелец =====

    @Test
    @DisplayName("Владелец на кириллице")
    void shouldShowErrorForCyrillicHolder() {
        var mainPage = open("/", MainPage.class);
        CreditPage creditPage = mainPage.buyInCredit();
        var valid = DataHelper.getApprovedCard();
        var invalid = new DataHelper.CardInfo(
                valid.getNumber(), valid.getMonth(), valid.getYear(),
                DataHelper.getCyrillicHolder(), valid.getCvc()
        );
        creditPage.fillForm(invalid);
        creditPage.verifyFieldError("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Владелец с цифрами")
    void shouldShowErrorForHolderWithDigits() {
        var mainPage = open("/", MainPage.class);
        CreditPage creditPage = mainPage.buyInCredit();
        var valid = DataHelper.getApprovedCard();
        var invalid = new DataHelper.CardInfo(
                valid.getNumber(), valid.getMonth(), valid.getYear(),
                DataHelper.getHolderWithDigits(), valid.getCvc()
        );
        creditPage.fillForm(invalid);
        creditPage.verifyFieldError("Неверный формат");
    }

    @Test
    @DisplayName("Владелец со спецсимволами")
    void shouldShowErrorForHolderWithSpecialChars() {
        var mainPage = open("/", MainPage.class);
        CreditPage creditPage = mainPage.buyInCredit();
        var valid = DataHelper.getApprovedCard();
        var invalid = new DataHelper.CardInfo(
                valid.getNumber(), valid.getMonth(), valid.getYear(),
                DataHelper.getHolderWithSpecialChars(), valid.getCvc()
        );
        creditPage.fillForm(invalid);
        creditPage.verifyFieldError("Неверный формат");
    }

    @Test
    @DisplayName("Пустой владелец")
    void shouldShowErrorForEmptyHolder() {
        var mainPage = open("/", MainPage.class);
        CreditPage creditPage = mainPage.buyInCredit();
        var valid = DataHelper.getApprovedCard();
        var invalid = new DataHelper.CardInfo(
                valid.getNumber(), valid.getMonth(), valid.getYear(),
                DataHelper.getEmptyHolder(), valid.getCvc()
        );
        creditPage.fillForm(invalid);
        creditPage.verifyFieldError("Поле обязательно для заполнения");
    }
}