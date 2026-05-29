package co.com.udea.reservehub.reservehub.stepdefinitions;

import co.com.udea.reservehub.reservehub.questions.ResponseJsonField;
import co.com.udea.reservehub.reservehub.questions.ResponseStatusCode;
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

    @Then("deberia recibir respuesta exitosa")
    public void deberiaRecibirRespuestaExitosa() {
        assertThat(ResponseStatusCode.value().answeredBy(OnStage.theActorInTheSpotlight()), equalTo(200));
    }

    @Then("deberia recibir respuesta {int}")
    public void deberiaRecibirRespuesta(Integer statusCodeEsperado) {
        assertThat(
                ResponseStatusCode.value().answeredBy(OnStage.theActorInTheSpotlight()),
                equalTo(statusCodeEsperado)
        );
    }

    @Then("el rol del usuario deberia ser {string}")
    public void elRolDelUsuarioDeberiaSer(String rolEsperado) {
        assertThat(
                ResponseJsonField.called("role").answeredBy(OnStage.theActorInTheSpotlight()),
                equalTo(rolEsperado)
        );
    }

    @Then("el login deberia retornar un token")
    public void elLoginDeberiaRetornarUnToken() {
        assertThat(
                ResponseJsonField.called("token").answeredBy(OnStage.theActorInTheSpotlight()),
                not(isEmptyOrNullString())
        );
    }

    @Then("deberia ver el mensaje de error {string}")
    public void deberiaVerElMensajeDeError(String mensajeEsperado) {
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
