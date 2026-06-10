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

    @Given("que una persona desea registrarse como cliente")
    public void queUnaPersonaDeseaRegistrarseComoCliente() {
        prepararActorCliente();
        clienteData = ClienteData.valid();
    }

    @When("completa el registro con su nombre, apellido, correo, contrasena y telefono correctos")
    public void completaElRegistroConSuNombreApellidoCorreoContrasenaYTelefonoCorrectos() {
        OnStage.theActorInTheSpotlight().attemptsTo(RegisterCliente.withData(clienteData));
    }

    @Given("que ya existe una cuenta de cliente con ese correo")
    public void queYaExisteUnaCuentaDeClienteConEseCorreo() {
        prepararActorCliente();
        clienteData = ClienteData.valid();
        OnStage.theActorInTheSpotlight().attemptsTo(RegisterCliente.withData(clienteData));
    }

    @When("intenta registrarse usando el mismo correo")
    public void intentaRegistrarseUsandoElMismoCorreo() {
        ClienteData clienteDuplicado = new ClienteData(
                "Juan",
                "Duplicado",
                clienteData.getEmail(),
                "Password1!",
                "3007654321"
        );
        OnStage.theActorInTheSpotlight().attemptsTo(RegisterCliente.withData(clienteDuplicado));
    }

    @When("intenta registrarse con los datos {string}, {string}, {string}, {string} y {string}")
    public void intentaRegistrarseConLosDatos(String firstName, String lastName, String email, String password, String phone) {
        prepararActorCliente();
        ClienteData clienteInvalido = new ClienteData(firstName, lastName, email, password, phone);
        OnStage.theActorInTheSpotlight().attemptsTo(RegisterCliente.withData(clienteInvalido));
    }
}
