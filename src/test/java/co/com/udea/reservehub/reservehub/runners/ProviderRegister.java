package co.com.udea.reservehub.reservehub.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features/providerregister.feature",
        glue = "co.com.udea.reservehub.reservehub.stepdefinitions",
        plugin = {"pretty"}
)

public class ProviderRegister {
}
