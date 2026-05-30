package co.com.udea.reservehub.reservehub.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Post;

import java.util.Map;

public class PostRegistroCliente implements Interaction {

    private final Map<String, Object> body;

    public PostRegistroCliente(Map<String, Object> body) {
        this.body = body;
    }

    public static PostRegistroCliente withBody(Map<String, Object> body) {
        return Tasks.instrumented(PostRegistroCliente.class, body);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Post.to("/api/users/register/cliente")
                        .with(request -> request
                                .contentType("application/json")
                                .body(body))
        );
    }
}