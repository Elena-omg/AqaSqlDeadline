package ru.netology.aqa.page;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private final SelenideElement heading = $("[data-test-id='dashboard']");

    public boolean isVisible() {
        return heading.isDisplayed();
    }

    // Альтернативно — проверка через Condition:
    public void shouldBeVisible() {
        heading.shouldBe(visible);
    }
}