package co.com.udea.reservehub.reservehub.models;

import java.util.HashMap;
import java.util.Map;

public class LoginData {

    private final String email;
    private final String password;

    public LoginData(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static LoginData fromClienteData(ClienteData clienteData) {
        return new LoginData(clienteData.getEmail(), clienteData.getPassword());
    }

    public Map<String, Object> asBody() {
        Map<String, Object> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);
        return body;
    }
}
