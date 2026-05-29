Feature: Registro de cliente en ReserveHub

  @registro @happy @registro_exitoso
  Scenario: Registro exitoso de cliente
    Given que el usuario quiere registrarse como cliente
    When envia el registro con datos validos
    Then deberia recibir respuesta exitosa
    And el rol del usuario deberia ser "CLIENTE"

  @login @happy @login_exitoso
  Scenario: Login exitoso de cliente
    Given que existe un cliente registrado
    When envia credenciales validas de cliente
    Then deberia recibir respuesta exitosa
    And el login deberia retornar un token
    And el rol del usuario deberia ser "CLIENTE"

  @login @negative @login_credenciales_invalidas
  Scenario Outline: Login fallido por credenciales invalidas
    Given que existe un cliente registrado
    When envia credenciales de login con "<email>" y "<password>"
    Then deberia recibir respuesta 400
    And deberia ver el mensaje de error "Correo o contrasena incorrectos"

    Examples:
      | email              | password    |
      | cliente_registrado | WrongPass1! |
      | noexiste@test.com  | Password1!  |

  @registro @negative @registro_correo_duplicado
  Scenario: Registro fallido por correo duplicado
    Given que existe un cliente registrado con un correo determinado
    When intenta registrarse nuevamente con el mismo correo
    Then deberia recibir respuesta 400
    And deberia ver el mensaje de error "Este correo ya esta registrado"

  @registro @negative @registro_datos_invalidos
  Scenario Outline: Registro fallido con datos invalidos
    When envia registro cliente con "<firstName>" "<lastName>" "<email>" "<password>" "<phone>"
    Then deberia recibir respuesta 400
    And deberia ver el mensaje de error "<error>"

    Examples:
      | firstName | lastName | email            | password    | phone      | error                                                               |
      |           | Tester   | user1@test.com   | Password1!  | 3001234567 | El nombre es obligatorio                                            |
      | Juan      | Tester   | correo-invalido  | Password1!  | 3001234567 | El formato del correo no es válido                                 |
      | Juan      | Tester   | user2@test.com   | 123         | 3001234567 | La contraseña debe tener mínimo 8 caracteres                       |
      | Juan      | Tester   | user3@test.com   | password123 | 3001234567 | La contraseña debe incluir mayúscula, número y carácter especial   |
      | Juan      |          | user4@test.com   | Password1!  | 3001234567 | El apellido es obligatorio                                          |
