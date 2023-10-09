import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

class AuthorizationTest {

    @BeforeEach
    void setup() { open("http://localhost:9999/"); }

    @Test
    @DisplayName("Should successfully authorize with registered user")
    void shouldSuccessfullyAuthorizedWithRegisteredUser() {
        var registeredUser = DataGenerate.Registration.getRegisteredUser("active");
        $("[data-test-id=\"login\"] input").setValue(registeredUser.getLogin());
        $("[data-test-id=\"password\"] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("h2").shouldHave(Condition.exactText("Личный кабинет")).shouldBe(Condition.visible);
    }
    @Test
    @DisplayName("Should get error message if user is not registered")
    void errorMessageIfUserIsNotRegistered() {
        var notRegisteredUser = DataGenerate.Registration.getUser("active");
        $("[data-test-id=\"login\"] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id=\"password\"] input").setValue(notRegisteredUser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"))
                .shouldBe((Condition.visible));
    }
    @Test
    @DisplayName("Should get error message if user has blocked login")
    void errorMessageIfUserHasBlockedLogin() {
        var blockedUser = DataGenerate.Registration.getRegisteredUser("blocked");
        $("[data-test-id=\"login\"] input").setValue(blockedUser.getLogin());
        $("[data-test-id=\"password\"] input").setValue(blockedUser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.text("Ошибка! Пользователь заблокирован"))
                .shouldBe((Condition.visible));
    }
    @Test
    @DisplayName("Should get error message if user entered an incorrect login")
    void errorMessageIfUserEnteredAnIncorrectLogin() {
        var registeredUser = DataGenerate.Registration.getUser("active");
        var incorrectLogin = DataGenerate.getLoginName();
        $("[data-test-id=\"login\"] input").setValue(incorrectLogin);
        $("[data-test-id=\"password\"] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"))
                .shouldBe((Condition.visible));
    }
    @Test
    @DisplayName("Should get error message if user entered an incorrect password")
    void errorMessageIfUserEnteredAnIncorrectPassword() {
        var registeredUser = DataGenerate.Registration.getUser("active");
        var incorrectPassword = DataGenerate.getPassword();
        $("[data-test-id=\"login\"] input").setValue(registeredUser.getLogin());
        $("[data-test-id=\"password\"] input").setValue(incorrectPassword);
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"))
                .shouldBe((Condition.visible));
    }

}
