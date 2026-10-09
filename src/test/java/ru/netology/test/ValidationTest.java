package ru.netology.test;

import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.page.MainPage;
import ru.netology.page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;

@DisplayName("Валидация полей формы оплаты")
public class ValidationTest {

    @BeforeAll
    static void setUp() {
        WebDriverManager.firefoxdriver().setup();
        Configuration.browser = "firefox";
        Configuration.baseUrl = "http://localhost:8080";
        Configuration.headless = false;
    }

    @Test
    @DisplayName("Неверный формат номера карты (15 цифр)")
    void shouldShowErrorFor15Digits() {
        var mainPage = open("/", MainPage.class);
        PaymentPage paymentPage = mainPage.buy();
        paymentPage.fillForm(DataHelper.getInvalidCard15Digits());
        paymentPage.verifyValidationError("Неверный формат");
    }

    @Test
    @DisplayName("Номер карты с буквами")
    void shouldShowErrorForLettersInCard() {
        var mainPage = open("/", MainPage.class);
        PaymentPage paymentPage = mainPage.buy();
        paymentPage.fillForm(DataHelper.getInvalidCardWithLetters());
        paymentPage.verifyValidationError("Неверный формат");
    }

    @Test
    @DisplayName("Неверный месяц 13")
    void shouldShowErrorForMonth13() {
        var mainPage = open("/", MainPage.class);
        PaymentPage paymentPage = mainPage.buy();
        paymentPage.fillForm(DataHelper.getInvalidMonth13());
        paymentPage.verifyValidationError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Месяц 00")
    void shouldShowErrorForMonth00() {
        var mainPage = open("/", MainPage.class);
        PaymentPage paymentPage = mainPage.buy();
        paymentPage.fillForm(DataHelper.getInvalidMonth00());
        paymentPage.verifyValidationError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Истёкший год")
    void shouldShowErrorForExpiredYear() {
        var mainPage = open("/", MainPage.class);
        PaymentPage paymentPage = mainPage.buy();
        paymentPage.fillForm(DataHelper.getExpiredYear());
        paymentPage.verifyValidationError("Истёк срок действия карты");
    }

    @Test
    @DisplayName("CVC из 2 цифр")
    void shouldShowErrorForCvc2Digits() {
        var mainPage = open("/", MainPage.class);
        PaymentPage paymentPage = mainPage.buy();
        paymentPage.fillForm(DataHelper.getInvalidCvc2Digits());
        paymentPage.verifyValidationError("Неверный формат");
    }

    @Test
    @DisplayName("CVC из 4 цифр")
    void shouldShowErrorForCvc4Digits() {
        var mainPage = open("/", MainPage.class);
        PaymentPage paymentPage = mainPage.buy();
        paymentPage.fillForm(DataHelper.getInvalidCvc4Digits());
        paymentPage.verifyValidationError("Неверный формат");
    }

    @Test
    @DisplayName("Владелец на кириллице")
    void shouldShowErrorForCyrillicHolder() {
        var mainPage = open("/", MainPage.class);
        PaymentPage paymentPage = mainPage.buy();
        paymentPage.fillForm(DataHelper.getCyrillicHolder());
        paymentPage.verifyValidationError("Неверный формат");
    }

    @Test
    @DisplayName("Владелец с цифрами")
    void shouldShowErrorForHolderWithDigits() {
        var mainPage = open("/", MainPage.class);
        PaymentPage paymentPage = mainPage.buy();
        paymentPage.fillForm(DataHelper.getHolderWithDigits());
        paymentPage.verifyValidationError("Неверный формат");
    }

    @Test
    @DisplayName("Владелец со спецсимволами")
    void shouldShowErrorForHolderWithSpecialChars() {
        var mainPage = open("/", MainPage.class);
        PaymentPage paymentPage = mainPage.buy();
        paymentPage.fillForm(DataHelper.getHolderWithSpecialChars());
        paymentPage.verifyValidationError("Неверный формат");
    }
}