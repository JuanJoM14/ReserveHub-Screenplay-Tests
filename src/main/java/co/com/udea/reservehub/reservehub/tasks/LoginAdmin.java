package co.com.udea.reservehub.reservehub.tasks;

import co.com.udea.reservehub.reservehub.interactions.ExtractToken;
import co.com.udea.reservehub.reservehub.interactions.PostLogin;
import co.com.udea.reservehub.reservehub.models.LoginWithCredentials;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

import static co.com.udea.reservehub.reservehub.utils.MemoryKeys.AUTH_TOKEN;

public class LoginAdmin implements Task {

    private final LoginWithCredentials credentials;

    public LoginAdmin(LoginWithCredentials credentials) {
        this.credentials = credentials;
    }

    public static LoginAdmin withCredentials(LoginWithCredentials credentials) {
        return Tasks.instrumented(LoginAdmin.class, credentials);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                PostLogin.withBody(credentials.asBody()),
                ExtractToken.andSaveAs(AUTH_TOKEN)
        );
    }
}