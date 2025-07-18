package ec.edu.ups.modelo;

import java.util.GregorianCalendar;

/**
 * La clase **Usuario** representa a un usuario dentro del sistema, almacenando
 * información personal y de autenticación.
 */
public class Usuario {
    private String cedula;
    private String contrasena;
    private Rol rol;
    private String nombre;
    private String telefono;
    private GregorianCalendar fecha;
    private String correo;

    /**
     * Constructor completo para la clase Usuario.
     *
     * @param cedula La cédula del usuario, utilizada como identificador principal.
     * @param contrasena La contraseña del usuario para autenticación.
     * @param rol El rol del usuario en el sistema (ADMINISTRADOR, USUARIO).
     * @param nombre El nombre completo del usuario.
     * @param telefono El número de teléfono del usuario.
     * @param fecha La fecha de algún evento relevante, como la fecha de registro o nacimiento.
     * @param correo La dirección de correo electrónico del usuario.
     */
    public Usuario(String cedula, String contrasena, Rol rol, String nombre,
                   String telefono, GregorianCalendar fecha, String correo) {
        this.cedula = cedula;
        this.contrasena = contrasena;
        this.rol = rol;
        this.nombre = nombre;
        this.telefono = telefono;
        this.fecha = fecha;
        this.correo = correo;
    }

    /**
     * Constructor vacío para la clase Usuario.
     */
    public Usuario() {}

    /**
     * Obtiene la cédula del usuario.
     *
     * @return La cédula del usuario.
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * Establece la cédula del usuario.
     *
     * @param cedula La nueva cédula del usuario.
     */
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    /**
     * Obtiene la dirección de correo electrónico del usuario.
     *
     * @return El correo electrónico del usuario.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece la dirección de correo electrónico del usuario.
     *
     * @param correo La nueva dirección de correo electrónico del usuario.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return La contraseña del usuario.
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param contrasena La nueva contraseña del usuario.
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * Obtiene el rol del usuario.
     *
     * @return El rol del usuario.
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * Establece el rol del usuario.
     *
     * @param rol El nuevo rol del usuario.
     */
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    /**
     * Obtiene el nombre completo del usuario.
     *
     * @return El nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre completo del usuario.
     *
     * @param nombre El nuevo nombre del usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el número de teléfono del usuario.
     *
     * @return El número de teléfono del usuario.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el número de teléfono del usuario.
     *
     * @param telefono El nuevo número de teléfono del usuario.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene la fecha asociada al usuario.
     *
     * @return La fecha asociada al usuario.
     */
    public GregorianCalendar getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha asociada al usuario.
     *
     * @param fecha La nueva fecha para el usuario.
     */
    public void setFecha(GregorianCalendar fecha) {
        this.fecha = fecha;
    }
}