package ru.netology.aqa;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.aqa.data.DataHelper;
import ru.netology.aqa.data.UserData;
import ru.netology.aqa.db.SQLHelper;
import ru.netology.aqa.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class DeadlineTest {

    @BeforeAll
    static void setUpAll() {
        SQLHelper.clearData();
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldLoginSuccessfully() {
        UserData validUser = DataHelper.getValidUser();
        var verificationPage = new LoginPage()
                .validLogin(validUser.getLogin(), validUser.getPassword());
        var dashboardPage = verificationPage.validVerify(validUser.getLogin());
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