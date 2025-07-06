package ec.edu.ups.dao;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.util.List;

public interface UsuarioDAO {
    Usuario autenticar(String user, String contrasena);

    void crear(Usuario usuario);

    Usuario buscarPorUsername(String user);

    void actualizar(Usuario usuario);

    void eliminar(String user);

    List<Usuario> listarTodos();

    List<Usuario> listarPorRol(Rol rol);
}
