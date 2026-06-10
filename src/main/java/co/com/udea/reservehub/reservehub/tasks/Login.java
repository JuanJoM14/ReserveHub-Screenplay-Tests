package co.com.udea.reservehub.reservehub.tasks;

import co.com.udea.reservehub.reservehub.interactions.ExtractAndSave;
import co.com.udea.reservehub.reservehub.models.LoginData;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Post;

import static co.com.udea.reservehub.reservehub.utils.MemoryKeys.AUTH_TOKEN;

public class Login implements Task {

    private final LoginData loginData;
    private boolean saveToken = false;

    public Login(LoginData loginData) {
        this.loginData = loginData;
    }

    public static Login withCredentials(String email, String password) {
        return Tasks.instrumented(Login.class, new LoginData(email, password));
    }

    public static Login withData(LoginData loginData) {
        return Tasks.instrumented(Login.class, loginData);
    }

    public Login andSaveToken() {
        this.saveToken = true;
        return this;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Post.to("/api/users/login")
                        .with(request -> request
                                .contentType("application/json")
                                .body(loginData.asBody()))
        );

        if (saveToken) {
            actor.attemptsTo(ExtractAndSave.field("token").as(AUTH_TOKEN));
        }
    }
}
