package co.com.udea.reservehub.reservehub.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import io.restassured.response.Response;

import static net.serenitybdd.rest.SerenityRest.lastResponse;

public class ExtractToken implements Interaction {

    private final String tokenKey;

    public ExtractToken(String tokenKey) {
        this.tokenKey = tokenKey;
    }

    public static ExtractToken andSaveAs(String tokenKey) {
        return Tasks.instrumented(ExtractToken.class, tokenKey);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        Response response = lastResponse();
        String token = response.jsonPath().getString("token");
        actor.remember(tokenKey, token);
    }
}