package ec.edu.ups.modelo;

/**
 * La clase **Producto** representa un artículo con un código único, nombre y precio.
 */
public class Producto {
    private int codigo;
    private String nombre;
    private double precio;

    /**
     * Constructor para la clase Producto.
     *
     * @param codigo El código único del producto.
     * @param nombre El nombre del producto.
     * @param precio El precio del producto.
     */
    public Producto(int codigo, String nombre, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
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
     * Retorna una representación en cadena del objeto Producto.
     *
     * @return Una cadena que representa el producto con su código, nombre y precio.
     */
    @Override
    public String toString() {
        return "Producto{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                '}';
    }
}