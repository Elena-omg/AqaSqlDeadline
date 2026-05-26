package ru.netology.aqa;

import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.aqa.data.DataHelper;
import ru.netology.aqa.db.DbUtils;
import ru.netology.aqa.page.LoginPage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeadlineTest {

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().driverVersion("148.0.7778.179").setup();
        Configuration.browser = "chrome";
        Configuration.headless = true;

        try {
            DbUtils.clearData();
            DbUtils.setUserPassword("vasya", "$2a$10$/MvtTGU5SCjRwuznXWQgt.FC2GkIwcMR4UImVCP6e4fevSo4E.28.");
        } catch (Exception e) {
            System.err.println("Ошибка подготовки БД: " + e.getMessage());
        }
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldLoginSuccessfully() throws Exception {
        var verificationPage = new LoginPage()
                .validLogin(DataHelper.getValidLogin(), DataHelper.getValidPassword());

        String code = DbUtils.getVerificationCode(DataHelper.getValidLogin());

        boolean dashboardVisible = verificationPage
                .validVerify(code)
                .isVisible();

        assertTrue(dashboardVisible, "Личный кабинет должен отображаться после успешной верификации");
    }

    @Test
    void shouldBlockAfterThreeWrongPasswords() throws Exception {
        var loginPage = new LoginPage();

        loginPage.validLogin(DataHelper.getValidLogin(), DataHelper.getInvalidPassword());
        open("http://localhost:9999");
        loginPage.validLogin(DataHelper.getValidLogin(), DataHelper.getInvalidPassword());
        open("http://localhost:9999");
        loginPage.validLogin(DataHelper.getValidLogin(), DataHelper.getInvalidPassword());
        var errorElement = $("[data-test-id='error-notification']");
        errorElement.shouldBe(visible);
    }
}