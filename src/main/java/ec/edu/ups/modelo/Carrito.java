package ec.edu.ups.modelo;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Representa un carrito de compras en el sistema.
 * Contiene una lista de items, información del usuario asociado, una fecha de creación
 * y la lógica de negocio para calcular los totales.
 *
 */
public class Carrito {

    /**
     * Constante que define el valor del Impuesto al Valor Agregado (IVA).
     */
    private final double IVA = 0.12;
    private int codigo;
    private GregorianCalendar fechaCreacion;
    private List<ItemCarrito> items;
    private Usuario usuario;

    /**
     * Constructor por defecto. Inicializa la lista de items del carrito.
     */
    public Carrito() {
        items = new ArrayList<>();
    }

    /**
     * Obtiene el usuario propietario del carrito.
     *
     * @return El objeto Usuario asociado a este carrito.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Asigna un usuario al carrito.
     *
     * @param usuario El objeto Usuario a asociar con este carrito.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene el código único del carrito.
     *
     * @return El código del carrito.
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Establece el código único del carrito.
     *
     * @param codigo El nuevo código para el carrito.
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene la fecha de creación del carrito.
     *
     * @return Un objeto GregorianCalendar que representa la fecha y hora de creación.
     */
    public GregorianCalendar getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * Establece la fecha de creación del carrito.
     *
     * @param fechaCreacion La fecha y hora en que se creó el carrito.
     */
    public void setFechaCreacion(GregorianCalendar fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * Agrega un producto al carrito. Si el producto ya existe en el carrito,
     * actualiza su cantidad. Si no existe, crea un nuevo item.
     *
     * @param producto El Producto a añadir.
     * @param cantidad La cantidad del producto a añadir.
     */
    public void agregarProducto(Producto producto, int cantidad) {
        for (ItemCarrito item : items) {
            if (item.getProducto().getCodigo() == producto.getCodigo()) {
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }
        items.add(new ItemCarrito(producto, cantidad));
    }

    /**
     * Elimina completamente un producto del carrito, basado en su código.
     *
     * @param codigoProducto El código del producto a eliminar.
     */
    public void eliminarProducto(int codigoProducto) {
        Iterator<ItemCarrito> it = items.iterator();
        while (it.hasNext()) {
            if (it.next().getProducto().getCodigo() == codigoProducto) {
                it.remove();
                break;
            }
        }
    }

    /**
     * Elimina todos los items del carrito, dejándolo vacío.
     */
    public void vaciarCarrito() {
        items.clear();
    }

    /**
     * Devuelve la lista de todos los items contenidos en el carrito.
     *
     * @return Una lista de objetos ItemCarrito.
     */
    public List<ItemCarrito> obtenerItems() {
        return items;
    }

    /**
     * Verifica si el carrito de compras está vacío.
     *
     * @return true si el carrito no tiene items, false en caso contrario.
     */
    public boolean estaVacio() {
        return items.isEmpty();
    }

    /**
     * Calcula el subtotal de la compra (la suma de los precios de los items sin impuestos).
     *
     * @return El valor del subtotal como un double.
     */
    public double calcularSubtotal() {
        double subtotal = 0;
        for (ItemCarrito item : items) {
            subtotal += item.getProducto().getPrecio() * item.getCantidad();
        }
        return subtotal;
    }

    /**
     * Calcula el monto del IVA basado en el subtotal del carrito.
     *
     * @return El valor del IVA como un double.
     */
    public double calcularIVA() {
        return calcularSubtotal() * IVA;
    }

    /**
     * Calcula el costo total de la compra (subtotal + IVA).
     *
     * @return El valor total a pagar como un double.
     */
    public double calcularTotal() {
        return calcularSubtotal() + calcularIVA();
    }

    /**
     * Crea y devuelve una copia profunda de este objeto Carrito.
     * La copia es un objeto independiente con los mismos datos, útil para
     * mantener la integridad de los datos al guardar en la capa de persistencia.
     *
     * @return Un nuevo objeto Carrito que es una copia exacta del actual.
     */
    public Carrito copiar() {
        Carrito copia = new Carrito();
        copia.setFechaCreacion(this.fechaCreacion);
        copia.setUsuario(this.usuario);
        copia.setCodigo(this.codigo);

        for (ItemCarrito item : this.items) {
            Producto producto = item.getProducto();
            int cantidad = item.getCantidad();
            copia.agregarProducto(producto, cantidad);
        }
        return copia;
    }
}