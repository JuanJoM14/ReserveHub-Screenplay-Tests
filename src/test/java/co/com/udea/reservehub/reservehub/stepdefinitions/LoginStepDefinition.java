package co.com.udea.reservehub.reservehub.stepdefinitions;

import co.com.udea.reservehub.reservehub.models.ClienteData;
import co.com.udea.reservehub.reservehub.models.LoginData;
import co.com.udea.reservehub.reservehub.tasks.Login;
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

    @When("ingresa con el correo y la contrasena registrados")
    public void ingresaConElCorreoYLaContrasenaRegistrados() {
        OnStage.theActorInTheSpotlight().attemptsTo(Login.withData(loginData));
    }

    @When("intenta ingresar con los datos {string} y {string}")
    public void intentaIngresarConLosDatos(String email, String password) {
        String resolvedEmail = "cliente_registrado".equals(email) ? clienteData.getEmail() : email;
        LoginData loginInvalido = new LoginData(resolvedEmail, password);
        OnStage.theActorInTheSpotlight().attemptsTo(Login.withData(loginInvalido));
    }
}
