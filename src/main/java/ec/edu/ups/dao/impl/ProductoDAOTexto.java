package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación de ProductoDAO que persiste los datos en un archivo de texto.
 * Realiza las operaciones CRUD leyendo y escribiendo en formato CSV.
 */
public class ProductoDAOTexto implements ProductoDAO {
    private final String filePath;

    /**
     * Constructor que inicializa la ruta del archivo de texto donde se guardarán los productos.
     * @param basePath La carpeta base donde se creará el archivo "productos.txt".
     */
    public ProductoDAOTexto(String basePath) {
        this.filePath = Paths.get(basePath, "productos.txt").toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void crear(Producto producto) {
        try (PrintWriter out = new PrintWriter(new FileWriter(filePath, true))) {
            out.println(producto.getCodigo() + "," + producto.getNombre() + "," + producto.getPrecio() + "," + producto.getStock());
        } catch (IOException e) {
            System.err.println("Error al escribir en productos.txt: " + e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    // RENOMBRADO: de 'read' a 'buscarPorCodigo'
    public Producto buscarPorCodigo(int codigo) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    int currentCodigo = Integer.parseInt(parts[0]);
                    if (currentCodigo == codigo) {
                        return new Producto(currentCodigo, parts[1], Double.parseDouble(parts[2]), Integer.parseInt(parts[3]));
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al leer el producto por código: " + e.getMessage());
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    // NUEVO MÉTODO: implementado para buscar por nombre.
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> todos = listarTodos();
        String nombreBusqueda = nombre.toLowerCase(); // Para búsqueda no sensible a mayúsculas

        return todos.stream()
                .filter(p -> p.getNombre().toLowerCase().contains(nombreBusqueda))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    // RENOMBRADO: de 'update' a 'actualizar'
    public void actualizar(Producto producto) {
        List<Producto> productos = listarTodos();
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo() == producto.getCodigo()) {
                productos.set(i, producto);
                break;
            }
        }
        guardarTodos(productos);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    // RENOMBRADO: de 'delete' a 'eliminar'
    public void eliminar(int codigo) {
        List<Producto> productos = listarTodos();
        productos.removeIf(p -> p.getCodigo() == codigo);
        guardarTodos(productos);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    // RENOMBRADO: de 'findAll' a 'listarTodos'
    public List<Producto> listarTodos() {
        List<Producto> productos = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) return productos;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    productos.add(new Producto(Integer.parseInt(parts[0]), parts[1], Double.parseDouble(parts[2]), Integer.parseInt(parts[3])));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al leer todos los productos: " + e.getMessage());
        }
        return productos;
    }

    /**
     * Método privado de utilidad para reescribir todo el archivo de texto
     * con una lista actualizada de productos. Se usa en 'actualizar' y 'eliminar'.
     * @param productos La lista completa de productos a guardar.
     */
    private void guardarTodos(List<Producto> productos) {
        try (PrintWriter out = new PrintWriter(new FileWriter(filePath, false))) { // false para sobreescribir
            for (Producto p : productos) {
                out.println(p.getCodigo() + "," + p.getNombre() + "," + p.getPrecio() + "," + p.getStock());
            }
        } catch (IOException e) {
            System.err.println("Error al reescribir el archivo de productos: " + e.getMessage());
        }
    }
}