package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TestOrderingForCard {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }
    @Test
    void shouldSubmitRequest() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Василий");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$("[type=button]").click();
        $("[data-test-id=order-success]").shouldHave(exactText("  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldSubmitRequestByNameContainsSpace() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Василий Алибабаевич");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$("[type=button]").click();
        $("[data-test-id=order-success]").shouldHave(exactText("  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldSubmitRequestByNameContainsSpaceAndHyphen() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Василий Алибабаев-Задунайский");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$("[type=button]").click();
        $("[data-test-id=order-success]").shouldHave(exactText("  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }


    @Test
    void shouldValidateFormByNameContainsLatin() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Василий Dou");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$("[type=button]").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldValidateFormByNameContainsSpecialSimbols() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Василий Алибабаевич.");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$("[type=button]").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldValidateFormByNameIsEmpty() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$("[type=button]").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldValidateFormByNameContainOnlySpaces() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("           ");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$("[type=button]").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldValidateFormByPhoneNotContainFirstPlus() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Василий Алибабаевич");
        form.$("[data-test-id=phone] input").setValue("79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$("[type=button]").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldValidateFormByPhoneNotContainPlusNotFirst() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Василий Алибабаевич");
        form.$("[data-test-id=phone] input").setValue("79270000000+");
        form.$("[data-test-id=agreement]").click();
        form.$("[type=button]").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldValidateFormByPhoneNotContainFewerThanElevenDigits() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Василий Алибабаевич");
        form.$("[data-test-id=phone] input").setValue("+7927000000");
        form.$("[data-test-id=agreement]").click();
        form.$("[type=button]").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldValidateFormByPhoneNotContainMoreThanElevenDigits() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Василий Алибабаевич");
        form.$("[data-test-id=phone] input").setValue("+792700000001");
        form.$("[data-test-id=agreement]").click();
        form.$("[type=button]").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldValidateFormByNotIncludedCheckbox() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Василий Алибабаевич");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[type=button]").click();
        $("[data-test-id='agreement'].input_invalid").shouldBe(Condition.visible);
    }
        @Test
    void shouldValidateFormByNameContainsOneHyphen() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("-");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$("[type=button]").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }
    @Test
    void shouldValidateFormByNameContainsOnlyHyphens() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("------");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$("[type=button]").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }
    @Test
    void shouldValidateFormByNameContainsMoreTwoWords() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Василий Алибабаевич Алибабаев");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$("[type=button]").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }
    @Test
    void shouldValidateFormByPhoneIsEmpty() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Василий Алибабаевич");
        form.$("[data-test-id=phone] input").setValue("");
        form.$("[data-test-id=agreement]").click();
        form.$("[type=button]").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }
}
