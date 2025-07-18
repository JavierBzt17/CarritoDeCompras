package ec.edu.ups.dao.impl;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.dao.ProductoDAO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementación en memoria de la interfaz ProductoDAO.
 * Esta clase simula una base de datos para los productos, almacenándolos
 * en una lista en memoria. Es ideal para desarrollo y pruebas, ya que los
 * datos se pierden al cerrar la aplicación.
 *
 */
public class ProductoDAOMemoria implements ProductoDAO {

    private List<Producto> productos;

    /**
     * Constructor que inicializa la lista en memoria y la precarga con
     * productos de ejemplo para facilitar las pruebas.
     */
    public ProductoDAOMemoria() {
        productos = new ArrayList<>();
        crear(new Producto(1, "a",15));
        crear(new Producto(2, "b",25));
        crear(new Producto(3, "c",35));
    }

    /**
     * Añade un nuevo producto a la lista en memoria.
     *
     * @param producto El objeto Producto a ser guardado.
     */
    @Override
    public void crear(Producto producto) {
        productos.add(producto);
    }

    /**
     * Busca y devuelve un producto por su código único.
     *
     * @param codigo El código del producto a buscar.
     * @return El objeto Producto encontrado, o null si no existe ninguno con ese código.
     */
    @Override
    public Producto buscarPorCodigo(int codigo) {
        for (Producto producto : productos) {
            if (producto.getCodigo() == codigo) {
                return producto;
            }
        }
        return null;
    }

    /**
     * Busca y devuelve una lista de productos cuyo nombre coincide exactamente
     * con el término de búsqueda, ignorando mayúsculas y minúsculas.
     *
     * @param nombre El nombre del producto a buscar.
     * @return Una lista de productos que coinciden con el nombre. Puede estar vacía.
     */
    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> productosEncontrados = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getNombre().equalsIgnoreCase(nombre)) {
                productosEncontrados.add(producto);
            }
        }
        return productosEncontrados;
    }

    /**
     * Actualiza un producto existente en la lista.
     * Busca el producto por su código y lo reemplaza con la nueva versión proporcionada.
     *
     * @param producto El objeto Producto con los datos actualizados.
     */
    @Override
    public void actualizar(Producto producto) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo() == producto.getCodigo()) {
                productos.set(i, producto);
                break;
            }
        }
    }

    /**
     * Elimina un producto de la lista en memoria utilizando su código.
     * Usa un iterador para una eliminación segura durante el recorrido de la lista.
     *
     * @param codigo El código del producto a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        Iterator<Producto> iterator = productos.iterator();
        while (iterator.hasNext()) {
            Producto producto = iterator.next();
            if (producto.getCodigo() == codigo) {
                iterator.remove();
                break;
            }
        }
    }

    /**
     * Devuelve una copia de la lista con todos los productos almacenados en memoria.
     *
     * @return Una lista de todos los objetos Producto.
     */
    @Override
    public List<Producto> listarTodos() {
        return new ArrayList<>(productos);
    }
}