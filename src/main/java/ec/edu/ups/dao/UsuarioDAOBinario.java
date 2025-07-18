package ec.edu.ups.dao;

import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOBinario implements UsuarioDAO {
    private String rutaArchivo = "usuarios.dat";

    public UsuarioDAOBinario() {
        // Asegura que el archivo exista al inicializar el DAO
        try {
            File file = new File(rutaArchivo);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Error al crear el archivo de usuarios binario: " + e.getMessage());
        }
    }

    /**
     * Lee todos los objetos Usuario del archivo binario.
     * Maneja la EOFException (End Of File) para saber cuándo ha terminado de leer.
     * @return Una lista de objetos Usuario.
     */
    private List<Usuario> leerTodosObjetos() {
        List<Usuario> usuarios = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            while (true) {
                try {
                    Usuario usuario = (Usuario) ois.readObject();
                    usuarios.add(usuario);
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            // Ignorar si el archivo está vacío o si la primera lectura falla por EOF (archivo nuevo/vacío)
            if (!(e instanceof EOFException)) {
                System.err.println("Error al leer objetos del archivo binario: " + e.getMessage());
            }
        }
        return usuarios;
    }

    /**
     * Escribe la lista completa de objetos Usuario en el archivo binario, sobrescribiendo el contenido existente.
     * @param usuarios La lista de usuarios a escribir.
     */
    private void escribirTodosObjetos(List<Usuario> usuarios) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            for (Usuario usuario : usuarios) {
                oos.writeObject(usuario);
            }
        } catch (IOException e) {
            System.err.println("Error al escribir objetos en el archivo binario: " + e.getMessage());
        }
    }

    @Override
    public Usuario autenticar(String cedula, String contrasena) {
        List<Usuario> usuarios = leerTodosObjetos();
        for (Usuario u : usuarios) {
            if (u.getCedula().equals(cedula) && u.getContrasena().equals(contrasena)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public void crear(Usuario usuario) {
        List<Usuario> usuarios = leerTodosObjetos(); // Leer todos los existentes
        usuarios.add(usuario); // Añadir el nuevo
        escribirTodosObjetos(usuarios); // Escribir la lista completa de vuelta
    }

    @Override
    public Usuario buscarPorCedula(String cedula) {
        List<Usuario> usuarios = leerTodosObjetos();
        for (Usuario u : usuarios) {
            if (u.getCedula().equals(cedula)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public void actualizar(Usuario usuario) {
        List<Usuario> usuarios = leerTodosObjetos();
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getCedula().equals(usuario.getCedula())) {
                usuarios.set(i, usuario); // Reemplazar el usuario antiguo con el actualizado
                break;
            }
        }
        escribirTodosObjetos(usuarios); // Escribir la lista actualizada
    }

    @Override
    public void eliminar(String cedula) {
        List<Usuario> usuarios = leerTodosObjetos();
        usuarios.removeIf(u -> u.getCedula().equals(cedula)); // Eliminar el usuario por cédula
        escribirTodosObjetos(usuarios); // Escribir la lista sin el usuario eliminado
    }

    @Override
    public List<Usuario> listarTodos() {
        return leerTodosObjetos(); // Simplemente devuelve la lista de todos los usuarios leídos
    }

    @Override
    public List<Usuario> listarPorRol(Rol rol) {
        List<Usuario> usuariosPorRol = new ArrayList<>();
        List<Usuario> todosLosUsuarios = leerTodosObjetos();
        for (Usuario u : todosLosUsuarios) {
            if (u.getRol() == rol) {
                usuariosPorRol.add(u);
            }
        }
        return usuariosPorRol;
    }
}