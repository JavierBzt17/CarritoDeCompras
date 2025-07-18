package ec.edu.ups.dao;

import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import java.io.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer; // For parsing lines

public class UsuarioDAOTexto implements UsuarioDAO {
    private String rutaArchivo = "usuarios.txt";

    public UsuarioDAOTexto() {
        try {
            File file = new File(rutaArchivo);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Error al crear el archivo de usuarios de texto: " + e.getMessage());
        }
    }

    private String usuarioAString(Usuario usuario) {
        String fechaStr = "";
        if (usuario.getFecha() != null) {
            fechaStr = String.format("%d-%d-%d",
                    usuario.getFecha().get(GregorianCalendar.YEAR),
                    usuario.getFecha().get(GregorianCalendar.MONTH) + 1, // Month is 0-indexed
                    usuario.getFecha().get(GregorianCalendar.DAY_OF_MONTH));
        }
        return String.join("|",
                usuario.getCedula(),
                usuario.getContrasena(),
                usuario.getRol().name(),
                usuario.getNombre(),
                usuario.getTelefono(),
                fechaStr,
                usuario.getCorreo());
    }

    private Usuario stringAUsuario(String linea) {
        StringTokenizer tokenizer = new StringTokenizer(linea, "|");
        if (tokenizer.countTokens() < 7) {
            return null;
        }
        try {
            String cedula = tokenizer.nextToken();
            String contrasena = tokenizer.nextToken();
            Rol rol = Rol.valueOf(tokenizer.nextToken());
            String nombre = tokenizer.nextToken();
            String telefono = tokenizer.nextToken();
            String fechaStr = tokenizer.nextToken();
            String correo = tokenizer.nextToken();

            GregorianCalendar fecha = null;
            if (!fechaStr.isEmpty()) {
                String[] fechaParts = fechaStr.split("-");
                if (fechaParts.length == 3) {
                    int year = Integer.parseInt(fechaParts[0]);
                    int month = Integer.parseInt(fechaParts[1]) - 1;
                    int day = Integer.parseInt(fechaParts[2]);
                    fecha = new GregorianCalendar(year, month, day);
                }
            }
            return new Usuario(cedula, contrasena, rol, nombre, telefono, fecha, correo);
        } catch (Exception e) {
            System.err.println("Error al parsear línea a Usuario: " + linea + " - " + e.getMessage());
            return null;
        }
    }

    @Override
    public Usuario autenticar(String cedula, String contrasena) {
        List<Usuario> usuarios = listarTodos();
        for (Usuario u : usuarios) {
            if (u.getCedula().equals(cedula) && u.getContrasena().equals(contrasena)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public void crear(Usuario usuario) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(rutaArchivo, true))) { // true for append mode
            writer.println(usuarioAString(usuario));
        } catch (IOException e) {
            System.err.println("Error al crear usuario en archivo de texto: " + e.getMessage());
        }
    }

    @Override
    public Usuario buscarPorCedula(String cedula) {
        try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Usuario u = stringAUsuario(line);
                if (u != null && u.getCedula().equals(cedula)) {
                    return u;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al buscar usuario por cédula en archivo de texto: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void actualizar(Usuario usuario) {
        List<Usuario> usuarios = listarTodos();
        try (PrintWriter writer = new PrintWriter(new FileWriter(rutaArchivo))) { // Overwrite file
            for (Usuario u : usuarios) {
                if (u.getCedula().equals(usuario.getCedula())) {
                    writer.println(usuarioAString(usuario)); // Write updated user
                } else {
                    writer.println(usuarioAString(u)); // Write existing user
                }
            }
        } catch (IOException e) {
            System.err.println("Error al actualizar usuario en archivo de texto: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(String cedula) {
        List<Usuario> usuarios = listarTodos();
        try (PrintWriter writer = new PrintWriter(new FileWriter(rutaArchivo))) { // Overwrite file
            for (Usuario u : usuarios) {
                if (!u.getCedula().equals(cedula)) {
                    writer.println(usuarioAString(u)); // Write all users except the one to be deleted
                }
            }
        } catch (IOException e) {
            System.err.println("Error al eliminar usuario en archivo de texto: " + e.getMessage());
        }
    }

    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Usuario u = stringAUsuario(line);
                if (u != null) {
                    usuarios.add(u);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al listar todos los usuarios del archivo de texto: " + e.getMessage());
        }
        return usuarios;
    }

    @Override
    public List<Usuario> listarPorRol(Rol rol) {
        List<Usuario> usuariosPorRol = new ArrayList<>();
        List<Usuario> todosLosUsuarios = listarTodos();
        for (Usuario u : todosLosUsuarios) {
            if (u.getRol() == rol) {
                usuariosPorRol.add(u);
            }
        }
        return usuariosPorRol;
    }
}