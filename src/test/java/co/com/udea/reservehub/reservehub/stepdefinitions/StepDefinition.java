package co.com.udea.reservehub.reservehub.stepdefinitions;

import co.com.udea.reservehub.reservehub.models.ClienteData;
import co.com.udea.reservehub.reservehub.questions.ResponseJsonField;
import co.com.udea.reservehub.reservehub.questions.ResponseStatusCode;
import co.com.udea.reservehub.reservehub.tasks.RegisterCliente;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class StepDefinition {

    private static final String BASE_URL = "http://localhost:8080";
    private ClienteData clienteData;

    @Before
    public void config() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Given("que el usuario quiere registrarse como cliente")
    public void queElUsuarioQuiereRegistrarseComoCliente() {
        OnStage.theActorCalled("cliente").whoCan(CallAnApi.at(BASE_URL));
        clienteData = ClienteData.valid();
    }

    @When("envia el registro con datos validos")
    public void enviaElRegistroConDatosValidos() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                RegisterCliente.withData(clienteData)
        );
    }

    @Then("deberia recibir respuesta exitosa")
    public void deberiaRecibirRespuestaExitosa() {
        assertThat(ResponseStatusCode.value().answeredBy(OnStage.theActorInTheSpotlight()), equalTo(200));
    }

    @Then("el rol del usuario deberia ser {string}")
    public void elRolDelUsuarioDeberiaSer(String rolEsperado) {
        assertThat(
                ResponseJsonField.called("role").answeredBy(OnStage.theActorInTheSpotlight()),
                equalTo(rolEsperado)
        );
    }
}
