package co.com.udea.reservehub.reservehub.questions;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class ResponseJsonField implements Question<String> {

    private final String field;

    public ResponseJsonField(String field) {
        this.field = field;
    }

    public static ResponseJsonField called(String field) {
        return new ResponseJsonField(field);
    }

    @Override
    public String answeredBy(Actor actor) {
        return SerenityRest.lastResponse().jsonPath().getString(field);
    }
}
