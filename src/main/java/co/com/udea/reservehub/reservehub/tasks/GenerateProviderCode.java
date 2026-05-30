package co.com.udea.reservehub.reservehub.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import static co.com.udea.reservehub.reservehub.utils.TokenKeys.AUTH_TOKEN;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class GenerateProviderCode implements Task {

    public static GenerateProviderCode now() {
        return instrumented(GenerateProviderCode.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String token = actor.recall(AUTH_TOKEN);
        actor.attemptsTo(
                Post.to("/api/provider-codes")
                        .with(request -> request
                                .header("Authorization", "Bearer " + token)
                                .contentType("application/json"))
        );
    }
}