package co.com.udea.reservehub.reservehub.stepdefinitions;

import co.com.udea.reservehub.reservehub.models.LoginWithCredentials;
import co.com.udea.reservehub.reservehub.questions.ResponseBody;
import co.com.udea.reservehub.reservehub.questions.ResponseStatus;
import co.com.udea.reservehub.reservehub.tasks.GenerateProviderCode;
import co.com.udea.reservehub.reservehub.tasks.LoginAdmin;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.*;

public class ProviderCodeStepDefinition {

    private static final String BASE_URL =
            System.getProperty("restapi.baseurl", "http://localhost:8080");

    @When("the admin logs in with {string} and {string}")
    public void the_admin_logs_in_with_and(String email, String password) {
        OnStage.theActorCalled("Admin").whoCan(CallAnApi.at(BASE_URL));
        OnStage.theActorInTheSpotlight().attemptsTo(
                LoginAdmin.withCredentials(LoginWithCredentials.of(email, password))
        );
    }

    @When("the admin generates a provider code")
    public void the_admin_generates_a_provider_code() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                GenerateProviderCode.now()
        );
    }

    @Then("the response status should be {int}")
    public void the_response_status_should_be(Integer expectedStatus) {
        OnStage.theActorInTheSpotlight().should(
                seeThat(ResponseStatus.ofLastResponse(), equalTo(expectedStatus))
        );
    }

    @Then("the response contains a code value starting with {string}")
    public void the_response_contains_a_code_value_starting_with(String prefix) {
        OnStage.theActorInTheSpotlight().should(
                seeThat(ResponseBody.string("code"), startsWith(prefix))
        );
    }

    @Then("the response indicates the code is active and not used")
    public void the_response_indicates_the_code_is_active_and_not_used() {
        OnStage.theActorInTheSpotlight().should(
                seeThat(ResponseBody.bool("active"), is(true)),
                seeThat(ResponseBody.bool("used"), is(false))
        );
    }
}