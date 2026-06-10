Feature: Gestion de codigos de proveedor en ReserveHub

  Scenario Outline: Generar un codigo de proveedor exitosamente
    When el administrador inicia sesion con "<adminUser>" y "<adminPassword>"
    And el administrador genera un codigo de proveedor
    Then el estado de la respuesta debe ser 200
    And la respuesta contiene un codigo que empieza por "PROV-"
    And la respuesta indica que el codigo esta activo y no usado

    Examples:
      | adminUser              | adminPassword |
      | juan.admin@correo.com  | password      |
