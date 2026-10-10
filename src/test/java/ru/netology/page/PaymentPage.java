package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentPage {

    private final SelenideElement cardNumberField = $("input[placeholder='0000 0000 0000 0000']");
    private final SelenideElement monthField = $("input[placeholder='08']");
    private final SelenideElement yearField = $("input[placeholder='22']");
    private final SelenideElement holderField = $$("input.input__control").get(3);
    private final SelenideElement cvcField = $("input[placeholder='999']");
    private final SelenideElement continueButton = $$("button").findBy(text("Продолжить"));
    private final SelenideElement successNotification = $(".notification_status_ok");
    private final SelenideElement errorNotification = $(".notification_status_error");

    public PaymentPage() {
        cardNumberField.shouldBe(visible);
    }

    public void fillForm(DataHelper.CardInfo cardInfo) {
        cardNumberField.setValue(cardInfo.getNumber());
        monthField.setValue(cardInfo.getMonth());
        yearField.setValue(cardInfo.getYear());
        holderField.setValue(cardInfo.getHolder());
        cvcField.setValue(cardInfo.getCvc());
        continueButton.click();
    }

    public void verifySuccess() {
        successNotification.shouldBe(visible).shouldHave(text("Успешно"));
    }

    public void verifyError() {
        errorNotification.shouldBe(visible).shouldHave(text("Ошибка"));
    }

    public void verifyValidationError(String expectedText) {
        errorNotification.shouldBe(visible).shouldHave(text(expectedText));
    }

    public void verifyFieldError(String expectedText) {
        $(".input__sub").shouldBe(visible).shouldHave(text(expectedText));
    }
}