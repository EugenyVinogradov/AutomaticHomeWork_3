package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TestOrderingForCard {
        @Test
        void shouldSubmitRequest() {
            open("http://localhost:9999");
            SelenideElement form = $("form");
            form.$("[data-test-id=name] input").setValue("Василий");
            form.$("[data-test-id=phone] input").setValue("+79270000000");
            form.$("[data-test-id=agreement]").click();
            form.$("[type=button]").click();
            $("[data-test-id=order-success]").shouldHave(exactText("  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
        }
        @Test
        void shouldSubmitRequestByNameContainsSpace() {
            open("http://localhost:9999");
            SelenideElement form = $("form");
            form.$("[data-test-id=name] input").setValue("Василий Алибабаевич");
            form.$("[data-test-id=phone] input").setValue("+79270000000");
            form.$("[data-test-id=agreement]").click();
            form.$("[type=button]").click();
            $("[data-test-id=order-success]").shouldHave(exactText("  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
        }
        @Test
        void shouldSubmitRequestByNameContainsSpaceAndHyphen() {
            open("http://localhost:9999");
            SelenideElement form = $("form");
            form.$("[data-test-id=name] input").setValue("Василий Алибабаевич Алибабаев-Задунайский");
            form.$("[data-test-id=phone] input").setValue("+79270000000");
            form.$("[data-test-id=agreement]").click();
            form.$("[type=button]").click();
            $("[data-test-id=order-success]").shouldHave(exactText("  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
        }
        @Test
        void shouldSubmitRequestByPhoneContainsAllZero() {
            open("http://localhost:9999");
            SelenideElement form = $("form");
            form.$("[data-test-id=name] input").setValue("Василий Алибабаевич Алибабаев-Задунайский");
            form.$("[data-test-id=phone] input").setValue("+00000000000");
            form.$("[data-test-id=agreement]").click();
            form.$("[type=button]").click();
            $("[data-test-id=order-success]").shouldHave(exactText("  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
        }
}
