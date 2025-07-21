package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.modelo.Usuario;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación de CarritoDAO que persiste los datos en archivos de texto.
 * NOTA: Debido a la complejidad de las relaciones de objetos, esta implementación
 * utiliza dos archivos: 'carritos.txt' para la información principal y 'carrito_items.txt'
 * para los detalles de los productos.
 */
public class CarritoDAOTexto implements CarritoDAO {

    private final String carritosFilePath;
    private final String itemsFilePath;
    private final UsuarioDAO usuarioDAO;
    private final ProductoDAO productoDAO;

    /**
     * Constructor que requiere la ruta base y los DAOs de Usuario y Producto
     * para reconstruir los objetos Carrito completos.
     *
     * @param basePath La carpeta donde se guardarán los archivos.
     * @param usuarioDAO El DAO para acceder a los datos de los usuarios.
     * @param productoDAO El DAO para acceder a los datos de los productos.
     */
    public CarritoDAOTexto(String basePath, UsuarioDAO usuarioDAO, ProductoDAO productoDAO) {
        this.carritosFilePath = Paths.get(basePath, "carritos.txt").toString();
        this.itemsFilePath = Paths.get(basePath, "carrito_items.txt").toString();
        this.usuarioDAO = usuarioDAO;
        this.productoDAO = productoDAO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void crear(Carrito carrito) {
        // Guardar la información principal del carrito
        try (PrintWriter out = new PrintWriter(new FileWriter(carritosFilePath, true))) {
            // Formato: codigo_carrito,cedula_usuario,fecha_en_milisegundos
            out.println(carrito.getCodigo() + "," + carrito.getUsuario().getCedula() + "," + carrito.getFechaCreacion().getTimeInMillis());
        } catch (IOException e) {
            System.err.println("Error al escribir en carritos.txt: " + e.getMessage());
        }

        // Guardar cada item del carrito por separado
        try (PrintWriter out = new PrintWriter(new FileWriter(itemsFilePath, true))) {
            for (ItemCarrito item : carrito.obtenerItems()) {
                // Formato: codigo_carrito,codigo_producto,cantidad
                out.println(carrito.getCodigo() + "," + item.getProducto().getCodigo() + "," + item.getCantidad());
            }
        } catch (IOException e) {
            System.err.println("Error al escribir en carrito_items.txt: " + e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Carrito buscarPorCodigo(int codigo) {
        // Es más eficiente listar todos y luego filtrar, para no leer los archivos múltiples veces.
        return listarTodos().stream()
                .filter(c -> c.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void actualizar(Carrito carrito) {
        List<Carrito> todos = listarTodos();
        // Se elimina el carrito viejo para luego añadir la versión actualizada
        todos.removeIf(c -> c.getCodigo() == carrito.getCodigo());
        todos.add(carrito);
        guardarTodos(todos);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void eliminar(int codigo) {
        List<Carrito> todos = listarTodos();
        todos.removeIf(c -> c.getCodigo() == codigo);
        guardarTodos(todos);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Carrito> listarTodos() {
        List<Carrito> carritos = new ArrayList<>();

        // 1. Leer la información base de los carritos
        try (BufferedReader br = new BufferedReader(new FileReader(carritosFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    int codigo = Integer.parseInt(parts[0]);
                    String cedulaUsuario = parts[1];
                    long fechaMillis = Long.parseLong(parts[2]);

                    // Se usa el DAO de Usuario para obtener el objeto completo
                    Usuario usuario = usuarioDAO.buscarPorCedula(cedulaUsuario);
                    if (usuario != null) {
                        Carrito carrito = new Carrito();
                        carrito.setCodigo(codigo);
                        carrito.setUsuario(usuario);
                        GregorianCalendar fecha = new GregorianCalendar();
                        fecha.setTimeInMillis(fechaMillis);
                        carrito.setFechaCreacion(fecha);
                        carritos.add(carrito);
                    }
                }
            }
        } catch (IOException e) {
            // No se considera un error si el archivo no existe en la primera ejecución
        }

        // 2. Leer los items y asignarlos a cada carrito correspondiente
        try (BufferedReader br = new BufferedReader(new FileReader(itemsFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    int carritoCodigo = Integer.parseInt(parts[0]);
                    int productoCodigo = Integer.parseInt(parts[1]);
                    int cantidad = Integer.parseInt(parts[2]);

                    // Buscar el carrito en la lista ya creada y añadirle el item
                    for (Carrito c : carritos) {
                        if (c.getCodigo() == carritoCodigo) {
                            // Se usa el DAO de Producto para obtener el objeto completo
                            Producto producto = productoDAO.buscarPorCodigo(productoCodigo);
                            if (producto != null) {
                                c.agregarProducto(producto, cantidad);
                            }
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            // No se considera un error si el archivo no existe
        }

        return carritos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    // ✅ MÉTODO AÑADIDO
    public List<Carrito> buscarPorUsuario(Usuario usuario) {
        return listarTodos().stream()
                .filter(carrito -> carrito.getUsuario().getCedula().equals(usuario.getCedula()))
                .collect(Collectors.toList());
    }

    /**
     * Método privado que reescribe ambos archivos de texto con la lista actualizada de carritos.
     * Es crucial para las operaciones de 'actualizar' y 'eliminar'.
     */
    private void guardarTodos(List<Carrito> carritos) {
        // Sobrescribir el archivo de carritos
        try (PrintWriter out = new PrintWriter(new FileWriter(carritosFilePath, false))) {
            for(Carrito carrito : carritos) {
                out.println(carrito.getCodigo() + "," + carrito.getUsuario().getCedula() + "," + carrito.getFechaCreacion().getTimeInMillis());
            }
        } catch (IOException e) {
            System.err.println("Error al reescribir carritos.txt: " + e.getMessage());
        }

        // Sobrescribir el archivo de items
        try (PrintWriter out = new PrintWriter(new FileWriter(itemsFilePath, false))) {
            for(Carrito carrito : carritos) {
                for (ItemCarrito item : carrito.obtenerItems()) {
                    out.println(carrito.getCodigo() + "," + item.getProducto().getCodigo() + "," + item.getCantidad());
                }
            }
        } catch (IOException e) {
            System.err.println("Error al reescribir carrito_items.txt: " + e.getMessage());
        }
    }
}