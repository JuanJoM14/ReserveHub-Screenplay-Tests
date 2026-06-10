package co.com.udea.reservehub.reservehub.stepdefinitions;

import co.com.udea.reservehub.reservehub.questions.ResponseJsonField;
import co.com.udea.reservehub.reservehub.questions.ResponseStatus;
import io.cucumber.java.en.Then;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.actors.OnStage;

import java.text.Normalizer;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;

public class CommonResponseStepDefinition {

    @Then("la cuenta del cliente queda registrada exitosamente")
    @Then("el cliente puede entrar a su cuenta exitosamente")
    public void laOperacionExitosaDelCliente() {
        assertThat(ResponseStatus.ofLastResponse().answeredBy(OnStage.theActorInTheSpotlight()), equalTo(200));
    }

    @Then("el ingreso a la cuenta es rechazado")
    @Then("el registro es rechazado")
    public void laOperacionEsRechazada() {
        assertThat(
                ResponseStatus.ofLastResponse().answeredBy(OnStage.theActorInTheSpotlight()),
                equalTo(400)
        );
    }

    @Then("el cliente queda identificado como cliente de ReserveHub")
    @Then("el cliente mantiene su perfil de cliente")
    public void elClienteMantieneSuPerfilDeCliente() {
        assertThat(
                ResponseJsonField.called("role").answeredBy(OnStage.theActorInTheSpotlight()),
                equalTo("CLIENTE")
        );
    }

    @Then("su sesion queda iniciada correctamente")
    public void suSesionQuedaIniciadaCorrectamente() {
        assertThat(
                ResponseJsonField.called("token").answeredBy(OnStage.theActorInTheSpotlight()),
                not(isEmptyOrNullString())
        );
    }

    @Then("se informa el error {string}")
    public void seInformaElError(String mensajeEsperado) {
        validarMensajeDeError(mensajeEsperado);
    }

    @Then("deberia informar que las credenciales son incorrectas")
    public void deberiaInformarQueLasCredencialesSonIncorrectas() {
        validarMensajeDeError("Correo o contrasena incorrectos");
    }

    private void validarMensajeDeError(String mensajeEsperado) {
        Map<String, String> responseBody = SerenityRest.lastResponse().jsonPath().getMap("$");
        String errorGeneral = responseBody.get("error");
        String esperadoNormalizado = normalizar(mensajeEsperado);

        if (errorGeneral != null) {
            assertThat(normalizar(errorGeneral), equalTo(esperadoNormalizado));
            return;
        }

        String todosLosMensajes = normalizar(String.join(" | ", responseBody.values()));
        assertThat(todosLosMensajes, containsString(esperadoNormalizado));
    }

    private String normalizar(String texto) {
        String sinTildes = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        return sinTildes.toLowerCase().trim();
    }
}
