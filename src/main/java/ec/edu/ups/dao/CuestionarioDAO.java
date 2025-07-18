package ec.edu.ups.dao;

import ec.edu.ups.modelo.Cuestionario;

/**
 * Interfaz que define las operaciones de persistencia de datos para la entidad Cuestionario.
 * Establece un contrato para cualquier clase DAO que gestione los cuestionarios de seguridad,
 * asegurando la implementación de los métodos necesarios para su guardado y recuperación.
 *
 */
public interface CuestionarioDAO {

    /**
     * Guarda o actualiza un cuestionario en el sistema de almacenamiento.
     * Si el cuestionario ya existe para el usuario, se actualiza; si no, se crea.
     *
     * @param cuestionario El objeto Cuestionario a ser guardado o actualizado.
     */
    void guardar(Cuestionario cuestionario);

    /**
     * Busca y devuelve un Cuestionario específico a partir de la cédula del usuario asociado.
     *
     * @param cedula La cédula del usuario cuyo cuestionario se desea buscar.
     * @return El objeto Cuestionario encontrado, o null si no se encuentra ninguno para esa cédula.
     */
    Cuestionario buscarPorCedula(String cedula);
}