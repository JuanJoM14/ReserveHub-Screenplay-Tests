package co.com.udea.reservehub.reservehub.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Post;

import java.util.Map;

public class PostRegisterProvider implements Interaction {

    private final Map<String, Object> body;

    public PostRegisterProvider(Map<String, Object> body) {
        this.body = body;
    }

    public static PostRegisterProvider withBody(Map<String, Object> body) {
        return Tasks.instrumented(PostRegisterProvider.class, body);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Post.to("/api/users/register/proveedor")
                        .with(request -> request
                                .contentType("application/json")
                                .body(body))
        );
    }
}