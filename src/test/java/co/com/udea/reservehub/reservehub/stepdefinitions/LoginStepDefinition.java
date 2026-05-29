package co.com.udea.reservehub.reservehub.stepdefinitions;

import co.com.udea.reservehub.reservehub.models.ClienteData;
import co.com.udea.reservehub.reservehub.models.LoginData;
import co.com.udea.reservehub.reservehub.tasks.LoginCliente;
import co.com.udea.reservehub.reservehub.tasks.RegisterCliente;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;

public class LoginStepDefinition {

    private static final String BASE_URL = "http://localhost:8080";
    private ClienteData clienteData;
    private LoginData loginData;

    private void prepararActorCliente() {
        OnStage.theActorCalled("cliente").whoCan(CallAnApi.at(BASE_URL));
    }

    @Given("que existe un cliente registrado")
    public void queExisteUnClienteRegistrado() {
        prepararActorCliente();
        clienteData = ClienteData.valid();
        loginData = LoginData.fromClienteData(clienteData);
        OnStage.theActorInTheSpotlight().attemptsTo(RegisterCliente.withData(clienteData));
    }

    @When("envia credenciales validas de cliente")
    public void enviaCredencialesValidasDeCliente() {
        OnStage.theActorInTheSpotlight().attemptsTo(LoginCliente.withCredentials(loginData));
    }

    @When("envia credenciales de login con {string} y {string}")
    public void enviaCredencialesDeLoginConY(String email, String password) {
        String resolvedEmail = "cliente_registrado".equals(email) ? clienteData.getEmail() : email;
        LoginData loginInvalido = new LoginData(resolvedEmail, password);
        OnStage.theActorInTheSpotlight().attemptsTo(LoginCliente.withCredentials(loginInvalido));
    }
}
