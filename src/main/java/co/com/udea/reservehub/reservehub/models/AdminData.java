package co.com.udea.reservehub.reservehub.models;

import java.util.HashMap;
import java.util.Map;

public class AdminData {

    private final String email;
    private final String password;

    public AdminData(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static AdminData valid() {
        return new AdminData("juan.admin@correo.com", "password");
    }

    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

    public Map<String, Object> asBody() {
        Map<String, Object> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);
        return body;
    }
}
