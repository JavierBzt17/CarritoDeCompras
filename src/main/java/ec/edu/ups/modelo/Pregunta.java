package ec.edu.ups.modelo;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

/**
 * La enumeración **Pregunta** define una colección de preguntas predefinidas
 * que pueden ser utilizadas en un cuestionario. Cada pregunta tiene un enunciado
 * que puede ser internacionalizado.
 */
public enum Pregunta {
    COLOR_FAVORITO("pregunta.color_favorito"),
    NOMBRE_MASCOTA("pregunta.nombre_mascota"),
    COMIDA_FAVORITA("pregunta.comida_favorita"),
    CIUDAD_NACIMIENTO("pregunta.ciudad_nacimiento"),
    CANTANTE_FAVORITO("pregunta.cantante_favorito"),
    NOMBRE_PRIMER_AMIGO("pregunta.nombre_primer_amigo"),
    PELICULA_FAVORITA("pregunta.pelicula_favorita"),
    NOMBRE_MADRE("pregunta.nombre_madre"),
    NOMBRE_PADRE("pregunta.nombre_padre"),
    NOMBRE_CARINO("pregunta.nombre_carino"),
    CANTIDAD_HERMANOS("pregunta.cantidad_hermanos"),
    NOMBRE_COLEGIO("pregunta.nombre_colegio");

    private String enunciado;
    private MensajeInternacionalizacionHandler mi;

    /**
     * Constructor para los elementos de la enumeración Pregunta.
     *
     * @param enunciado La clave del enunciado de la pregunta, utilizada para la internacionalización.
     */
    Pregunta(String enunciado) {
        this.enunciado = enunciado;
    }

    /**
     * Establece el manejador de internacionalización para esta pregunta.
     * Esto permite que el enunciado de la pregunta se obtenga en el idioma correcto.
     *
     * @param mi El objeto **MensajeInternacionalizacionHandler** que proporciona los mensajes traducidos.
     */
    public void setMensajeIdioma(MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
    }

    /**
     * Obtiene el enunciado de la pregunta. Si se ha configurado un manejador de idioma,
     * devuelve el enunciado traducido; de lo contrario, devuelve la clave original del enunciado.
     *
     * @return El enunciado de la pregunta, posiblemente internacionalizado.
     */
    public String getEnunciado() {
        if (mi != null) {
            return mi.get(enunciado);
        } else {
            return enunciado;
        }
    }
}