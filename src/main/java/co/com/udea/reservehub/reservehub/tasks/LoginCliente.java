package co.com.udea.reservehub.reservehub.tasks;

import co.com.udea.reservehub.reservehub.interactions.PostLoginCliente;
import co.com.udea.reservehub.reservehub.models.LoginData;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class LoginCliente implements Task {

    private final LoginData loginData;

    public LoginCliente(LoginData loginData) {
        this.loginData = loginData;
    }

    public static LoginCliente withCredentials(LoginData loginData) {
        return Tasks.instrumented(LoginCliente.class, loginData);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                PostLoginCliente.withBody(loginData.asBody())
        );
    }
}