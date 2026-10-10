package ru.netology.test;

import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.db.DbUtils;
import ru.netology.page.MainPage;
import ru.netology.page.CreditPage;

import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Покупка тура в кредит")
public class CreditTest extends BaseTest {

    @BeforeEach
    void cleanUp() {
        DbUtils.cleanDatabase();
    }

    @Test
    @DisplayName("Успешный кредит APPROVED-картой")
    void shouldApproveCredit() {
        var mainPage = open("/", MainPage.class);
        CreditPage creditPage = mainPage.buyInCredit();
        creditPage.fillForm(DataHelper.getApprovedCard());
        creditPage.verifySuccess();

        assertThat(DbUtils.getCreditStatus()).isEqualTo("APPROVED");
        assertThat(DbUtils.getOrderCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("Отказ в кредите DECLINED-картой")
    void shouldDeclineCredit() {
        var mainPage = open("/", MainPage.class);
        CreditPage creditPage = mainPage.buyInCredit();
        creditPage.fillForm(DataHelper.getDeclinedCard());
        creditPage.verifyError();

        assertThat(DbUtils.getCreditStatus()).isEqualTo("DECLINED");
    }
}