package ec.edu.ups.excepciones;

public class TelefonoInvalidoException extends Exception {
    public TelefonoInvalidoException(String mensaje) {
        super(mensaje);
    }
}