package co.com.udea.reservehub.reservehub.tasks;

import co.com.udea.reservehub.reservehub.interactions.PostRegistroCliente;
import co.com.udea.reservehub.reservehub.models.ClienteData;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

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
                PostRegistroCliente.withBody(clienteData.asBody())
        );
    }
}
