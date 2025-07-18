package ec.edu.ups.dao;

import ec.edu.ups.modelo.Producto;

import java.util.List;

/**
 * Interfaz que define las operaciones de persistencia de datos para la entidad Producto.
 * Establece un contrato para cualquier clase DAO que gestione los productos,
 * garantizando que se implementen los métodos estándar de un CRUD (Crear, Leer, Actualizar, Eliminar)
 * y otras operaciones de búsqueda.
 *
 */
public interface ProductoDAO {

    /**
     * Persiste un nuevo objeto Producto en el sistema de almacenamiento.
     *
     * @param producto El objeto Producto a ser creado y guardado.
     */
    void crear(Producto producto);

    /**
     * Busca y devuelve un Producto específico a partir de su código único.
     *
     * @param codigo El código del producto a buscar.
     * @return El objeto Producto encontrado, o null si no se encuentra ninguno con ese código.
     */
    Producto buscarPorCodigo(int codigo);

    /**
     * Busca y devuelve una lista de productos cuyo nombre coincide con el término de búsqueda.
     *
     * @param nombre El nombre o parte del nombre del producto a buscar.
     * @return Una lista de productos que coinciden con el criterio de búsqueda.
     */
    List<Producto> buscarPorNombre(String nombre);

    /**
     * Actualiza los datos de un producto existente en el sistema de almacenamiento.
     *
     * @param producto El objeto Producto con la información actualizada.
     */
    void actualizar(Producto producto);

    /**
     * Elimina un producto del sistema de almacenamiento utilizando su código.
     *
     * @param codigo El código del producto a eliminar.
     */
    void eliminar(int codigo);

    /**
     * Devuelve una lista con todos los productos registrados en el sistema.
     *
     * @return Una lista de todos los objetos Producto.
     */
    List<Producto> listarTodos();
}