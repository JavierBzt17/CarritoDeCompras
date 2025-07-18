package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Calendar;

/**
 * Implementación en memoria de la interfaz UsuarioDAO.
 * Esta clase simula una base de datos para los usuarios, almacenándolos
 * en una lista en memoria. Es ideal para desarrollo y pruebas, ya que los
 * datos se pierden al cerrar la aplicación.
 *
 */
public class UsuarioDAOMemoria implements UsuarioDAO {

    private final List<Usuario> usuarios;

    /**
     * Constructor que inicializa la lista en memoria y la precarga con
     * un usuario administrador y un usuario normal para pruebas.
     */
    public UsuarioDAOMemoria() {
        this.usuarios = new ArrayList<>();

        Usuario admin = new Usuario(
                "0101234567",
                "1881",
                Rol.ADMINISTRADOR,
                "Administrador Principal",
                "0959047144",
                new GregorianCalendar(2000, Calendar.JULY, 12),
                "admn01@gmail.com"
        );

        Usuario user = new Usuario(
                "0109876543",
                "2006",
                Rol.USUARIO,
                "Usuario de Prueba",
                "0989453263",
                new GregorianCalendar(1995, Calendar.JUNE, 15),
                "usr01@gmail.com"
        );
        crear(admin);
        crear(user);
    }

    /**
     * Autentica a un usuario comparando la cédula y contraseña proporcionadas.
     *
     * @param cedula La cédula del usuario que intenta iniciar sesión.
     * @param contrasena La contraseña proporcionada para la autenticación.
     * @return El objeto Usuario si las credenciales son correctas, de lo contrario, null.
     */
    @Override
    public Usuario autenticar(String cedula, String contrasena) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCedula().equals(cedula) && usuario.getContrasena().equals(contrasena)) {
                return usuario;
            }
        }
        return null;
    }

    /**
     * Añade un nuevo usuario a la lista en memoria, siempre y cuando no exista
     * otro usuario con la misma cédula.
     *
     * @param usuario El objeto Usuario a ser creado.
     */
    @Override
    public void crear(Usuario usuario) {
        if (buscarPorCedula(usuario.getCedula()) == null) {
            usuarios.add(usuario);
        }
    }

    /**
     * Busca y devuelve un usuario por su número de cédula.
     *
     * @param cedula La cédula del usuario a buscar.
     * @return El objeto Usuario encontrado, o null si no existe.
     */
    @Override
    public Usuario buscarPorCedula(String cedula) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCedula().equals(cedula)) {
                return usuario;
            }
        }
        return null;
    }

    /**
     * Actualiza un usuario existente en la lista.
     * Busca al usuario por su cédula y lo reemplaza con la nueva versión proporcionada.
     *
     * @param usuarioActualizado El objeto Usuario con los datos actualizados.
     */
    @Override
    public void actualizar(Usuario usuarioActualizado) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getCedula().equals(usuarioActualizado.getCedula())) {
                usuarios.set(i, usuarioActualizado);
                break;
            }
        }
    }

    /**
     * Elimina un usuario de la lista en memoria utilizando su cédula.
     *
     * @param cedula La cédula del usuario a eliminar.
     */
    @Override
    public void eliminar(String cedula) {
        usuarios.removeIf(usuario -> usuario.getCedula().equals(cedula));
    }

    /**
     * Devuelve una copia de la lista con todos los usuarios almacenados en memoria.
     *
     * @return Una lista de todos los objetos Usuario.
     */
    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios);
    }

    /**
     * Devuelve una lista de usuarios que pertenecen a un rol específico.
     *
     * @param rol El rol (ADMINISTRADOR o USUARIO) por el cual filtrar.
     * @return Una lista de usuarios que coinciden con el rol especificado.
     */
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