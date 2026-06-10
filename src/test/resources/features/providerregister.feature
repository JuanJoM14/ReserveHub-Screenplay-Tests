Feature: Registro de proveedor en ReserveHub

  Scenario: Registrar un proveedor exitosamente usando un codigo generado por el administrador
    Given que el administrador inicio sesion con credenciales validas
    When el administrador genera un codigo de proveedor para el registro
    And el proveedor completa el registro con datos validos
    Then el estado del registro del proveedor debe ser 200
    And la respuesta contiene el correo del proveedor registrado
    And el proveedor registrado tiene un id asignado
