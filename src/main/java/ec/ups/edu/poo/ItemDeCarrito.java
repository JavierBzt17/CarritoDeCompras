package ec.ups.edu.poo;

public class ItemDeCarrito {
    private Producto producto;
    private int cantidad;

    public ItemDeCarrito(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public double calcularSubtotal(){
        return producto.getPrecio()*cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }
}
