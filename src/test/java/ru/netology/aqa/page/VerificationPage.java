package ru.netology.aqa.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private final SelenideElement codeField = $("[data-test-id='code'] input");
    private final SelenideElement verifyButton = $("[data-test-id='action-verify']");

    public DashboardPage validVerify(String code) {
        codeField.setValue(code);
        verifyButton.click();
        return new DashboardPage();
    }
}