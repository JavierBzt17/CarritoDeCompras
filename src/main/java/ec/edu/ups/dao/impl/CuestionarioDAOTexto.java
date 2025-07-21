package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CuestionarioDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Cuestionario;
import ec.edu.ups.modelo.Pregunta;
import ec.edu.ups.modelo.Respuesta;
import ec.edu.ups.modelo.Usuario;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de CuestionarioDAO que persiste los datos en archivos de texto.
 * Utiliza un sistema de dos archivos para manejar la relación uno-a-muchos
 * entre un Cuestionario y sus Respuestas.
 */
public class CuestionarioDAOTexto implements CuestionarioDAO {

    private final String cuestionariosFilePath;
    private final String respuestasFilePath;
    private final UsuarioDAO usuarioDAO;

    /**
     * Constructor que requiere la ruta base y el DAO de Usuario.
     * @param basePath La carpeta donde se guardarán los archivos.
     * @param usuarioDAO El DAO para poder asociar un cuestionario a un usuario.
     */
    public CuestionarioDAOTexto(String basePath, UsuarioDAO usuarioDAO) {
        this.cuestionariosFilePath = Paths.get(basePath, "cuestionarios.txt").toString();
        this.respuestasFilePath = Paths.get(basePath, "respuestas.txt").toString();
        this.usuarioDAO = usuarioDAO;
    }

    /**
     * {@inheritDoc}
     * Guarda o actualiza un cuestionario. Lee todos los cuestionarios, reemplaza
     * el que corresponde al mismo usuario y reescribe los archivos.
     */
    @Override
    // MÉTODO ACTUALIZADO: Combina la lógica de crear y actualizar.
    public void guardar(Cuestionario cuestionario) {
        List<Cuestionario> todos = listarTodosPrivado();
        // Elimina el cuestionario anterior de este usuario, si existía.
        todos.removeIf(c -> c.getUsuario().equals(cuestionario.getUsuario()));
        // Añade la nueva versión del cuestionario.
        todos.add(cuestionario);
        guardarTodos(todos);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    // MÉTODO ACTUALIZADO: Ahora busca por Cédula (String) en lugar de por Objeto Usuario.
    public Cuestionario buscarPorCedula(String cedula) {
        // 1. Validar que el usuario exista
        Usuario usuario = usuarioDAO.buscarPorCedula(cedula);
        if (usuario == null) {
            return null; // Si el usuario no existe, no puede tener un cuestionario.
        }

        // 2. Reconstruir el cuestionario
        Cuestionario cuestionario = new Cuestionario(cedula);

        // 3. Llenar con preguntas por defecto
        for (Pregunta p : Pregunta.values()) {
            cuestionario.agregarRespuesta(new Respuesta(p.ordinal() + 1, p.getEnunciado()));
        }

        // 4. Leer las respuestas guardadas del archivo y actualizar el objeto
        try (BufferedReader br = new BufferedReader(new FileReader(respuestasFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 3);
                // Si la línea corresponde a la cédula buscada
                if (parts.length >= 3 && parts[0].equals(cedula)) {
                    int preguntaId = Integer.parseInt(parts[1]);
                    String textoRespuesta = parts[2];

                    Respuesta respuesta = cuestionario.buscarRespuestaPorId(preguntaId);
                    if (respuesta != null) {
                        respuesta.setRespuesta(textoRespuesta);
                    }
                }
            }
        } catch (IOException e) {
            // No es un error si el archivo de respuestas no existe.
        }

        return cuestionario;
    }

    /**
     * Método privado para leer todos los cuestionarios. Es una función de ayuda interna.
     */
    private List<Cuestionario> listarTodosPrivado() {
        List<Cuestionario> cuestionarios = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(cuestionariosFilePath))) {
            String cedula;
            while ((cedula = br.readLine()) != null) {
                Cuestionario c = buscarPorCedula(cedula);
                if (c != null) {
                    cuestionarios.add(c);
                }
            }
        } catch (IOException e) {
            // Archivo no existe, retorna lista vacía.
        }
        return cuestionarios;
    }

    /**
     * Método privado que reescribe los archivos con la lista actualizada de cuestionarios.
     */
    private void guardarTodos(List<Cuestionario> cuestionarios) {
        // Sobrescribir el archivo de cuestionarios (solo con las cédulas)
        try (PrintWriter out = new PrintWriter(new FileWriter(cuestionariosFilePath, false))) {
            for (Cuestionario c : cuestionarios) {
                out.println(c.getUsuario());
            }
        } catch (IOException e) {
            System.err.println("Error al reescribir cuestionarios.txt: " + e.getMessage());
        }

        // Sobrescribir el archivo de respuestas
        try (PrintWriter out = new PrintWriter(new FileWriter(respuestasFilePath, false))) {
            for (Cuestionario c : cuestionarios) {
                for (Respuesta r : c.getRespuestas()) {
                    if (r.getRespuesta() != null && !r.getRespuesta().isEmpty()) {
                        out.println(c.getUsuario() + "," + r.getId() + "," + r.getRespuesta());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al reescribir respuestas.txt: " + e.getMessage());
        }
    }
}