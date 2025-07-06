package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CuestionarioDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.util.*;

public class UsuarioDAOMemoria implements UsuarioDAO {

    private List<Usuario> usuarios;
    private CuestionarioDAO cuestionarioDAO;

    public UsuarioDAOMemoria() {
        this.usuarios = new ArrayList<>();
        this.cuestionarioDAO = cuestionarioDAO;

        Usuario admin = new Usuario(
                "administrador01",
                "1881",
                Rol.ADMINISTRADOR,
                "Administrador",
                "0959047144",
                new GregorianCalendar(2000, Calendar.JULY, 12),
                "admn01gmail.com"
        );

        Usuario user = new Usuario(
                "useruario01",
                "2006",
                Rol.USUARIO,
                "Usuario",
                "0989453263",
                new GregorianCalendar(1995, Calendar.JUNE, 15),
                "usr01@gmail.com"
        );
        crear(admin);
        crear(user);
    }


    @Override
    public Usuario autenticar(String username, String contrasena) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsuario().equals(username) && usuario.getContrasena().equals(contrasena)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void crear(Usuario usuario) {
        usuarios.add(usuario);
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsuario().equals(username)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void actualizar(Usuario usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getUsuario().equals(usuario.getUsuario())) {
                usuarios.set(i, usuario);
                break;
            }
        }
    }

    @Override
    public void eliminar(String username) {
        Iterator<Usuario> iterator = usuarios.iterator();
        while (iterator.hasNext()) {
            Usuario usuario = iterator.next();
            if (usuario.getUsuario().equals(username)) {
                iterator.remove();
            }
        }
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarios;
    }

    @Override
    public List<Usuario> listarPorRol(Rol rol) {
        List<Usuario> usuariosEncontrados = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario.getRol().equals(rol)) {
                usuariosEncontrados.add(usuario);
            }
        }
        return usuariosEncontrados;
    }
}
