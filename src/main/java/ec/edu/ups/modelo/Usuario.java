package ec.edu.ups.modelo;

import java.util.GregorianCalendar;

public class Usuario {
    private String usuario;
    private String contrasena;
    private Rol rol;
    private String nombre;
    private String telefono;
    private GregorianCalendar fecha;
    private String correo;

    public Usuario(String nombreUsuario, String contrasena, Rol rol, String nombre,
                   String telefono, GregorianCalendar fecha, String correo) {
        this.usuario = nombreUsuario;
        this.contrasena = contrasena;
        this.rol = rol;
        this.nombre = nombre;
        this.telefono = telefono;
        this.fecha = fecha;
        this.correo = correo;
    }
    public Usuario() {}

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public GregorianCalendar getFecha() {
        return fecha;
    }

    public void setFecha(GregorianCalendar fecha) {
        this.fecha = fecha;
    }
}
