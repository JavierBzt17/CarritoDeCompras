package ec.edu.ups.excepciones;

public class ContrasenaInvalidaException extends Exception {
    public ContrasenaInvalidaException(String mensaje) {
        super(mensaje);
    }
}