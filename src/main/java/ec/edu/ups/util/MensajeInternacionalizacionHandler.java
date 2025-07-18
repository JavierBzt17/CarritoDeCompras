package ec.edu.ups.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * La clase **MensajeInternacionalizacionHandler** gestiona la internacionalización de mensajes
 * cargando recursos de un archivo de propiedades (`mensajes.properties`) basado en la configuración regional (idioma y país) especificada.
 * Permite obtener mensajes traducidos y cambiar el idioma dinámicamente.
 */
public class MensajeInternacionalizacionHandler {

    private ResourceBundle bundle;
    private Locale locale;

    /**
     * Constructor para la clase MensajeInternacionalizacionHandler.
     * Inicializa el manejador de internacionalización con el idioma y país especificados,
     * cargando el `ResourceBundle` correspondiente.
     *
     * @param lenguaje El código de idioma (ej. "es", "en").
     * @param pais El código de país (ej. "EC", "US").
     */
    public MensajeInternacionalizacionHandler(String lenguaje, String pais) {
        this.locale = new Locale(lenguaje, pais);
        this.bundle = ResourceBundle.getBundle("mensajes", locale);
    }

    /**
     * Obtiene el mensaje internacionalizado asociado a una clave dada.
     * Busca la clave en el `ResourceBundle` cargado actualmente.
     *
     * @param key La clave del mensaje a buscar (ej. "saludo.bienvenida").
     * @return El mensaje traducido asociado a la clave.
     */
    public String get(String key) {
        return bundle.getString(key);
    }

    /**
     * Cambia el idioma y país para la internacionalización.
     * Esto recarga el `ResourceBundle` con la nueva configuración regional,
     * permitiendo que los mensajes subsiguientes se obtengan en el nuevo idioma.
     *
     * @param lenguaje El nuevo código de idioma.
     * @param pais El nuevo código de país.
     */
    public void setLenguaje(String lenguaje, String pais) {
        this.locale = new Locale(lenguaje, pais);
        this.bundle = ResourceBundle.getBundle("mensajes", locale);
    }

    /**
     * Obtiene el objeto `Locale` que está siendo utilizado actualmente por el manejador.
     *
     * @return El objeto `Locale` actual.
     */
    public Locale getLocale() {
        return  locale;
    }
}