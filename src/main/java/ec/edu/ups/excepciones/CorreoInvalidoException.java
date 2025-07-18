package ec.edu.ups.excepciones;

public class CorreoInvalidoException extends Exception {
    public CorreoInvalidoException(String mensaje) {
        super(mensaje);
    }
}