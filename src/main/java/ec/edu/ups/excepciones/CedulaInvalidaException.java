package ec.edu.ups.excepciones;

public class CedulaInvalidaException extends Exception {
    public CedulaInvalidaException(String mensaje) {
        super(mensaje);
    }
}