package ru.netology.aqa.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.aqa.db.SQLHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private final SelenideElement codeField = $("[data-test-id='code'] input");
    private final SelenideElement verifyButton = $("[data-test-id='action-verify']");

    public DashboardPage validVerify(String login) {
        codeField.shouldBe(visible);
        String code = SQLHelper.getVerificationCode(login);
        if (code == null) {
            throw new RuntimeException("Не удалось получить код верификации для пользователя " + login);
        }
        codeField.setValue(code);
        verifyButton.click();
        return new DashboardPage();
    }
}