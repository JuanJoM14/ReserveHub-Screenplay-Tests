package co.com.udea.reservehub.reservehub.stepdefinitions;

import co.com.udea.reservehub.reservehub.models.LoginWithCredentials;
import co.com.udea.reservehub.reservehub.models.ProviderRegisterData;
import co.com.udea.reservehub.reservehub.questions.ResponseBody;
import co.com.udea.reservehub.reservehub.questions.ResponseStatus;
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

    private static final String BASE_URL =
            System.getProperty("restapi.baseurl", "http://localhost:8080");

    // -------------------------------------------------------------------------
    // Admin: login y generación del código
    // -------------------------------------------------------------------------

    @Given("the admin is logged in with {string} and {string}")
    public void the_admin_is_logged_in_with_and(String email, String password) {
        OnStage.theActorCalled("Admin").whoCan(CallAnApi.at(BASE_URL));
        OnStage.theActorInTheSpotlight().attemptsTo(
                LoginAdmin.withCredentials(LoginWithCredentials.of(email, password))
        );
    }

    @When("the admin generates a provider code for registration")
    public void the_admin_generates_a_provider_code_for_registration() {
        OnStage.theActorCalled("Admin").attemptsTo(
                GenerateProviderCode.now()
        );
    }

    // -------------------------------------------------------------------------
    // Proveedor: registro usando el código generado por el admin
    // -------------------------------------------------------------------------

    @When("the provider registers with first name {string}, last name {string}, email {string}, password {string}, phone {string}, service type {string} and service description {string}")
    public void the_provider_registers(
            String firstName,
            String lastName,
            String email,
            String password,
            String phone,
            String serviceType,
            String serviceDescription
    ) {
        // El código fue guardado en la memoria del actor Admin
        String providerCode = OnStage.theActorCalled("Admin").recall(PROVIDER_CODE);

        OnStage.theActorCalled("Provider").whoCan(CallAnApi.at(BASE_URL));

        ProviderRegisterData providerData = ProviderRegisterData.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .phone(phone)
                .providerCode(providerCode)
                .serviceType(serviceType)
                .serviceDescription(serviceDescription)
                .build();

        OnStage.theActorCalled("Provider").attemptsTo(
                RegisterProvider.with(providerData)
        );
    }

    // -------------------------------------------------------------------------
    // Verificaciones
    // -------------------------------------------------------------------------

    @Then("the provider registration status should be {int}")
    public void the_provider_registration_status_should_be(Integer expectedStatus) {
        OnStage.theActorCalled("Provider").should(
                seeThat(ResponseStatus.ofLastResponse(), equalTo(expectedStatus))
        );
    }

    @Then("the response contains the registered provider email {string}")
    public void the_response_contains_the_registered_provider_email(String expectedEmail) {
        OnStage.theActorCalled("Provider").should(
                seeThat(ResponseBody.string("email"), equalTo(expectedEmail))
        );
    }

    @Then("the registered provider has an assigned id")
    public void the_registered_provider_has_an_assigned_id() {
        OnStage.theActorCalled("Provider").should(
                seeThat(ResponseBody.string("id"), notNullValue())
        );
    }
}