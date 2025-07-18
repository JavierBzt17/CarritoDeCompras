package ec.edu.ups.dao;

import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.util.List;

/**
 * Interfaz que define las operaciones de persistencia de datos para la entidad Usuario.
 * Establece un contrato para cualquier clase DAO que gestione los usuarios,
 * garantizando que se implementen los métodos estándar de un CRUD (Crear, Leer, Actualizar, Eliminar)
 * y otras operaciones como la autenticación y el filtrado por rol.
 *
 */
public interface UsuarioDAO {

    /**
     * Autentica a un usuario comparando la cédula y contraseña proporcionadas.
     *
     * @param cedula La cédula del usuario que intenta iniciar sesión.
     * @param contrasena La contraseña proporcionada para la autenticación.
     * @return El objeto Usuario si las credenciales son correctas, de lo contrario, null.
     */
    Usuario autenticar(String cedula, String contrasena);

    /**
     * Persiste un nuevo objeto Usuario en el sistema de almacenamiento.
     *
     * @param usuario El objeto Usuario a ser creado y guardado.
     */
    void crear(Usuario usuario);

    /**
     * Busca y devuelve un Usuario específico a partir de su cédula.
     *
     * @param cedula La cédula del usuario a buscar.
     * @return El objeto Usuario encontrado, o null si no se encuentra ninguno con esa cédula.
     */
    Usuario buscarPorCedula(String cedula);

    /**
     * Actualiza los datos de un usuario existente en el sistema de almacenamiento.
     *
     * @param usuario El objeto Usuario con la información actualizada.
     */
    void actualizar(Usuario usuario);

    /**
     * Elimina un usuario del sistema de almacenamiento utilizando su cédula.
     *
     * @param cedula La cédula del usuario a eliminar.
     */
    void eliminar(String cedula);

    /**
     * Devuelve una lista con todos los usuarios registrados en el sistema.
     *
     * @return Una lista de todos los objetos Usuario.
     */
    List<Usuario> listarTodos();

    /**
     * Devuelve una lista de usuarios que pertenecen a un rol específico.
     *
     * @param rol El rol (ADMINISTRADOR o USUARIO) por el cual filtrar.
     * @return Una lista de usuarios que coinciden con el rol especificado.
     */
    List<Usuario> listarPorRol(Rol rol);
}