Feature: Acceso de cliente en ReserveHub

  @registro @happy @registro_exitoso
  Scenario: Registro exitoso de cliente
    Given que una persona desea registrarse como cliente
    When completa el registro con su nombre, apellido, correo, contrasena y telefono correctos
    Then la cuenta del cliente queda registrada exitosamente
    And el cliente queda identificado como cliente de ReserveHub

  @login @happy @login_exitoso
  Scenario: Inicio de sesion exitoso de cliente
    Given que existe un cliente registrado
    When ingresa con el correo y la contrasena registrados
    Then el cliente puede entrar a su cuenta exitosamente
    And su sesion queda iniciada correctamente
    And el cliente mantiene su perfil de cliente

  @login @negative @login_datos_incorrectos
  Scenario Outline: Inicio de sesion fallido por datos incorrectos
    Given que existe un cliente registrado
    When intenta ingresar con los datos "<correo>" y "<contrasena>"
    Then el ingreso a la cuenta es rechazado
    And se informa el error "Correo o contrasena incorrectos"

    Examples:
      | correo             | contrasena  |
      | cliente_registrado | WrongPass1! |
      | noexiste@test.com  | Password1!  |

  @registro @negative @registro_correo_duplicado
  Scenario: Registro fallido por correo duplicado
    Given que ya existe una cuenta de cliente con ese correo
    When intenta registrarse usando el mismo correo
    Then el registro es rechazado
    And se informa el error "Este correo ya esta registrado"

  @registro @negative @registro_datos_incorrectos
  Scenario Outline: Registro fallido con datos incorrectos
    When intenta registrarse con los datos "<nombre>", "<apellido>", "<correo>", "<contrasena>" y "<telefono>"
    Then el registro es rechazado
    And se informa el error "<error>"

    Examples:
      | nombre | apellido | correo           | contrasena  | telefono   | error                                                            |
      |        | Tester   | user1@test.com   | Password1!  | 3001234567 | El nombre es obligatorio                                         |
      | Juan   | Tester   | correo-invalido  | Password1!  | 3001234567 | El formato del correo no es valido                               |
      | Juan   | Tester   | user3@test.com   | password123 | 3001234567 | La contrasena debe incluir mayuscula, numero y caracter especial |
      | Juan   |          | user4@test.com   | Password1!  | 3001234567 | El apellido es obligatorio                                       |
