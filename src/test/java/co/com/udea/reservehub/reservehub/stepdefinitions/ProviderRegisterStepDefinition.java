package co.com.udea.reservehub.reservehub.stepdefinitions;

import co.com.udea.reservehub.reservehub.models.ProviderRegisterData;
import co.com.udea.reservehub.reservehub.questions.ResponseBody;
import co.com.udea.reservehub.reservehub.questions.ResponseStatus;
import co.com.udea.reservehub.reservehub.tasks.GenerateProviderCode;
import co.com.udea.reservehub.reservehub.tasks.Login;
import co.com.udea.reservehub.reservehub.tasks.RegisterProvider;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;

import static co.com.udea.reservehub.reservehub.utils.MemoryKeys.PROVIDER_CODE;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ProviderRegisterStepDefinition {

    private static final String BASE_URL =
            System.getProperty("restapi.baseurl", "http://localhost:8080");

    private ProviderRegisterData providerData;

    @Given("the admin is logged in with valid credentials")
    public void theAdminIsLoggedInWithValidCredentials() {
        OnStage.theActorCalled("Admin").whoCan(CallAnApi.at(BASE_URL));
        OnStage.theActorInTheSpotlight().attemptsTo(
                Login.withCredentials("juan.admin@correo.com", "password").andSaveToken()
        );
    }

    @When("the admin generates a provider code for registration")
    public void theAdminGeneratesAProviderCodeForRegistration() {
        OnStage.theActorCalled("Admin").attemptsTo(
                GenerateProviderCode.now()
        );
    }

    @When("the provider completes the registration with valid data")
    public void theProviderCompletesTheRegistrationWithValidData() {
        String providerCode = OnStage.theActorCalled("Admin").recall(PROVIDER_CODE);
        providerData = ProviderRegisterData.validWithCode(providerCode);

        OnStage.theActorCalled("Provider").whoCan(CallAnApi.at(BASE_URL));
        OnStage.theActorCalled("Provider").attemptsTo(
                RegisterProvider.with(providerData)
        );
    }

    @Then("the provider registration status should be {int}")
    public void theProviderRegistrationStatusShouldBe(Integer expectedStatus) {
        OnStage.theActorCalled("Provider").should(
                seeThat(ResponseStatus.ofLastResponse(), equalTo(expectedStatus))
        );
    }

    @Then("the response contains the registered provider email")
    public void theResponseContainsTheRegisteredProviderEmail() {
        OnStage.theActorCalled("Provider").should(
                seeThat(ResponseBody.string("email"), equalTo(providerData.getEmail()))
        );
    }

    @Then("the registered provider has an assigned id")
    public void theRegisteredProviderHasAnAssignedId() {
        OnStage.theActorCalled("Provider").should(
                seeThat(ResponseBody.string("id"), notNullValue())
        );
    }
}
