Feature: Provider registration in ReserveHub

  Scenario: Register a provider successfully using an admin-generated code
    Given the admin is logged in with valid credentials
    When the admin generates a provider code for registration
    And the provider completes the registration with valid data
    Then the provider registration status should be 200
    And the response contains the registered provider email
    And the registered provider has an assigned id
