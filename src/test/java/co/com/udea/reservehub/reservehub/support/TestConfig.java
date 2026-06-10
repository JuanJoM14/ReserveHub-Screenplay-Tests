package co.com.udea.reservehub.reservehub.support;

import net.serenitybdd.model.environment.ConfiguredEnvironment;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;

public final class TestConfig {

    private static final String REST_API_BASE_URL = "restapi.baseurl";
    private static final String DEFAULT_BASE_URL = "http://localhost:8080";

    private TestConfig() {
    }

    public static String restApiBaseUrl() {
        return EnvironmentSpecificConfiguration
                .from(ConfiguredEnvironment.getEnvironmentVariables())
                .getOptionalProperty(REST_API_BASE_URL)
                .orElse(DEFAULT_BASE_URL);
    }
}
