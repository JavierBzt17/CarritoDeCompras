package ec.edu.ups.poo.modelo;

import java.util.ArrayList;
import java.util.List;

public class CarritoDeCompras {
    private List<ItemDeCarrito> items;

    public CarritoDeCompras() {
        items = new ArrayList<>();
    }

    public void agregarProducto(Producto producto, int cantidad) {
        items.add(new ItemDeCarrito(producto, cantidad));
    }


    public double calcularTotal() {
        double total = 0;
        for (ItemDeCarrito item : items) {
            total += item.calcularSubtotal();
        }
        return total;
    }

    public void mostrarCarrito() {
        for (ItemDeCarrito item : items) {
            System.out.println("Producto: " + item.getProducto().getNombre() + "\n" + " Cantidad: " + item.getCantidad() + "\n" + " Precio: $" + item.calcularSubtotal() + "\n");
        }
        System.out.println("Total: $" + calcularTotal());
        }
}