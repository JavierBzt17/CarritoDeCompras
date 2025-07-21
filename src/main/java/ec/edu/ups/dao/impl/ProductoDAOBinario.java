package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación de ProductoDAO que persiste los datos en un archivo binario (.dat).
 * Las operaciones de escritura (crear, actualizar, eliminar) reescriben el archivo
 * completo con la lista de objetos actualizada.
 */
public class ProductoDAOBinario implements ProductoDAO {
    private final String filePath;

    /**
     * Constructor que inicializa la ruta del archivo binario donde se guardarán los productos.
     * @param basePath La carpeta base donde se creará el archivo "productos.dat".
     */
    public ProductoDAOBinario(String basePath) {
        this.filePath = Paths.get(basePath, "productos.dat").toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void crear(Producto producto) {
        List<Producto> productos = listarTodos();
        productos.add(producto);
        guardarTodos(productos);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Producto buscarPorCodigo(int codigo) {
        return listarTodos().stream()
                .filter(p -> p.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        String nombreBusqueda = nombre.toLowerCase();
        return listarTodos().stream()
                .filter(p -> p.getNombre().toLowerCase().contains(nombreBusqueda))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void actualizar(Producto producto) {
        List<Producto> productos = listarTodos();
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo() == producto.getCodigo()) {
                productos.set(i, producto); // Reemplaza el objeto en la lista
                break;
            }
        }
        guardarTodos(productos);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void eliminar(int codigo) {
        List<Producto> productos = listarTodos();
        productos.removeIf(p -> p.getCodigo() == codigo); // Elimina el objeto de la lista
        guardarTodos(productos);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Producto> listarTodos() {
        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<Producto>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al leer el archivo binario de productos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Método privado de utilidad para escribir la lista completa de productos en el archivo binario.
     * Sobrescribe el contenido anterior del archivo.
     * @param productos La lista de productos a guardar.
     */
    private void guardarTodos(List<Producto> productos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(productos);
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo binario de productos: " + e.getMessage());
        }
    }
}