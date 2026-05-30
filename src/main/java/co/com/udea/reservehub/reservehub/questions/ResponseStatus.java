package co.com.udea.reservehub.reservehub.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import static net.serenitybdd.rest.SerenityRest.lastResponse;

public class ResponseStatus implements Question<Integer> {

    public static ResponseStatus ofLastResponse() {
        return new ResponseStatus();
    }

    @Override
    public Integer answeredBy(Actor actor) {
        return lastResponse().statusCode();
    }
}