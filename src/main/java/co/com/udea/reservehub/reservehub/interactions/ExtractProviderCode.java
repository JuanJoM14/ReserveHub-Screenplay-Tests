package co.com.udea.reservehub.reservehub.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;

import static co.com.udea.reservehub.reservehub.utils.MemoryKeys.PROVIDER_CODE;
import static net.serenitybdd.rest.SerenityRest.lastResponse;

public class ExtractProviderCode implements Interaction {

    public static ExtractProviderCode fromResponse() {
        return Tasks.instrumented(ExtractProviderCode.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String code = lastResponse().jsonPath().getString("code");
        actor.remember(PROVIDER_CODE, code);
    }
}