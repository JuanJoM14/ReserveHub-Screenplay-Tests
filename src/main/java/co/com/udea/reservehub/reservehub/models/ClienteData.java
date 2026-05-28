package co.com.udea.reservehub.reservehub.models;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClienteData {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final String phone;

    public ClienteData(String firstName, String lastName, String email, String password, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public static ClienteData valid() {
        return new ClienteData(
                "Juan",
                "Tester",
                "cliente." + UUID.randomUUID() + "@test.com",
                "Password1!",
                "3001234567"
        );
    }

    public Map<String, Object> asBody() {
        Map<String, Object> body = new HashMap<>();
        body.put("firstName", firstName);
        body.put("lastName", lastName);
        body.put("email", email);
        body.put("password", password);
        body.put("phone", phone);
        return body;
    }
}
