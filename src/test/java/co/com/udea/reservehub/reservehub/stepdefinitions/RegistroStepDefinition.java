package co.com.udea.reservehub.reservehub.stepdefinitions;

import co.com.udea.reservehub.reservehub.models.ClienteData;
import co.com.udea.reservehub.reservehub.tasks.RegisterCliente;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;

public class RegistroStepDefinition {

    private static final String BASE_URL = "http://localhost:8080";
    private ClienteData clienteData;

    private void prepararActorCliente() {
        OnStage.theActorCalled("cliente").whoCan(CallAnApi.at(BASE_URL));
    }

    @Given("que el usuario quiere registrarse como cliente")
    public void queElUsuarioQuiereRegistrarseComoCliente() {
        prepararActorCliente();
        clienteData = ClienteData.valid();
    }

    @When("envia el registro con datos validos")
    public void enviaElRegistroConDatosValidos() {
        OnStage.theActorInTheSpotlight().attemptsTo(RegisterCliente.withData(clienteData));
    }

    @Given("que existe un cliente registrado con un correo determinado")
    public void queExisteUnClienteRegistradoConUnCorreoDeterminado() {
        prepararActorCliente();
        clienteData = ClienteData.valid();
        OnStage.theActorInTheSpotlight().attemptsTo(RegisterCliente.withData(clienteData));
    }

    @When("intenta registrarse nuevamente con el mismo correo")
    public void intentaRegistrarseNuevamenteConElMismoCorreo() {
        ClienteData clienteDuplicado = new ClienteData(
                "Juan",
                "Duplicado",
                clienteData.getEmail(),
                "Password1!",
                "3007654321"
        );
        OnStage.theActorInTheSpotlight().attemptsTo(RegisterCliente.withData(clienteDuplicado));
    }

    @When("envia registro cliente con {string} {string} {string} {string} {string}")
    public void enviaRegistroClienteCon(String firstName, String lastName, String email, String password, String phone) {
        prepararActorCliente();
        ClienteData clienteInvalido = new ClienteData(firstName, lastName, email, password, phone);
        OnStage.theActorInTheSpotlight().attemptsTo(RegisterCliente.withData(clienteInvalido));
    }
}
