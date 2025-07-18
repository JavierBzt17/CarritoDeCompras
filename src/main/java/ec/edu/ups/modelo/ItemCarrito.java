package ec.edu.ups.modelo;

/**
 * La clase **ItemCarrito** representa un elemento dentro de un carrito de compras,
 * que incluye un producto y la cantidad de dicho producto.
 */
public class ItemCarrito {
    private Producto producto;
    private int cantidad;

    /**
     * Constructor para la clase ItemCarrito.
     *
     * @param producto El objeto **Producto** que se añade al carrito.
     * @param cantidad La cantidad de dicho producto.
     */
    public ItemCarrito(Producto producto, int cantidad) {
        this.cantidad = cantidad;
        this.producto = producto;
    }

    /**
     * Obtiene el producto asociado a este ítem del carrito.
     *
     * @return El objeto **Producto**.
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * Establece el producto para este ítem del carrito.
     *
     * @param producto El nuevo objeto **Producto** a establecer.
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    /**
     * Obtiene la cantidad de este producto en el carrito.
     *
     * @return La cantidad del producto.
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad de este producto en el carrito.
     *
     * @param cantidad La nueva cantidad a establecer.
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Retorna una representación en cadena del objeto **ItemCarrito**.
     *
     * @return Una cadena que representa el ítem del carrito, incluyendo el producto y la cantidad.
     */
    @Override
    public String toString() {
        return "ItemCarrito{" +
                "producto=" + producto +
                ", cantidad=" + cantidad +
                '}';
    }
}