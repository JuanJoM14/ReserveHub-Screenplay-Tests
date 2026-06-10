package co.com.udea.reservehub.reservehub.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;

import static net.serenitybdd.rest.SerenityRest.lastResponse;

public class ExtractAndSave implements Interaction {

    private final String jsonPath;
    private final String memoryKey;

    public ExtractAndSave(String jsonPath, String memoryKey) {
        this.jsonPath = jsonPath;
        this.memoryKey = memoryKey;
    }

    public static ExtractAndSaveBuilder field(String jsonPath) {
        return new ExtractAndSaveBuilder(jsonPath);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String value = lastResponse().jsonPath().getString(jsonPath);
        actor.remember(memoryKey, value);
    }

    public static class ExtractAndSaveBuilder {
        private final String jsonPath;

        public ExtractAndSaveBuilder(String jsonPath) {
            this.jsonPath = jsonPath;
        }

        public Interaction as(String memoryKey) {
            return Tasks.instrumented(ExtractAndSave.class, jsonPath, memoryKey);
        }
    }
}
