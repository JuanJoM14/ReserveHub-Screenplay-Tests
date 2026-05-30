Feature: Provider codes management in ReserveHub

  Scenario Outline: Generate a provider code successfully
    When the admin logs in with "<adminUser>" and "<adminPassword>"
    And the admin generates a provider code
    Then the response status should be 200
    And the response contains a code value starting with "PROV-"
    And the response indicates the code is active and not used

    Examples:
      | adminUser              | adminPassword |
      | juan.admin@correo.com  | password      |