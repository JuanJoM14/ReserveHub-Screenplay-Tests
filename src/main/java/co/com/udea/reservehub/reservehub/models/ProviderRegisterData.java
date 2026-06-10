package co.com.udea.reservehub.reservehub.models;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProviderRegisterData {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final String phone;
    private final String providerCode;
    private final String serviceType;
    private final String serviceDescription;

    private ProviderRegisterData(Builder builder) {
        this.firstName          = builder.firstName;
        this.lastName           = builder.lastName;
        this.email              = builder.email;
        this.password           = builder.password;
        this.phone              = builder.phone;
        this.providerCode       = builder.providerCode;
        this.serviceType        = builder.serviceType;
        this.serviceDescription = builder.serviceDescription;
    }

    public Map<String, Object> asBody() {
        Map<String, Object> body = new HashMap<>();
        body.put("firstName",          firstName);
        body.put("lastName",           lastName);
        body.put("email",              email);
        body.put("password",           password);
        body.put("phone",              phone);
        body.put("providerCode",       providerCode);
        body.put("serviceType",        serviceType);
        body.put("serviceDescription", serviceDescription);
        return body;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static ProviderRegisterData validWithCode(String providerCode) {
        return ProviderRegisterData.builder()
                .firstName("Carlos")
                .lastName("Ramirez")
                .email(uniqueEmail())
                .password("Secure@123")
                .phone("3001234567")
                .providerCode(providerCode)
                .serviceType("SALON")
                .serviceDescription("Servicio de salon de fiestas")
                .build();
    }

    public String getEmail() {
        return email;
    }

    private static String uniqueEmail() {
        return "pruebasp+" + UUID.randomUUID().toString().substring(0, 8) + "@proveedor.com";
    }

    public static class Builder {
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private String phone;
        private String providerCode;
        private String serviceType;
        private String serviceDescription;

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder providerCode(String providerCode) {
            this.providerCode = providerCode;
            return this;
        }

        public Builder serviceType(String serviceType) {
            this.serviceType = serviceType;
            return this;
        }

        public Builder serviceDescription(String serviceDescription) {
            this.serviceDescription = serviceDescription;
            return this;
        }

        public ProviderRegisterData build() {
            return new ProviderRegisterData(this);
        }
    }
}
