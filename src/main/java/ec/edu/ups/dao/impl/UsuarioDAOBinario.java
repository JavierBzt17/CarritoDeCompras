package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioDAOBinario implements UsuarioDAO {
    private final String filePath;

    /**
     * Constructor que recibe la ruta base desde la FabricaDAO.
     */
    public UsuarioDAOBinario(String basePath) {
        this.filePath = Paths.get(basePath, "usuarios.dat").toString();
    }

    @Override
    public void crear(Usuario usuario) {
        List<Usuario> usuarios = listarTodos();
        usuarios.add(usuario);
        guardarTodos(usuarios);
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
        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<Usuario>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al leer usuarios.dat: " + e.getMessage());
            return new ArrayList<>();
        }
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
        return listarTodos().stream()
                .filter(u -> u.getRol() == rol)
                .collect(Collectors.toList());
    }

    private void guardarTodos(List<Usuario> usuarios) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            System.err.println("Error al guardar usuarios.dat: " + e.getMessage());
        }
    }
}