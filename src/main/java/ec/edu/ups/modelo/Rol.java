package ec.edu.ups.modelo;

import java.io.Serializable;

/**
 * La enumeración **Rol** define los diferentes tipos de roles que un usuario puede tener
 * dentro del sistema.
 */
public enum Rol implements Serializable {
    /**
     * Representa el rol de administrador, con privilegios elevados.
     */
    ADMINISTRADOR,
    /**
     * Representa un rol de usuario estándar, con privilegios básicos.
     */
    USUARIO
}