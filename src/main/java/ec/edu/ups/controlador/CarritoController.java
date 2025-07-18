package ec.edu.ups.controlador;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.*;
import ec.edu.ups.util.Formateador;
import ec.edu.ups.vista.carrito.*;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Controlador que gestiona la lógica de negocio para las operaciones del carrito de compras.
 * Conecta las vistas relacionadas con el carrito (Añadir, Listar, Modificar, Eliminar)
 * con los DAOs para el acceso a datos.
 *
 */
public class CarritoController {
    private final CarritoAnadirView carritoAnadirView;
    private final ProductoDAO productoDAO;
    private Carrito carrito;
    private final CarritoDAO carritoDAO;
    private final Usuario usuario;
    private final CarritoListaView carritoListaView;
    private CarritoDetalleView carritoDetalleView;
    private final CarritoModificarView carritoModificarView;
    private final CarritoEliminarView carritoEliminarView;
    private final MensajeInternacionalizacionHandler mi;

    /**
     * Constructor del CarritoController.
     * Inicializa las dependencias (vistas, DAOs) y configura los eventos iniciales.
     *
     * @param carritoView Vista para añadir productos al carrito.
     * @param productoDAO Objeto de acceso a datos para productos.
     * @param carritoDAO Objeto de acceso a datos para carritos.
     * @param usuario El usuario autenticado que está realizando la compra.
     * @param carritoListaView Vista para listar los carritos existentes.
     * @param carritoModificarView Vista para modificar un carrito existente.
     * @param carritoEliminarView Vista para eliminar un carrito.
     * @param mi Manejador de internacionalización para los mensajes.
     */
    public CarritoController(CarritoAnadirView carritoView, ProductoDAO productoDAO,
                             CarritoDAO carritoDAO, Usuario usuario, CarritoListaView carritoListaView, CarritoModificarView carritoModificarView, CarritoEliminarView carritoEliminarView,
                             MensajeInternacionalizacionHandler mi) {
        this.carritoAnadirView = carritoView;
        this.productoDAO = productoDAO;
        this.carritoDAO = carritoDAO;
        this.usuario = usuario;
        this.carritoListaView = carritoListaView;
        this.carritoModificarView = carritoModificarView;
        this.carritoEliminarView = carritoEliminarView;
        this.mi = mi;
        this.carrito = new Carrito();
        carrito.setCodigo(carrito.hashCode());
        configurarEventosEnVistas();
    }

    /**
     * Configura y asigna los ActionListeners a los botones de todas las vistas
     * gestionadas por este controlador.
     */
    private void configurarEventosEnVistas() {
        carritoAnadirView.getBtnAnadir().addActionListener(e -> anadirProductoACarrito());
        carritoAnadirView.getBtnGuardar().addActionListener(e -> aceptarCarrito());
        carritoAnadirView.getBtnLimpiar().addActionListener(e -> limpiarFormulario());
        carritoAnadirView.getBtnBorrar().addActionListener(e -> borrarItemFormulario());
        carritoListaView.getBtnBuscar().addActionListener(e -> buscarCarritoCodigo());
        carritoListaView.getBtnListar().addActionListener(e -> listarCarritos());
        carritoListaView.getBtnVer().addActionListener(e -> mostrarDetalle());
        carritoModificarView.getBtnBuscar().addActionListener(e -> buscarCarritoCodigoModificar());
        carritoModificarView.getBtnEditar().addActionListener(e -> editarCarrito());
        carritoEliminarView.getBtnBuscar().addActionListener(e -> buscarCarritoCodigoEliminar());
        carritoEliminarView.getBtnEliminar().addActionListener(e -> eliminarCarrito());
    }

    /**
     * Añade un producto al carrito de compras actual.
     * Obtiene el código del producto y la cantidad desde la vista, valida la existencia
     * del producto y actualiza tanto el modelo del carrito como la interfaz de usuario.
     */
    private void anadirProductoACarrito() {
        // NECESARIO: Añadir manejo de errores por si el producto no existe o el código es inválido.
        try {
            int codigo = Integer.parseInt(carritoAnadirView.getTxtCodigo().getText());
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto == null) {
                carritoAnadirView.mostrarMensaje(mi.get("producto.msj.no.encontrado"));
                return;
            }
            int cantidad =  Integer.parseInt(carritoAnadirView.getCbxCantidad().getSelectedItem().toString());
            carrito.agregarProducto(producto, cantidad);
            actualizarTabla();
            actualizarTotales();
            carritoAnadirView.limpiarCampos();
        } catch (NumberFormatException e) {
            carritoAnadirView.mostrarMensaje(mi.get("producto.msj.codigo.invalido"));
        }
    }

    /**
     * Actualiza la tabla de productos en la vista de añadir carrito.
     * Limpia el modelo de la tabla y lo vuelve a poblar con los items actuales
     * del carrito, aplicando el formato de moneda correspondiente.
     */
    private void actualizarTabla() {
        List<ItemCarrito> items = carrito.obtenerItems();
        DefaultTableModel modelo = (DefaultTableModel) carritoAnadirView.getTblProductos().getModel();
        modelo.setNumRows(0);
        for (ItemCarrito item : items) {
            Locale locale = mi.getLocale();
            modelo.addRow(new Object[]{
                    item.getProducto().getCodigo(),
                    item.getProducto().getNombre(),
                    Formateador.formatearMoneda(item.getProducto().getPrecio(), locale),
                    item.getCantidad(),
                    Formateador.formatearMoneda(item.getProducto().getPrecio() * item.getCantidad(), locale)
            });
        }
    }

    /**
     * Recalcula y actualiza los campos de texto de subtotal, IVA y total en la vista.
     */
    private void actualizarTotales() {
        Locale locale = mi.getLocale();
        carritoAnadirView.getTxtSubtotal().setText(Formateador.formatearMoneda(carrito.calcularSubtotal(), locale));
        carritoAnadirView.getTxtIVA().setText(Formateador.formatearMoneda(carrito.calcularIVA(), locale));
        carritoAnadirView.getTxtTotal().setText(Formateador.formatearMoneda(carrito.calcularTotal(), locale));
    }

    /**
     * Guarda el carrito de compras actual en la capa de persistencia.
     * Valida que el carrito no esté vacío, le asigna el usuario y la fecha,
     * y si la operación es exitosa, muestra un mensaje y prepara un nuevo
     * carrito para la siguiente compra.
     * @apiNote Este método asume que la operación `carritoDAO.crear()` tendrá éxito.
     * Para un código más robusto, se debería manejar un valor de retorno del DAO.
     */
    private void aceptarCarrito() {
        if (carrito.estaVacio()) {
            carritoAnadirView.mostrarMensaje(mi.get("carrito.msj.vacio"));
            return;
        }
        carrito.setUsuario(usuario);
        carrito.setFechaCreacion(new GregorianCalendar());
        carritoDAO.crear(carrito);
        // NECESARIO: Usar getCedula() para ser consistente con el modelo Usuario corregido.
        String mensaje = mi.get("carrito.msj.guardado")
                .replace("{0}", String.valueOf(carrito.getCodigo()))
                .replace("{1}", usuario.getCedula());
        carritoAnadirView.mostrarMensaje(mensaje);

        // NECESARIO: Añadir estas líneas para crear un nuevo carrito para la siguiente compra.
        this.carrito = new Carrito();
        this.carrito.setCodigo(this.carrito.hashCode());

        actualizarTabla();
        actualizarTotales();
        carritoAnadirView.limpiarCampos();
    }

    /**
     * Elimina un item seleccionado de la tabla y del carrito de compras actual.
     * Pide confirmación al usuario antes de proceder con la eliminación.
     */
    private void borrarItemFormulario(){
        int filaSeleccionada = carritoAnadirView.getTblProductos().getSelectedRow();
        if (filaSeleccionada != -1) {
            DefaultTableModel modelo = (DefaultTableModel) carritoAnadirView.getTblProductos().getModel();
            int codigoProducto = (int) modelo.getValueAt(filaSeleccionada, 0);
            boolean confirmado = carritoAnadirView.mostrarMensajePregunta(mi.get("carrito.msj.confirmar.eliminar"));
            if (confirmado) {
                carrito.eliminarProducto(codigoProducto);
                actualizarTabla();
                actualizarTotales();
            }else{
                carritoAnadirView.mostrarMensaje(mi.get("carrito.msj.cancelado"));
            }
        } else {
            carritoAnadirView.mostrarMensaje(mi.get("carrito.msj.seleccione.fila"));
        }
    }

    /**
     * Vacía por completo el carrito de compras actual, previa confirmación del usuario.
     * Limpia la tabla y los totales en la vista.
     */
    private void limpiarFormulario() {
        boolean confirmado = carritoAnadirView.mostrarMensajePregunta(mi.get("carrito.msj.confirmar.vaciar"));
        if (confirmado) {
            carrito.vaciarCarrito();
            actualizarTabla();
            actualizarTotales();
            carritoAnadirView.limpiarCampos();
        } else {
            // NECESARIO: Usar un mensaje más coherente para la cancelación.
            carritoAnadirView.mostrarMensaje(mi.get("carrito.msj.cancelado"));
        }
    }

    /**
     * Busca un carrito por su código y muestra el resultado en la tabla de la vista de listado.
     * Permite a un administrador ver cualquier carrito, mientras que un usuario normal
     * solo puede ver los suyos.
     */
    private void buscarCarritoCodigo() {
        String txtCod = carritoListaView.getTxtCodigo().getText().trim();
        if (txtCod.isEmpty()) {
            carritoListaView.cargarDatos(List.of());
            return;
        }
        // NECESARIO: Usar una validación más robusta para números.
        if (!txtCod.matches("\\d+")) {
            carritoListaView.mostrarMensaje(mi.get("carrito.msj.codigo.invalido"));
            carritoListaView.cargarDatos(List.of());
            return;
        }
        int codigo = Integer.parseInt(txtCod);
        Carrito carritoEncontrado = carritoDAO.buscarPorCodigo(codigo);
        if (carritoEncontrado != null) {
            // NECESARIO: Usar getCedula() en la comparación.
            if (usuario.getRol().equals(Rol.ADMINISTRADOR)
                    || carritoEncontrado.getUsuario().getCedula().equals(usuario.getCedula())) {
                carritoListaView.cargarDatos(List.of(carritoEncontrado));
            } else {
                carritoListaView.cargarDatos(List.of());
            }
        } else {
            carritoListaView.cargarDatos(List.of());
        }
    }

    /**
     * Carga en la tabla los carritos correspondientes al usuario.
     * Si el usuario es administrador, lista todos los carritos. Si no, solo los del usuario.
     */
    private void listarCarritos() {
        List<Carrito> carritosAMostrar;

        if (usuario.getRol().equals(Rol.ADMINISTRADOR)) {
            carritosAMostrar = carritoDAO.listarTodos();
        } else {
            carritosAMostrar = carritoDAO.buscarPorUsuario(usuario);
        }

        carritoListaView.cargarDatos(carritosAMostrar);
    }

    /**
     * Muestra una ventana de detalle para el carrito seleccionado en la tabla de la lista.
     */
    private void mostrarDetalle() {
        int filaSeleccionada = carritoListaView.getTblProducto().getSelectedRow();

        if (filaSeleccionada != -1) {
            int codigoCarrito = (int) carritoListaView.getModelo().getValueAt(filaSeleccionada, 0);

            Carrito carritoEncontrado = carritoDAO.buscarPorCodigo(codigoCarrito);
            if (carritoEncontrado != null) {
                if (carritoDetalleView == null || carritoDetalleView.isClosed()) {
                    carritoDetalleView = new CarritoDetalleView(mi);
                    carritoListaView.getDesktopPane().add(carritoDetalleView);
                }

                carritoDetalleView.cargarDatos(carritoEncontrado);

                Locale locale = mi.getLocale();
                carritoDetalleView.getTxtSubtotal().setText(Formateador.formatearMoneda(carritoEncontrado.calcularSubtotal(), locale));
                carritoDetalleView.getTxtIVA().setText(Formateador.formatearMoneda(carritoEncontrado.calcularIVA(), locale));
                carritoDetalleView.getTxtTotal().setText(Formateador.formatearMoneda(carritoEncontrado.calcularTotal(), locale));

                carritoDetalleView.setVisible(true);
                carritoDetalleView.toFront();
                carritoDetalleView.requestFocusInWindow();

            } else {
                carritoListaView.mostrarMensaje(mi.get("carrito.msj.no.encontrado"));
            }
        } else {
            carritoListaView.mostrarMensaje(mi.get("carrito.msj.seleccione.carrito"));
        }
    }

    /**
     * Busca un carrito por código y carga sus datos en la vista de modificación.
     */
    private void buscarCarritoCodigoModificar() {
        String txtCod = carritoModificarView.getTxtCodigo().getText().trim();
        if (txtCod.isEmpty()) {
            carritoModificarView.mostrarMensaje(mi.get("carrito.msj.ingrese.codigo"));
            return;
        }
        if (!txtCod.matches("\\d+")) {
            carritoModificarView.mostrarMensaje(mi.get("carrito.msj.codigo.invalido"));
            return;
        }
        int codigo = Integer.parseInt(txtCod);
        Carrito carritoEncontrado = carritoDAO.buscarPorCodigo(codigo);

        if (carritoEncontrado != null) {
            Locale locale = mi.getLocale();
            carritoModificarView.getTxtFecha().setText(
                    Formateador.formatearFecha(carritoEncontrado.getFechaCreacion().getTime(), locale)
            );
            carritoModificarView.cargarDatos(carritoEncontrado);
            carritoModificarView.getTxtSubtotal().setText(Formateador.formatearMoneda(carritoEncontrado.calcularSubtotal(), locale));
            carritoModificarView.getTxtIVA().setText(Formateador.formatearMoneda(carritoEncontrado.calcularIVA(), locale));


            carritoModificarView.getTxtTotal().setText(Formateador.formatearMoneda(carritoEncontrado.calcularTotal(), locale));

        } else {
            carritoModificarView.mostrarMensaje(mi.get("carrito.msj.no.encontrado"));
        }
    }

    /**
     * Edita la cantidad de un item dentro de un carrito existente.
     * Solicita la nueva cantidad al usuario y actualiza el carrito en la capa de persistencia.
     */
    private void editarCarrito() {
        if (carritoModificarView.getTblProducto().getSelectedRow() != -1) {
            String cantidadStr = carritoModificarView.cantidad(mi.get("carrito.msj.modificar.cantidad"));
            if (cantidadStr != null) {
                cantidadStr = cantidadStr.trim();
                if (!cantidadStr.matches("[1-9]\\d*")) {
                    // NECESARIO: Eliminar el segundo mensaje de error que estaba duplicado.
                    carritoModificarView.mostrarMensaje(mi.get("carrito.msj.cantidad.invalida"));
                    return;
                }
                int nuevaCantidad = Integer.parseInt(cantidadStr);
                int codigoCarrito = Integer.parseInt(carritoModificarView.getTxtCodigo().getText().trim());
                Carrito carritoEncontrado = carritoDAO.buscarPorCodigo(codigoCarrito);
                if (carritoEncontrado == null) {
                    carritoModificarView.mostrarMensaje(mi.get("carrito.msj.no.encontrado"));
                    return;
                }

                int codigoProducto = (Integer) carritoModificarView.getTblProducto().getValueAt(
                        carritoModificarView.getTblProducto().getSelectedRow(), 0);

                boolean itemEncontrado = false;
                for (ItemCarrito item : carritoEncontrado.obtenerItems()) {
                    if (item.getProducto().getCodigo() == codigoProducto) {
                        item.setCantidad(nuevaCantidad);
                        itemEncontrado = true;
                        break;
                    }
                }

                if (!itemEncontrado) {
                    carritoModificarView.mostrarMensaje(mi.get("carrito.msj.producto.no.encontrado"));
                    return;
                }

                carritoDAO.actualizar(carritoEncontrado);
                carritoModificarView.cargarDatos(carritoEncontrado);
                Locale locale = mi.getLocale();
                carritoModificarView.getTxtSubtotal().setText(Formateador.formatearMoneda(carritoEncontrado.calcularSubtotal(), locale));
                carritoModificarView.getTxtIVA().setText(Formateador.formatearMoneda(carritoEncontrado.calcularIVA(), locale));
                carritoModificarView.getTxtTotal().setText(Formateador.formatearMoneda(carritoEncontrado.calcularTotal(), locale));
            } else {
                carritoModificarView.mostrarMensaje(mi.get("carrito.msj.cantidad.no.modificada"));
            }
        } else {
            carritoModificarView.mostrarMensaje(mi.get("carrito.msj.seleccione.item"));
        }
    }

    /**
     * Busca un carrito por código y carga sus datos en la vista de eliminación.
     */
    private void buscarCarritoCodigoEliminar() {
        String txtCod = carritoEliminarView.getTxtCodigo().getText().trim();
        if (txtCod.isEmpty()) {
            carritoEliminarView.mostrarMensaje(mi.get("carrito.msj.ingrese.codigo"));
            carritoEliminarView.getBtnEliminar().setEnabled(false);
            return;
        }
        if (!txtCod.matches("\\d+")) {
            carritoEliminarView.mostrarMensaje(mi.get("carrito.msj.codigo.invalido"));
            carritoEliminarView.getBtnEliminar().setEnabled(false);
            return;
        }
        int codigo = Integer.parseInt(txtCod);
        Carrito carritoEncontrado = carritoDAO.buscarPorCodigo(codigo);
        if (carritoEncontrado != null) {
            Locale locale = mi.getLocale();
            // NECESARIO: Usar 'carritoEncontrado' para evitar un NullPointerException.
            carritoEliminarView.getTxtFecha().setText(
                    Formateador.formatearFecha(carritoEncontrado.getFechaCreacion().getTime(), locale)
            );

            carritoEliminarView.cargarDatos(carritoEncontrado);
            carritoEliminarView.getTxtSubtotal().setText(Formateador.formatearMoneda(carritoEncontrado.calcularSubtotal(), locale));
            carritoEliminarView.getTxtIVA().setText(Formateador.formatearMoneda(carritoEncontrado.calcularIVA(), locale));
            carritoEliminarView.getTxtTotal().setText(Formateador.formatearMoneda(carritoEncontrado.calcularTotal(), locale));
            carritoEliminarView.getBtnEliminar().setEnabled(true);
        } else {
            carritoEliminarView.mostrarMensaje(mi.get("carrito.msj.no.encontrado"));
            carritoEliminarView.getBtnEliminar().setEnabled(false);
        }
    }

    /**
     * Elimina un carrito de la capa de persistencia, previa confirmación del usuario.
     */
    private void eliminarCarrito(){
        boolean confirmado = carritoEliminarView.mostrarMensajePregunta(mi.get("carrito.msj.eliminar.confirmar"));
        if(confirmado) {
            carritoDAO.eliminar(Integer.parseInt(carritoEliminarView.getTxtCodigo().getText()));
            carritoEliminarView.mostrarMensaje(mi.get("carrito.msj.eliminado"));
            carritoEliminarView.limpiarCampos();
        }else{
            carritoEliminarView.mostrarMensaje(mi.get("carrito.msj.eliminar.cancelada"));
        }
    }

    /**
     * Actualiza el idioma de todas las vistas gestionadas por este controlador.
     */
    public void actualizarIdiomaEnVistas() {
        carritoAnadirView.cambiarIdioma();
        carritoListaView.cambiarIdioma();
        carritoModificarView.cambiarIdioma();
        carritoEliminarView.cambiarIdioma();
        if (carritoDetalleView != null && !carritoDetalleView.isClosed()) {
            carritoDetalleView.cambiarIdioma();
        }
    }
}