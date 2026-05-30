package co.com.udea.reservehub.reservehub.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import static net.serenitybdd.rest.SerenityRest.lastResponse;

public class ResponseBody<T> implements Question<T> {

    private final String jsonPath;
    private final Class<T> type;

    public ResponseBody(String jsonPath, Class<T> type) {
        this.jsonPath = jsonPath;
        this.type = type;
    }

    public static ResponseBody<String> string(String jsonPath) {
        return new ResponseBody<>(jsonPath, String.class);
    }

    public static ResponseBody<Boolean> bool(String jsonPath) {
        return new ResponseBody<>(jsonPath, Boolean.class);
    }

    @Override
    public T answeredBy(Actor actor) {
        return lastResponse().jsonPath().getObject(jsonPath, type);
    }
}