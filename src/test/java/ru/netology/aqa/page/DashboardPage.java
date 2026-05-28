package ru.netology.aqa.page;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    public void shouldBeVisibleWithHeading(String expectedText) {
        $(byText(expectedText)).shouldBe(visible);
    }
}