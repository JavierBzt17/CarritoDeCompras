package ec.edu.ups.dao;

import ec.edu.ups.modelo.CarritoDeCompras;

import java.util.List;

public interface CarritoDAO {

    void crear(CarritoDeCompras carrito);

    CarritoDeCompras buscarPorCodigo(int codigo);

    void actualizar(CarritoDeCompras carrito);

    void eliminar(int codigo);

    List<CarritoDeCompras> listarTodos();

}
