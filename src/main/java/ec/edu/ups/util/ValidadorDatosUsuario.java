package ec.edu.ups.util;

import ec.edu.ups.excepciones.CedulaInvalidaException;
import ec.edu.ups.excepciones.CorreoInvalidoException;
import ec.edu.ups.excepciones.ContrasenaInvalidaException;
import ec.edu.ups.excepciones.TelefonoInvalidoException;

public class ValidadorDatosUsuario {

    /**
     * Valida un número de teléfono.
     * @param telefono El teléfono a validar.
     * @throws TelefonoInvalidoException si no contiene exactamente 10 dígitos numéricos.
     */
    public static void validarTelefono(String telefono) throws TelefonoInvalidoException {
        if (!telefono.matches("\\d{10}")) {
            throw new TelefonoInvalidoException("Número de teléfono inválido. Debe contener exactamente 10 dígitos.");
        }
    }

    /**
     * Valida una dirección de correo electrónico.
     * @param correo El correo a validar.
     * @throws CorreoInvalidoException si no contiene el carácter '@'.
     */
    public static void validarCorreo(String correo) throws CorreoInvalidoException {
        if (correo == null || !correo.contains("@")) {
            throw new CorreoInvalidoException("Correo inválido. Debe contener el símbolo '@'.");
        }
    }

    /**
     * Valida una contraseña según los requisitos de seguridad.
     * @param contrasena La contraseña a validar.
     * @throws ContrasenaInvalidaException si no cumple con los criterios de seguridad.
     */
    public static void validarContrasena(String contrasena) throws ContrasenaInvalidaException {
        if (contrasena.length() < 6) {
            throw new ContrasenaInvalidaException("La contraseña debe tener un mínimo de 6 caracteres.");
        }
        if (!contrasena.matches(".*[A-Z].*")) {
            throw new ContrasenaInvalidaException("La contraseña debe contener al menos una letra mayúscula.");
        }
        if (!contrasena.matches(".*[a-z].*")) {
            throw new ContrasenaInvalidaException("La contraseña debe contener al menos una letra minúscula.");
        }
        if (!contrasena.matches(".*[@_-].*")) {
            throw new ContrasenaInvalidaException("La contraseña debe contener al menos uno de los siguientes caracteres: @ _ -");
        }
    }

    /**
     * Valida una cédula ecuatoriana.
     * @param cedula El número de cédula a validar.
     * @throws CedulaInvalidaException si la cédula no es válida.
     */
    public static void validarCedula(String cedula) throws CedulaInvalidaException {
        if (cedula == null || cedula.length() != 10) {
            throw new CedulaInvalidaException("La cédula debe tener 10 dígitos.");
        }
        if (!cedula.matches("\\d+")) {
            throw new CedulaInvalidaException("La cédula solo debe contener números.");
        }

        int provincia = Integer.parseInt(cedula.substring(0, 2));
        if (provincia <= 0 || provincia > 24) {
            throw new CedulaInvalidaException("El código de provincia de la cédula es inválido.");
        }

        int[] digitos = new int[10];
        for (int i = 0; i < 10; i++) {
            digitos[i] = Integer.parseInt(String.valueOf(cedula.charAt(i)));
        }

        int suma = 0;
        for (int i = 0; i < 9; i++) {
            int valor = digitos[i];
            if (i % 2 == 0) { // Coeficiente 2 para posiciones pares
                valor *= 2;
                if (valor > 9) {
                    valor -= 9;
                }
            }
            suma += valor;
        }

        int digitoVerificador = (suma % 10 == 0) ? 0 : 10 - (suma % 10);

        if (digitoVerificador != digitos[9]) {
            throw new CedulaInvalidaException("El número de cédula ingresado es incorrecto.");
        }
    }
}