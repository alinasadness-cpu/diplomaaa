package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {

    private final SelenideElement heading = $("h2");
    private final SelenideElement buyButton = $$("button").get(0);
    private final SelenideElement creditButton = $$("button").get(1);

    public MainPage() {
        heading.shouldBe(visible).shouldHave(text("Путешествие дня"));
    }

    public PaymentPage buy() {
        buyButton.click();
        return new PaymentPage();
    }

    public CreditPage buyInCredit() {
        creditButton.click();
        return new CreditPage();
    }
}