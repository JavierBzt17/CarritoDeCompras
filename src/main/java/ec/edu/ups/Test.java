package ec.edu.ups;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;

public class Test {
    public static void main(String[] args) {

        Carrito carrito = new Carrito();

        Producto p1 = new Producto(1, "Supan", 3.50);
        Producto p2 = new Producto(2, "Mantequilla",1.75);

        carrito.agregarProducto(p1, 6);  // 6 x 3.50 $ = 21 $
        carrito.agregarProducto(p2, 4);  // 4 x 1.75 $ = 7 $

        System.out.println("Contenido del carrito:");
        for (ItemCarrito prod : carrito.obtenerItems()) {
            System.out.println("- " + prod);
        }


        double total = carrito.calcularTotal();
        System.out.println("Total:" + total + "$");
        // 21 + 7 = 28 $

        System.out.println("¿El carrito esta vacío? " + carrito.estaVacio());

        carrito.eliminarProducto(1);
        carrito.vaciarCarrito();

        System.out.println("El carrito a sido vaciado. ¿Vacío ahora? " + carrito.estaVacio());
    }
}
