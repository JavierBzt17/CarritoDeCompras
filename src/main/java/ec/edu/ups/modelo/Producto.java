package ec.edu.ups.modelo;

import java.io.Serializable;

/**
 * La clase **Producto** representa un artículo con un código único, nombre, precio y stock.
 * (Esta clase ya está correcta y completa. No necesita cambios.)
 */
public class Producto implements Serializable {

    /**
     * UID de versión para la serialización.
     */
    private static final long serialVersionUID = 1L;

    private int codigo;
    private String nombre;
    private double precio;
    private int stock;

    /**
     * Constructor para la clase Producto.
     *
     * @param codigo El código único del producto.
     * @param nombre El nombre del producto.
     * @param precio El precio del producto.
     * @param stock La cantidad disponible del producto.
     */
    public Producto(int codigo, String nombre, double precio, int stock) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    /**
     * Obtiene el código del producto.
     *
     * @return El código del producto.
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Establece el código del producto.
     *
     * @param codigo El nuevo código del producto.
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene el nombre del producto.
     *
     * @return El nombre del producto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del producto.
     *
     * @param nombre El nuevo nombre del producto.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el precio del producto.
     *
     * @return El precio del producto.
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del producto.
     *
     * @param precio El nuevo precio del producto.
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Obtiene el stock disponible del producto.
     *
     * @return La cantidad en stock.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Establece el stock disponible del producto.
     *
     * @param stock La nueva cantidad en stock.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Retorna una representación en cadena del objeto Producto.
     *
     * @return Una cadena que representa el producto con su código, nombre, precio y stock.
     */
    @Override
    public String toString() {
        return "Producto{" +
                "codigo=" + codigo +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                '}';
    }
}