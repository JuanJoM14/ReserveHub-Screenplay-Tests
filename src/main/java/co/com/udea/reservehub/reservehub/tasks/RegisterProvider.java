package co.com.udea.reservehub.reservehub.tasks;

import co.com.udea.reservehub.reservehub.interactions.PostRegisterProvider;
import co.com.udea.reservehub.reservehub.models.ProviderRegisterData;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class RegisterProvider implements Task {

    private final ProviderRegisterData providerData;

    public RegisterProvider(ProviderRegisterData providerData) {
        this.providerData = providerData;
    }

    public static RegisterProvider with(ProviderRegisterData providerData) {
        return Tasks.instrumented(RegisterProvider.class, providerData);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                PostRegisterProvider.withBody(providerData.asBody())
        );
    }
}