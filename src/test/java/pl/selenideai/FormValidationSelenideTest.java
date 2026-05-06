package pl.selenideai;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

class FormValidationSelenideTest {

    private static String pageUrl;

    @BeforeAll
    static void setupBrowser() {
        Configuration.browser = "firefox";
        Configuration.headless = true;
        Configuration.timeout = 10000;
        pageUrl = Path.of("index.html").toAbsolutePath().toUri().toString();
    }

    @BeforeEach
    void openForm() {
        closeWebDriver();
        open(pageUrl);
    }

    @Test
    void tc01_emptyFormShowsAllErrors() {
        $("#submit-btn").click();

        $("#firstName-error").shouldBe(visible);
        $("#lastName-error").shouldBe(visible);
        $("#email-error").shouldBe(visible);
        $("#password-error").shouldBe(visible);
        $("#confirmPassword-error").shouldBe(visible);
    }

    @Test
    void tc02_singleMissingRequiredFieldShowsOnlyThatError() {
        $("#lastName").setValue("Kowalski");
        $("#email").setValue("jan@example.com");
        $("#password").setValue("Haslo123!");
        $("#confirmPassword").setValue("Haslo123!");

        $("#submit-btn").click();

        $("#firstName-error").shouldBe(visible);
        $("#lastName-error").shouldNotBe(visible);
        $("#email-error").shouldNotBe(visible);
        $("#password-error").shouldNotBe(visible);
        $("#confirmPassword-error").shouldNotBe(visible);
    }

    @Test
    void tc03_invalidEmailShowsEmailError() {
        $("#firstName").setValue("Jan");
        $("#lastName").setValue("Kowalski");
        $("#email").setValue("jan-at-example.com");
        $("#password").setValue("Haslo123!");
        $("#confirmPassword").setValue("Haslo123!");

        $("#submit-btn").click();

        $("#email-error").shouldBe(visible).shouldHave(text("adres e-mail"));
    }

    @Test
    void tc04_shortPasswordShowsMinLengthError() {
        $("#firstName").setValue("Jan");
        $("#lastName").setValue("Kowalski");
        $("#email").setValue("jan@example.com");
        $("#password").setValue("1234567");
        $("#confirmPassword").setValue("1234567");

        $("#submit-btn").click();

        $("#password-error").shouldBe(visible).shouldHave(text("8"));
    }

    @Test
    void tc05_passwordMismatchShowsConfirmPasswordError() {
        $("#firstName").setValue("Jan");
        $("#lastName").setValue("Kowalski");
        $("#email").setValue("jan@example.com");
        $("#password").setValue("Haslo123!");
        $("#confirmPassword").setValue("InneHaslo123!");

        $("#submit-btn").click();

        $("#confirmPassword-error").shouldBe(visible);
    }

    @Test
    void tc06_validDataShowsSuccessAndHidesForm() {
        $("#firstName").setValue("Jan");
        $("#lastName").setValue("Kowalski");
        $("#email").setValue("jan@example.com");
        $("#password").setValue("Haslo123!");
        $("#confirmPassword").setValue("Haslo123!");

        $("#submit-btn").click();

        $("#success-msg").shouldBe(visible);
        $("#register-form").shouldBe(hidden);
    }
}



