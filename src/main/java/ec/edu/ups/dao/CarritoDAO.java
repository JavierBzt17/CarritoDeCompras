package ec.edu.ups.dao;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Usuario;

import java.util.List;

/**
 * Interfaz que define las operaciones de persistencia de datos para la entidad Carrito.
 * Establece un contrato para cualquier clase DAO que gestione los carritos de compra,
 * garantizando que se implementen los métodos estándar de un CRUD (Crear, Leer, Actualizar, Eliminar).
 *
 */
public interface CarritoDAO {

    /**
     * Persiste un nuevo objeto Carrito en el sistema de almacenamiento.
     *
     * @param carrito El objeto Carrito a ser creado y guardado.
     */
    void crear(Carrito carrito);

    /**
     * Busca y devuelve un Carrito específico a partir de su código único.
     *
     * @param codigo El código del carrito a buscar.
     * @return El objeto Carrito encontrado, o null si no se encuentra ninguno con ese código.
     */
    Carrito buscarPorCodigo(int codigo);

    /**
     * Actualiza los datos de un carrito existente en el sistema de almacenamiento.
     *
     * @param carrito El objeto Carrito con la información actualizada.
     */
    void actualizar(Carrito carrito);

    /**
     * Elimina un carrito del sistema de almacenamiento utilizando su código.
     *
     * @param codigo El código del carrito a eliminar.
     */
    void eliminar(int codigo);

    /**
     * Devuelve una lista con todos los carritos registrados en el sistema.
     *
     * @return Una lista de todos los objetos Carrito.
     */
    List<Carrito> listarTodos();

    /**
     * Busca y devuelve todos los carritos que pertenecen a un usuario específico.
     *
     * @param usuario El objeto Usuario por el cual se filtrarán los carritos.
     * @return Una lista de carritos pertenecientes al usuario especificado.
     */
    List<Carrito> buscarPorUsuario(Usuario usuario);
}