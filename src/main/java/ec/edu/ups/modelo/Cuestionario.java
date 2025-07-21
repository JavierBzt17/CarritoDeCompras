package ec.edu.ups.modelo;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import java.io.Serializable; // 1. Importación necesaria para la serialización
import java.util.ArrayList;
import java.util.List;

/**
 * La clase `Cuestionario` representa un cuestionario asociado a un usuario,
 * que contiene una lista de respuestas a preguntas.
 * AHORA ES SERIALIZABLE para permitir la persistencia en archivos binarios.
 */
// 2. Se añade "implements Serializable"
public class Cuestionario implements Serializable {

    /**
     * UID de versión para la serialización. Previene errores de versionado.
     */
    private static final long serialVersionUID = 1L;

    private String usuario;
    private List<Respuesta> respuestas;

    /**
     * Constructor para la clase Cuestionario.
     * Inicializa el cuestionario con un usuario específico y una lista de respuestas vacía.
     *
     * @param usuario El nombre de usuario al que está asociado el cuestionario.
     */
    public Cuestionario(String usuario) {
        this.usuario = usuario;
        this.respuestas = new ArrayList<>();
    }

    /**
     * Obtiene el nombre de usuario asociado a este cuestionario.
     *
     * @return El nombre de usuario.
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Obtiene la lista de respuestas de este cuestionario.
     *
     * @return La lista de objetos Respuesta.
     */
    public List<Respuesta> getRespuestas() {
        return respuestas;
    }

    /**
     * Agrega una nueva respuesta al cuestionario.
     * Si una respuesta con el mismo ID ya existe, la actualiza; de lo contrario, la añade.
     *
     * @param nuevaRespuesta La nueva respuesta a agregar o actualizar.
     */
    public void agregarRespuesta(Respuesta nuevaRespuesta) {
        boolean found = false;
        for (int i = 0; i < respuestas.size(); i++) {
            if (respuestas.get(i).equals(nuevaRespuesta)) {
                respuestas.set(i, nuevaRespuesta);
                found = true;
                break;
            }
        }
        if (!found) {
            respuestas.add(nuevaRespuesta);
        }
    }

    /**
     * Busca y devuelve una respuesta específica por su ID de pregunta.
     *
     * @param idPregunta El ID de la pregunta de la respuesta que se desea buscar.
     * @return El objeto Respuesta si se encuentra, o null si no existe.
     */
    public Respuesta buscarRespuestaPorId(int idPregunta) {
        for (Respuesta r : respuestas) {
            if (r.getId() == idPregunta) {
                return r;
            }
        }
        return null;
    }

    /**
     * Genera una lista de respuestas por defecto basadas en las preguntas predefinidas.
     * Cada respuesta tendrá un ID y el enunciado de la pregunta correspondiente.
     *
     * @return Una lista de objetos Respuesta con preguntas por defecto.
     */
    public List<Respuesta> preguntasPorDefecto() {
        List<Respuesta> lista = new ArrayList<>();
        Pregunta[] preguntas = Pregunta.values();
        for (int i = 0; i < preguntas.length; i++) {
            lista.add(new Respuesta(i + 1, preguntas[i].getEnunciado()));
        }
        return lista;
    }

    /**
     * Aplica el idioma especificado a todas las preguntas predefinidas.
     * Esto actualiza los enunciados de las preguntas según el `MensajeInternacionalizacionHandler` proporcionado.
     *
     * @param mi El manejador de internacionalización de mensajes a aplicar.
     */
    public void aplicarIdioma(MensajeInternacionalizacionHandler mi) {
        for (Pregunta p : Pregunta.values()) {
            p.setMensajeIdioma(mi);
        }
    }
}