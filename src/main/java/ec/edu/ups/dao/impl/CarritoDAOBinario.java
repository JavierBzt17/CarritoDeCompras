package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Usuario;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación de CarritoDAO que persiste los datos en un archivo binario (.dat).
 * Las operaciones de escritura (crear, actualizar, eliminar) leen la lista completa
 * de objetos, la modifican en memoria y la reescriben por completo en el archivo.
 */
public class CarritoDAOBinario implements CarritoDAO {

    private final String filePath;

    /**
     * Constructor que inicializa la ruta del archivo binario.
     * @param basePath La carpeta base donde se creará el archivo "carritos.dat".
     */
    public CarritoDAOBinario(String basePath) {
        this.filePath = Paths.get(basePath, "carritos.dat").toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    // RENOMBRADO: de 'create' a 'crear'
    public void crear(Carrito carrito) {
        List<Carrito> carritos = listarTodos();
        carritos.add(carrito);
        guardarTodos(carritos);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Carrito buscarPorCodigo(int codigo) {
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
        List<Carrito> carritos = listarTodos();
        for (int i = 0; i < carritos.size(); i++) {
            if (carritos.get(i).getCodigo() == carrito.getCodigo()) {
                carritos.set(i, carrito); // Reemplaza el objeto en la lista
                break;
            }
        }
        guardarTodos(carritos);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void eliminar(int codigo) {
        List<Carrito> carritos = listarTodos();
        carritos.removeIf(c -> c.getCodigo() == codigo);
        guardarTodos(carritos);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    // RENOMBRADO: de 'findAll' a 'listarTodos'
    public List<Carrito> listarTodos() {
        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            // Lee la lista completa de carritos como un solo objeto
            return (List<Carrito>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al leer carritos.dat: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    // NUEVO MÉTODO AÑADIDO
    public List<Carrito> buscarPorUsuario(Usuario usuario) {
        return listarTodos().stream()
                .filter(c -> c.getUsuario().getCedula().equals(usuario.getCedula()))
                .collect(Collectors.toList());
    }

    /**
     * Método privado que escribe la lista completa de carritos en el archivo binario,
     * sobrescribiendo cualquier contenido anterior.
     * @param carritos La lista de carritos a guardar.
     */
    // RENOMBRADO: de 'guardarLista' a 'guardarTodos' por consistencia
    private void guardarTodos(List<Carrito> carritos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(carritos);
        } catch (IOException e) {
            System.err.println("Error al guardar carritos.dat: " + e.getMessage());
        }
    }
}