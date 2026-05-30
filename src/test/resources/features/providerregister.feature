Feature: Provider registration in ReserveHub

  Scenario Outline: Register a provider successfully using an admin-generated code
    Given the admin is logged in with "<adminEmail>" and "<adminPassword>"
    When the admin generates a provider code for registration
    And the provider registers with first name "<firstName>", last name "<lastName>", email "<providerEmail>", password "<password>", phone "<phone>", service type "<serviceType>" and service description "<serviceDescription>"
    Then the provider registration status should be 200
    And the response contains the registered provider email "<providerEmail>"
    And the registered provider has an assigned id

    Examples:
      | adminEmail             | adminPassword | firstName | lastName | providerEmail            | password    | phone      | serviceType | serviceDescription          |
      | juan.admin@correo.com  | password      | Carlos    | Ramirez  | pruebasp@proveedor.com    | Secure@123  | 3001234567 | SALON       | Servicio de salon de fiestas |
