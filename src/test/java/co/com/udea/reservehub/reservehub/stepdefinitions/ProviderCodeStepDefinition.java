package co.com.udea.reservehub.reservehub.stepdefinitions;

import co.com.udea.reservehub.reservehub.models.LoginData;
import co.com.udea.reservehub.reservehub.questions.ResponseBody;
import co.com.udea.reservehub.reservehub.questions.ResponseStatus;
import co.com.udea.reservehub.reservehub.support.TestConfig;
import co.com.udea.reservehub.reservehub.tasks.GenerateProviderCode;
import co.com.udea.reservehub.reservehub.tasks.LoginAdmin;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.*;

public class ProviderCodeStepDefinition {

    @When("el administrador inicia sesion con {string} y {string}")
    public void the_admin_logs_in_with_and(String email, String password) {
        OnStage.theActorCalled("Admin").whoCan(CallAnApi.at(TestConfig.restApiBaseUrl()));
        OnStage.theActorInTheSpotlight().attemptsTo(
                LoginAdmin.withCredentials(LoginData.of(email, password))
        );
    }

    @When("el administrador genera un codigo de proveedor")
    public void the_admin_generates_a_provider_code() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                GenerateProviderCode.now()
        );
    }

    @Then("el estado de la respuesta debe ser {int}")
    public void the_response_status_should_be(Integer expectedStatus) {
        OnStage.theActorInTheSpotlight().should(
                seeThat(ResponseStatus.ofLastResponse(), equalTo(expectedStatus))
        );
    }

    @Then("la respuesta contiene un codigo que empieza por {string}")
    public void the_response_contains_a_code_value_starting_with(String prefix) {
        OnStage.theActorInTheSpotlight().should(
                seeThat(ResponseBody.string("code"), startsWith(prefix))
        );
    }

    @Then("la respuesta indica que el codigo esta activo y no usado")
    public void the_response_indicates_the_code_is_active_and_not_used() {
        OnStage.theActorInTheSpotlight().should(
                seeThat(ResponseBody.bool("active"), is(true)),
                seeThat(ResponseBody.bool("used"), is(false))
        );
    }
}
