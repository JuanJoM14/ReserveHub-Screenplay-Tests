package co.com.udea.reservehub.reservehub.models;

import java.util.HashMap;
import java.util.Map;

public class LoginWithCredentials {

    private final String email;
    private final String password;

    public LoginWithCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static LoginWithCredentials of(String email, String password) {
        return new LoginWithCredentials(email, password);
    }

    public Map<String, Object> asBody() {
        Map<String, Object> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);
        return body;
    }
}
