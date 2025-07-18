package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Usuario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementación en memoria de la interfaz CarritoDAO.
 * Esta clase simula una base de datos guardando los carritos en una lista en memoria.
 * Es útil para desarrollo y pruebas. Los datos se pierden al finalizar la aplicación.
 *
 * @author [Tu Nombre]
 * @version 1.2
 * @since 2024-07-16
 */
public class CarritoDAOMemoria implements CarritoDAO {
    private List<Carrito> carritos;
    private int contCodigo = 1;

    /**
     * Constructor que inicializa la lista en memoria para almacenar los carritos.
     */
    public CarritoDAOMemoria() {
        carritos = new ArrayList<Carrito>();
    }

    /**
     * Guarda un nuevo carrito en la lista en memoria.
     * Asigna un código autoincremental y guarda una copia del objeto para
     * evitar modificaciones externas inesperadas.
     *
     * @param carrito El objeto Carrito a ser guardado.
     */
    @Override
    public void crear(Carrito carrito) {
        carrito.setCodigo(contCodigo++);
        Carrito copia = carrito.copiar();
        carritos.add(copia);
    }

    /**
     * Busca y devuelve un carrito por su código.
     *
     * @param codigo El código único del carrito a buscar.
     * @return El objeto Carrito encontrado, o null si no se encuentra ninguno con ese código.
     */
    @Override
    public Carrito buscarPorCodigo(int codigo) {
        for(Carrito carrito : carritos){
            if(carrito.getCodigo() == codigo){
                return carrito;
            }
        }
        return null;
    }

    /**
     * Actualiza un carrito existente en la lista.
     * Busca el carrito por su código y lo reemplaza con la nueva versión proporcionada.
     *
     * @param carrito El objeto Carrito con los datos actualizados.
     */
    @Override
    public void actualizar(Carrito carrito) {
        for (int i = 0; i < carritos.size(); i++) {
            if (carritos.get(i).getCodigo() == carrito.getCodigo()) {
                carritos.set(i, carrito);
                break;
            }
        }
    }

    /**
     * Elimina un carrito de la lista en memoria utilizando su código.
     * Usa un iterador para una eliminación segura durante el recorrido de la lista.
     *
     * @param codigo El código del carrito a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        Iterator<Carrito> iterator = carritos.iterator();
        while (iterator.hasNext()) {
            Carrito carrito = iterator.next();
            if (carrito.getCodigo() == codigo) {
                iterator.remove();
                break;
            }
        }
    }

    /**
     * Devuelve una copia de la lista con todos los carritos almacenados en memoria.
     *
     * @return Una lista de todos los objetos Carrito.
     */
    @Override
    public List<Carrito> listarTodos() {
        return new ArrayList<>(carritos);
    }

    /**
     * Busca y devuelve todos los carritos que pertenecen a un usuario específico.
     * La comparación se realiza utilizando la cédula del usuario.
     *
     * @param usuario El objeto Usuario por el cual se filtrarán los carritos.
     * @return Una lista de carritos pertenecientes al usuario. Puede estar vacía.
     */
    @Override
    public List<Carrito> buscarPorUsuario(Usuario usuario) {
        List<Carrito> resultado = new ArrayList<>();
        for (Carrito carrito : carritos) {
            if (carrito.getUsuario().getCedula().equals(usuario.getCedula())) {
                resultado.add(carrito);
            }
        }
        return resultado;
    }
}