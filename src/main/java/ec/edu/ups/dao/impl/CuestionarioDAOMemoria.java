package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CuestionarioDAO;
import ec.edu.ups.modelo.Cuestionario;
import ec.edu.ups.modelo.Respuesta;

import java.util.List;
import java.util.ArrayList;

/**
 * Implementación en memoria de la interfaz CuestionarioDAO.
 * Esta clase simula una base de datos para los cuestionarios de seguridad,
 * almacenándolos en una lista en memoria. Es ideal para desarrollo y pruebas,
 * ya que los datos se pierden al cerrar la aplicación.
 *
 */
public class CuestionarioDAOMemoria implements CuestionarioDAO {

    private final List<Cuestionario> cuestionarios;

    /**
     * Constructor que inicializa la lista en memoria y precarga un
     * cuestionario de ejemplo para un usuario de prueba.
     */
    public CuestionarioDAOMemoria() {
        this.cuestionarios = new ArrayList<>();

        Cuestionario cuestionarioAdmin = new Cuestionario("0101234567");
        List<Respuesta> preguntas = cuestionarioAdmin.preguntasPorDefecto();

        preguntas.get(0).setRespuesta("Negro");
        preguntas.get(1).setRespuesta("Firulais");
        preguntas.get(2).setRespuesta("Pizza");

        cuestionarioAdmin.agregarRespuesta(preguntas.get(0));
        cuestionarioAdmin.agregarRespuesta(preguntas.get(1));
        cuestionarioAdmin.agregarRespuesta(preguntas.get(2));

        guardar(cuestionarioAdmin);
    }

    /**
     * Guarda o actualiza un cuestionario en la lista en memoria.
     * Si ya existe un cuestionario para el mismo usuario (identificado por su cédula),
     * el antiguo es reemplazado por el nuevo para evitar duplicados.
     * Si no existe, simplemente se añade a la lista.
     *
     * @param cuestionario El objeto Cuestionario a guardar o actualizar.
     */
    @Override
    public void guardar(Cuestionario cuestionario) {
        Cuestionario existente = this.buscarPorCedula(cuestionario.getUsuario());
        if (existente != null) {
            this.cuestionarios.remove(existente);
        }
        this.cuestionarios.add(cuestionario);
    }

    /**
     * Busca y devuelve un cuestionario basado en la cédula del usuario asociado.
     *
     * @param cedula La cédula del usuario cuyo cuestionario se desea buscar.
     * @return El objeto Cuestionario encontrado, o null si no existe ninguno para esa cédula.
     */
    @Override
    public Cuestionario buscarPorCedula(String cedula) {
        for (Cuestionario cuestionario : cuestionarios) {
            if (cuestionario.getUsuario().equalsIgnoreCase(cedula)) {
                return cuestionario;
            }
        }
        return null;
    }
}