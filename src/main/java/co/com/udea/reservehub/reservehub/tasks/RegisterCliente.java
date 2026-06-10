package co.com.udea.reservehub.reservehub.tasks;

import co.com.udea.reservehub.reservehub.models.ClienteData;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Post;

public class RegisterCliente implements Task {

    private final ClienteData clienteData;

    public RegisterCliente(ClienteData clienteData) {
        this.clienteData = clienteData;
    }

    public static RegisterCliente withData(ClienteData clienteData) {
        return Tasks.instrumented(RegisterCliente.class, clienteData);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Post.to("/api/users/register/cliente")
                        .with(request -> request
                                .contentType("application/json")
                                .body(clienteData.asBody()))
        );
    }
}
