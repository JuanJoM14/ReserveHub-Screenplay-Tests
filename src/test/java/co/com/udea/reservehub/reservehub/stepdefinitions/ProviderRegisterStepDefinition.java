package co.com.udea.reservehub.reservehub.stepdefinitions;

import co.com.udea.reservehub.reservehub.models.LoginData;
import co.com.udea.reservehub.reservehub.models.ProviderRegisterData;
import co.com.udea.reservehub.reservehub.questions.ResponseBody;
import co.com.udea.reservehub.reservehub.questions.ResponseStatus;
import co.com.udea.reservehub.reservehub.support.TestConfig;
import co.com.udea.reservehub.reservehub.tasks.GenerateProviderCode;
import co.com.udea.reservehub.reservehub.tasks.LoginAdmin;
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

    private ProviderRegisterData providerData;

    @Given("que el administrador inicio sesion con credenciales validas")
    public void theAdminIsLoggedInWithValidCredentials() {
        OnStage.theActorCalled("Admin").whoCan(CallAnApi.at(TestConfig.restApiBaseUrl()));
        OnStage.theActorInTheSpotlight().attemptsTo(
                LoginAdmin.withCredentials(LoginData.of("juan.admin@correo.com", "password"))
        );
    }

    @When("el administrador genera un codigo de proveedor para el registro")
    public void theAdminGeneratesAProviderCodeForRegistration() {
        OnStage.theActorCalled("Admin").attemptsTo(
                GenerateProviderCode.now()
        );
    }

    @When("el proveedor completa el registro con datos validos")
    public void theProviderCompletesTheRegistrationWithValidData() {
        String providerCode = OnStage.theActorCalled("Admin").recall(PROVIDER_CODE);
        providerData = ProviderRegisterData.validWithCode(providerCode);

        OnStage.theActorCalled("Provider").whoCan(CallAnApi.at(TestConfig.restApiBaseUrl()));
        OnStage.theActorCalled("Provider").attemptsTo(
                RegisterProvider.with(providerData)
        );
    }

    @Then("el estado del registro del proveedor debe ser {int}")
    public void theProviderRegistrationStatusShouldBe(Integer expectedStatus) {
        OnStage.theActorCalled("Provider").should(
                seeThat(ResponseStatus.ofLastResponse(), equalTo(expectedStatus))
        );
    }

    @Then("la respuesta contiene el correo del proveedor registrado")
    public void theResponseContainsTheRegisteredProviderEmail() {
        OnStage.theActorCalled("Provider").should(
                seeThat(ResponseBody.string("email"), equalTo(providerData.getEmail()))
        );
    }

    @Then("el proveedor registrado tiene un id asignado")
    public void theRegisteredProviderHasAnAssignedId() {
        OnStage.theActorCalled("Provider").should(
                seeThat(ResponseBody.string("id"), notNullValue())
        );
    }
}
