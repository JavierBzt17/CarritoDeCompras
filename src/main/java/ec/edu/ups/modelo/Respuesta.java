package ec.edu.ups.modelo;

import java.util.Objects;

/**
 * La clase **Respuesta** representa una respuesta a una pregunta específica dentro de un cuestionario.
 * Contiene un identificador único para la pregunta, el enunciado de la pregunta y la respuesta proporcionada.
 */
public class Respuesta {
    private int id;
    private String enunciado;
    private String respuesta;

    /**
     * Constructor para crear una respuesta sin una respuesta aún establecida.
     *
     * @param id El ID único de la pregunta a la que se refiere esta respuesta.
     * @param enunciado El enunciado de la pregunta.
     */
    public Respuesta(int id, String enunciado) {
        this.id = id;
        this.enunciado = enunciado;
        this.respuesta = null;
    }

    /**
     * Constructor para crear una respuesta con una respuesta ya establecida.
     *
     * @param id El ID único de la pregunta a la que se refiere esta respuesta.
     * @param enunciado El enunciado de la pregunta.
     * @param respuesta La respuesta proporcionada.
     */
    public Respuesta(int id, String enunciado, String respuesta) {
        this.id = id;
        this.enunciado = enunciado;
        this.respuesta = respuesta;
    }

    /**
     * Obtiene el ID de la pregunta.
     *
     * @return El ID de la pregunta.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el ID de la pregunta.
     *
     * @param id El nuevo ID de la pregunta.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el enunciado de la pregunta.
     *
     * @return El enunciado de la pregunta.
     */
    public String getEnunciado() {
        return enunciado;
    }

    /**
     * Establece el enunciado de la pregunta.
     *
     * @param enunciado El nuevo enunciado de la pregunta.
     */
    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    /**
     * Obtiene la respuesta proporcionada.
     *
     * @return La respuesta.
     */
    public String getRespuesta() {
        return respuesta;
    }

    /**
     * Establece la respuesta para esta pregunta.
     *
     * @param respuesta La nueva respuesta.
     */
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    /**
     * Compara este objeto Respuesta con otro objeto para determinar si son iguales.
     * Dos objetos Respuesta se consideran iguales si tienen el mismo ID.
     *
     * @param o El objeto a comparar.
     * @return true si los objetos son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Respuesta)) return false;
        Respuesta that = (Respuesta) o;
        return id == that.id;
    }

    /**
     * Calcula un valor de código hash para este objeto Respuesta.
     * El código hash se basa únicamente en el ID de la pregunta.
     *
     * @return El valor del código hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Retorna una representación en cadena del objeto Respuesta.
     *
     * @return Una cadena que representa la respuesta, incluyendo su ID, enunciado y la respuesta (o "N/A" si no hay respuesta).
     */
    @Override
    public String toString() {
        return "Respuesta{" +
                "id=" + id +
                ", enunciado='" + enunciado + '\'' +
                ", respuesta='" + (respuesta != null ? respuesta : "N/A") + '\'' +
                '}';
    }
}