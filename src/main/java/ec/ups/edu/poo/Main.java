package ec.ups.edu.poo;

public class Main {
    public static void main(String[] args) {

        CarritoDeCompras carrito = new CarritoDeCompras();
        Producto p1 = new Producto(002, "Mouse", 25.0);
        Producto p2 = new Producto(003, "Teclado", 50.0);

        carrito.agregarProducto(p1, 2);
        carrito.agregarProducto(p2, 1);

        carrito.mostrarCarrito();
    }
}