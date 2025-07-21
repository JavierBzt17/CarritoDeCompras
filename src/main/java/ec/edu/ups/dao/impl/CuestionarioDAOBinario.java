package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CuestionarioDAO;
import ec.edu.ups.modelo.Cuestionario;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de CuestionarioDAO que persiste los datos en un archivo binario (.dat).
 * Las operaciones de escritura leen la lista completa, la modifican y la reescriben.
 */
public class CuestionarioDAOBinario implements CuestionarioDAO {

    private final String filePath;

    /**
     * Constructor que inicializa la ruta del archivo binario.
     * @param basePath La carpeta base donde se creará el archivo "cuestionarios.dat".
     */
    public CuestionarioDAOBinario(String basePath) {
        this.filePath = Paths.get(basePath, "cuestionarios.dat").toString();
    }

    /**
     * {@inheritDoc}
     * Guarda o actualiza un cuestionario. Lee todos los cuestionarios, reemplaza
     * el que corresponde al mismo usuario y reescribe el archivo.
     */
    @Override
    public void guardar(Cuestionario cuestionario) {
        List<Cuestionario> cuestionarios = listarTodosPrivado();
        // Elimina el cuestionario anterior de este usuario, si ya existía.
        cuestionarios.removeIf(c -> c.getUsuario().equals(cuestionario.getUsuario()));
        // Añade la versión nueva o actualizada del cuestionario.
        cuestionarios.add(cuestionario);
        guardarTodos(cuestionarios);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cuestionario buscarPorCedula(String cedula) {
        // Lee la lista completa y busca el cuestionario por la cédula del usuario.
        return listarTodosPrivado().stream()
                .filter(c -> c.getUsuario().equals(cedula))
                .findFirst()
                .orElse(null);
    }

    /**
     * Método privado que lee la lista completa de cuestionarios desde el archivo binario.
     * @return Una lista de Cuestionario; vacía si el archivo no existe o hay un error.
     */
    private List<Cuestionario> listarTodosPrivado() {
        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<Cuestionario>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al leer cuestionarios.dat: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Método privado que escribe la lista completa de cuestionarios en el archivo binario,
     * sobrescribiendo cualquier contenido anterior.
     * @param cuestionarios La lista de cuestionarios a guardar.
     */
    private void guardarTodos(List<Cuestionario> cuestionarios) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(cuestionarios);
        } catch (IOException e) {
            System.err.println("Error al guardar cuestionarios.dat: " + e.getMessage());
        }
    }
}