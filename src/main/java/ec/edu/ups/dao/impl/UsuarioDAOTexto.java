package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class UsuarioDAOTexto implements UsuarioDAO {
    private final String filePath;

    /**
     * Constructor que recibe la ruta base desde la FabricaDAO.
     */
    public UsuarioDAOTexto(String basePath) {
        this.filePath = Paths.get(basePath, "usuarios.txt").toString();
    }

    @Override
    public void crear(Usuario usuario) {
        try (PrintWriter out = new PrintWriter(new FileWriter(filePath, true))) {
            out.println(usuarioToString(usuario));
        } catch (IOException e) {
            System.err.println("Error al crear usuario en texto: " + e.getMessage());
        }
    }

    @Override
    public Usuario buscarPorCedula(String cedula) {
        return listarTodos().stream()
                .filter(u -> u.getCedula().equals(cedula))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void actualizar(Usuario usuario) {
        List<Usuario> usuarios = listarTodos();
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getCedula().equals(usuario.getCedula())) {
                usuarios.set(i, usuario);
                break;
            }
        }
        guardarTodos(usuarios);
    }

    @Override
    public void eliminar(String cedula) {
        List<Usuario> usuarios = listarTodos();
        usuarios.removeIf(u -> u.getCedula().equals(cedula));
        guardarTodos(usuarios);
    }

    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                Usuario u = stringToUsuario(line);
                if (u != null) {
                    usuarios.add(u);
                }
            }
        } catch (IOException e) {
            // No es un error si el archivo no existe la primera vez
        }
        return usuarios;
    }

    @Override
    public Usuario autenticar(String cedula, String contrasena) {
        return listarTodos().stream()
                .filter(u -> u.getCedula().equals(cedula) && u.getContrasena().equals(contrasena))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Usuario> listarPorRol(Rol rol) {
        List<Usuario> todos = listarTodos();
        List<Usuario> porRol = new ArrayList<>();
        for (Usuario u : todos) {
            if (u.getRol() == rol) {
                porRol.add(u);
            }
        }
        return porRol;
    }

    // --- Métodos de Ayuda ---

    private String usuarioToString(Usuario usuario) {
        // Formato CSV: cedula,contrasena,rol,nombre,telefono,fecha_millis,correo
        return usuario.getCedula() + "," +
                usuario.getContrasena() + "," +
                usuario.getRol().name() + "," +
                usuario.getNombre() + "," +
                usuario.getTelefono() + "," +
                usuario.getFecha().getTimeInMillis() + "," +
                usuario.getCorreo();
    }

    private Usuario stringToUsuario(String linea) {
        String[] parts = linea.split(",");
        if (parts.length < 7) return null;

        try {
            String cedula = parts[0];
            String contrasena = parts[1];
            Rol rol = Rol.valueOf(parts[2]);
            String nombre = parts[3];
            String telefono = parts[4];
            long fechaMillis = Long.parseLong(parts[5]);
            String correo = parts[6];

            GregorianCalendar fecha = new GregorianCalendar();
            fecha.setTimeInMillis(fechaMillis);

            return new Usuario(cedula, contrasena, rol, nombre, telefono, fecha, correo);
        } catch (Exception e) {
            System.err.println("Error al parsear usuario desde texto: " + e.getMessage());
            return null;
        }
    }

    private void guardarTodos(List<Usuario> usuarios) {
        try (PrintWriter out = new PrintWriter(new FileWriter(filePath, false))) { // false para sobreescribir
            for (Usuario usuario : usuarios) {
                out.println(usuarioToString(usuario));
            }
        } catch (IOException e) {
            System.err.println("Error al reescribir usuarios.txt: " + e.getMessage());
        }
    }
}