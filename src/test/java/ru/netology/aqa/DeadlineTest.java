package ru.netology.aqa;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.netology.aqa.data.DataHelper;
import ru.netology.aqa.data.UserData;
import ru.netology.aqa.db.SQLHelper;
import ru.netology.aqa.page.LoginPage;

public class DeadlineTest {

    @BeforeAll
    static void setUpAll() {
        SQLHelper.clearData();
        SQLHelper.setUserPassword("vasya", "$2a$10$O5CBjM0kBscP2k0UE2CqPeXKvXvQs8jXqHzqB3Ee8n/8O8w7sZi.");
    }
    @Test
    void shouldLoginSuccessfully() {
        UserData validUser = DataHelper.getValidUser();
        var verificationPage = new LoginPage()
                .validLogin(validUser.getLogin(), validUser.getPassword());

        String code = SQLHelper.getVerificationCode(validUser.getLogin());
        var dashboardPage = verificationPage.validVerify(code);
        dashboardPage.shouldBeVisibleWithHeading("Личный кабинет");
    }

    @Test
    void shouldBlockAfterThreeWrongPasswords() {
        UserData validUser = DataHelper.getValidUser();
        UserData invalidUser = DataHelper.getInvalidUser();
        LoginPage loginPage = new LoginPage();

        loginPage.login(validUser.getLogin(), invalidUser.getPassword());
        loginPage.login(validUser.getLogin(), invalidUser.getPassword());
        loginPage.login(validUser.getLogin(), invalidUser.getPassword());

        loginPage.shouldSeeErrorNotification("Ошибка! Неверно указан логин или пароль");
    }
}