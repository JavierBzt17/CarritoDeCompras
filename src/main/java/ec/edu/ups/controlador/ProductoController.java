package ec.edu.ups.controlador;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.Formateador;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.carrito.CarritoAnadirView;
import ec.edu.ups.vista.producto.ProductoAnadirView;
import ec.edu.ups.vista.producto.ProductoEditarView;
import ec.edu.ups.vista.producto.ProductoEliminarView;
import ec.edu.ups.vista.producto.ProductoListaView;

import java.awt.event.ActionListener;
import java.util.List;

/**
 * Controlador que gestiona toda la lógica de negocio para las operaciones CRUD de Productos.
 * Actúa como intermediario entre las vistas de producto (Añadir, Listar, Editar, Eliminar)
 * y la capa de acceso a datos (ProductoDAO). También interactúa con la vista de añadir
 * al carrito para la búsqueda de productos.
 *
 */
public class ProductoController {

    private final ProductoAnadirView productoAnadirView;
    private final ProductoListaView productoListaView;
    private final ProductoDAO productoDAO;
    private final ProductoEditarView productoEditarView;
    private final ProductoEliminarView productoEliminarView;
    private final CarritoAnadirView carritoAnadirView;
    private final MensajeInternacionalizacionHandler mi;

    /**
     * Constructor del ProductoController.
     * Inyecta las dependencias necesarias (DAOs y Vistas) y configura los eventos.
     *
     * @param productoDAO Objeto de acceso a datos para productos.
     * @param productoAnadirView Vista para crear nuevos productos.
     * @param productoListaView Vista para listar y buscar productos.
     * @param productoGestionView Vista para editar productos existentes.
     * @param productoEliminarView Vista para eliminar productos.
     * @param carritoAnadirView Vista para añadir productos a un carrito, que requiere buscar productos.
     * @param mi Manejador de internacionalización para los mensajes.
     */
    public ProductoController(ProductoDAO productoDAO,
                              ProductoAnadirView productoAnadirView,
                              ProductoListaView productoListaView,
                              ProductoEditarView productoGestionView, ProductoEliminarView productoEliminarView,
                              CarritoAnadirView carritoAnadirView, MensajeInternacionalizacionHandler mi) {
        this.productoDAO = productoDAO;
        this.productoAnadirView = productoAnadirView;
        this.productoListaView = productoListaView;
        this.productoEditarView = productoGestionView;
        this.productoEliminarView = productoEliminarView;
        this.carritoAnadirView = carritoAnadirView;
        this.mi = mi;
        configurarEventos();
    }

    /**
     * Configura y asigna los ActionListeners a los botones de todas las vistas
     * gestionadas por este controlador.
     */
    private void configurarEventos() {
        productoAnadirView.getBtnAceptar().addActionListener(e -> guardarProducto());
        productoListaView.getBtnBuscar().addActionListener(e -> buscarProducto());
        productoListaView.getBtnListar().addActionListener(e -> listarProductos());
        productoEditarView.getBtnBuscar().addActionListener(e -> buscarProductoEdicion());
        productoEditarView.getBtnActualizar().addActionListener(e -> actualizarProducto());
        productoEliminarView.getBtnEliminar().addActionListener(e -> eliminarProducto());
        productoEliminarView.getBtnBuscar().addActionListener(e -> buscarProductoEliminar());
        carritoAnadirView.getBtnBuscar().addActionListener(e -> buscarProductoCarrito());
    }

    /**
     * Valida los datos de entrada y guarda un nuevo producto en la capa de persistencia.
     * Muestra mensajes de error si la validación falla o de éxito si la operación se completa.
     */
    private void guardarProducto() {
        String codigoTexto = productoAnadirView.getTxtCodigo().getText().trim();
        String nombre = productoAnadirView.getTxtNombre().getText().trim();
        String precioTexto = productoAnadirView.getTxtPrecio().getText().trim();

        if (codigoTexto.isEmpty() || nombre.isEmpty() || precioTexto.isEmpty()) {
            productoAnadirView.mostrarMensaje(mi.get("producto.mensaje.campos.vacios"));
            return;
        }
        if (!codigoTexto.matches("[1-9]\\d*|0")) {
            productoAnadirView.mostrarMensaje(mi.get("producto.mensaje.codigo.invalido"));
            return;
        }
        int codigo = Integer.parseInt(codigoTexto);
        if (!precioTexto.matches("\\d+(\\.\\d+)?")) {
            productoAnadirView.mostrarMensaje(mi.get("producto.mensaje.precio.invalido"));
            return;
        }
        double precio = Double.parseDouble(precioTexto);
        if (productoDAO.buscarPorCodigo(codigo) != null) {
            productoAnadirView.mostrarMensaje(mi.get("producto.mensaje.error.codigo.existe"));
            return;
        }
        productoDAO.crear(new Producto(codigo, nombre, precio));
        productoAnadirView.mostrarMensaje(mi.get("producto.mensaje.guardado.correctamente"));
        productoAnadirView.limpiarCampos();
        productoAnadirView.mostrarProductos(productoDAO.listarTodos());
    }

    /**
     * Busca productos cuyo nombre contenga el texto proporcionado y actualiza
     * la tabla en la vista de listado con los resultados.
     */
    private void buscarProducto() {
        String nombre = productoListaView.getTxtBuscar().getText();
        List<Producto> productosEncontrados = productoDAO.buscarPorNombre(nombre);
        productoListaView.cargarDatos(productosEncontrados);
    }

    /**
     * Carga todos los productos existentes desde la capa de persistencia y los
     * muestra en la tabla de la vista de listado.
     */
    private void listarProductos() {
        List<Producto> productos = productoDAO.listarTodos();
        productoListaView.cargarDatos(productos);
    }

    /**
     * Valida los datos del formulario de edición y actualiza un producto existente
     * en la capa de persistencia, previa confirmación del usuario.
     */
    private void actualizarProducto() {
        String txtCod = productoEditarView.getTxtCodigo().getText().trim();
        String nombre = productoEditarView.getTxtNombre().getText().trim();
        String txtPrecio = productoEditarView.getTxtPrecio().getText().trim();

        if (txtCod.isEmpty() || nombre.isEmpty() || txtPrecio.isEmpty()) {
            productoEditarView.mostrarMensaje(mi.get("producto.mensaje.campos.vacios"));
            return;
        }
        if (!txtCod.matches("[1-9]\\d*|0")) {
            productoEditarView.mostrarMensaje(mi.get("producto.mensaje.codigo.invalido"));
            return;
        }
        int codigo = Integer.parseInt(txtCod);
        if (!txtPrecio.matches("\\d+(\\.\\d+)?")) {
            productoEditarView.mostrarMensaje(mi.get("producto.mensaje.precio.invalido"));
            return;
        }
        double precio = Double.parseDouble(txtPrecio);
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        if (producto == null) {
            productoEditarView.mostrarMensaje(mi.get("producto.mensaje.no.encontrado"));
            return;
        }
        boolean confirmado = productoEditarView.mostrarMensajePregunta(mi.get("producto.mensaje.actualizar.pregunta"));
        if (!confirmado) {
            productoEliminarView.mostrarMensaje(mi.get("producto.mensaje.actualizacion.cancelada"));
            return;
        }
        producto.setNombre(nombre);
        producto.setPrecio(precio);
        productoDAO.actualizar(producto);
        productoEditarView.mostrarMensaje(mi.get("producto.mensaje.actualizado.correctamente"));
    }

    /**
     * Elimina un producto de la capa de persistencia, previa confirmación del usuario.
     */
    private void eliminarProducto() {
        String textCodigo = productoEliminarView.getTxtCodigo().getText().trim();
        if (textCodigo.isEmpty()) {
            productoEliminarView.mostrarMensaje(mi.get("producto.mensaje.ingresar.codigo"));
            return;
        }
        if (!textCodigo.matches("[1-9]\\d*|0")) {
            productoEliminarView.mostrarMensaje(mi.get("producto.mensaje.codigo.invalido"));
            return;
        }
        int codigo = Integer.parseInt(textCodigo);
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        if (producto == null) {
            productoEliminarView.mostrarMensaje(mi.get("producto.mensaje.no.encontrado"));
            return;
        }
        boolean confirmado = productoEliminarView.mostrarMensajePregunta(mi.get("producto.mensaje.eliminar.pregunta"));
        if (!confirmado) {
            productoEliminarView.mostrarMensaje(mi.get("producto.mensaje.eliminacion.cancelada"));
            return;
        }
        productoDAO.eliminar(codigo);
        productoEliminarView.mostrarMensaje(mi.get("producto.mensaje.eliminado.correctamente"));
        productoEliminarView.limpiarCampos();
    }

    /**
     * Busca un producto por su código y, si lo encuentra, muestra sus datos en los
     * campos de la vista de eliminación para confirmación visual.
     */
    private void buscarProductoEliminar() {
        String txtCod = productoEliminarView.getTxtCodigo().getText().trim();
        if (txtCod.isEmpty()) {
            productoEliminarView.mostrarMensaje(mi.get("producto.mensaje.error.ingresar.codigo"));
            return;
        }
        if (!txtCod.matches("[1-9]\\d*|0")) {
            productoEliminarView.mostrarMensaje(mi.get("producto.mensaje.codigo.invalido"));
            return;
        }
        int codigo = Integer.parseInt(txtCod);
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        if (producto != null) {
            productoEliminarView.getTxtNombre().setText(producto.getNombre());
            productoEliminarView.getTxtPrecio().setText(Formateador.formatearMoneda(producto.getPrecio(), mi.getLocale()));
        } else {
            productoEliminarView.mostrarMensaje(mi.get("producto.mensaje.no.encontrado"));
            productoEliminarView.limpiarCampos();
        }
    }

    /**
     * Busca un producto por su código y, si lo encuentra, muestra sus datos en los
     * campos de la vista de edición, listos para ser modificados.
     */
    private void buscarProductoEdicion() {
        String txtCod = productoEditarView.getTxtCodigo().getText().trim();
        if (txtCod.isEmpty()) {
            productoEditarView.mostrarMensaje(mi.get("producto.mensaje.error.ingresar.codigo"));
            return;
        }
        if (!txtCod.matches("[1-9]\\d*|0")) {
            productoEditarView.mostrarMensaje(mi.get("producto.mensaje.codigo.invalido"));
            return;
        }
        int codigo = Integer.parseInt(txtCod);
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        if (producto != null) {
            productoEditarView.getTxtNombre().setText(producto.getNombre());
            productoEditarView.getTxtPrecio().setText(Formateador.formatearMoneda(producto.getPrecio(), mi.getLocale()));
        } else {
            productoEditarView.mostrarMensaje(mi.get("producto.mensaje.no.encontrado"));
            productoEditarView.limpiarCampos();
        }
    }

    /**
     * Busca un producto por código desde la vista de añadir al carrito y muestra
     * su nombre y precio para que el usuario verifique el item antes de añadirlo.
     */
    private void buscarProductoCarrito() {
        String txtCod = carritoAnadirView.getTxtCodigo().getText().trim();
        if (txtCod.isEmpty()) {
            carritoAnadirView.mostrarMensaje(mi.get("producto.mensaje.error.ingresar.codigo"));
            return;
        }
        if (!txtCod.matches("[1-9]\\d*|0")) {
            carritoAnadirView.mostrarMensaje(mi.get("producto.mensaje.codigo.invalido"));
            return;
        }
        int codigo = Integer.parseInt(txtCod);
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        if (producto != null) {
            carritoAnadirView.getTxtNombre().setText(producto.getNombre());
            carritoAnadirView.getTxtPrecio().setText(Formateador.formatearMoneda(producto.getPrecio(), mi.getLocale()));
        } else {
            carritoAnadirView.mostrarMensaje(mi.get("producto.mensaje.no.encontrado"));
            carritoAnadirView.limpiarCampos();
        }
    }

    /**
     * Actualiza el idioma de todas las vistas de producto gestionadas por este controlador.
     * Este método es invocado externamente cuando se cambia el idioma de la aplicación.
     */
    public void actualizarIdiomaEnVistas() {
        productoAnadirView.cambiarIdioma();
        productoEditarView.cambiarIdioma();
        productoEliminarView.cambiarIdioma();
        productoListaView.cambiarIdioma();
    }
}