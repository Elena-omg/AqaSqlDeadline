package ru.netology.aqa.page;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private final SelenideElement heading = $("[data-test-id='dashboard']");

    public void shouldBeVisibleWithHeading(String expectedText) {
        heading.shouldBe(visible).shouldHave(text(expectedText));
    }
}