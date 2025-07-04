package ec.edu.ups.controlador;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.*;
import ec.edu.ups.util.Formateador;
import ec.edu.ups.vista.carrito.*;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class CarritoController {
    private final CarritoAnadirView carritoAnadirView;
    private final ProductoDAO productoDAO;
    private final Carrito carrito;
    private final CarritoDAO carritoDAO;
    private final Usuario usuario;
    private final CarritoListaView carritoListaView;
    private CarritoDetalleView carritoDetalleView;
    private final CarritoModificarView carritoModificarView;
    private final CarritoEliminarView carritoEliminarView;
    private final MensajeInternacionalizacionHandler mi;

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
    private void configurarEventosEnVistas() {
        carritoAnadirView.getBtnAnadir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                anadirProductoACarrito();
            }
        });
        carritoAnadirView.getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aceptarCarrito();
            }
        });
        carritoAnadirView.getBtnLimpiar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
            }
        });
        carritoAnadirView.getBtnBorrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrarItemFormulario();
            }
        });
        carritoListaView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarCarritoCodigo();
            }
        });
        carritoListaView.getBtnListar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarCarritos();
            }
        });
        carritoListaView.getBtnVer().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDetalle();
            }
        });
        carritoModificarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarCarritoCodigoModificar();
            }
        });
        carritoModificarView.getBtnEditar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarCarrito();
            }
        });
        carritoEliminarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarCarritoCodigoEliminar();
            }
        });
        carritoEliminarView.getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarCarrito();
            }
        });
    }

    private void anadirProductoACarrito() {
        int codigo = Integer.parseInt(carritoAnadirView.getTxtBuscar().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        int cantidad =  Integer.parseInt(carritoAnadirView.getCbxCantidad().getSelectedItem().toString());
        carrito.agregarProducto(producto, cantidad);
        actualizarTabla();
        actualizarTotales();
        carritoAnadirView.limpiarCampos();
    }

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

    private void actualizarTotales() {
        Locale locale = mi.getLocale();
        carritoAnadirView.getTxtSubTotal().setText(Formateador.formatearMoneda(carrito.calcularSubtotal(), locale));
        carritoAnadirView.getTxtIVA().setText(Formateador.formatearMoneda(carrito.calcularIVA(), locale));
        carritoAnadirView.getTxtTotal().setText(Formateador.formatearMoneda(carrito.calcularTotal(), locale));
    }

    private void aceptarCarrito() {
        if (carrito.estaVacio()) {
            carritoAnadirView.mostrarMensaje(mi.get("carrito.msj.vacio"));
            return;
        }
        carrito.setUsuario(usuario);
        carrito.setFechaCreacion(new GregorianCalendar());
        carritoDAO.crear(carrito);
        String mensaje = mi.get("carrito.msj.guardado")
                .replace("{0}", String.valueOf(carrito.getCodigo()))
                .replace("{1}", usuario.getUsername());
        carritoAnadirView.mostrarMensaje(mensaje);

        carrito.vaciarCarrito();
        actualizarTabla();
        actualizarTotales();
        carritoAnadirView.limpiarCampos();
    }

    private void borrarItemFormulario(){
        int filaSeleccionada = carritoAnadirView.getTblProductos().getSelectedRow();
        if (filaSeleccionada != -1) {
            DefaultTableModel modelo = (DefaultTableModel) carritoAnadirView.getTblProductos().getModel();
            int codigoProducto = Integer.parseInt(modelo.getValueAt(filaSeleccionada, 0).toString());
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

    private void limpiarFormulario() {
        boolean confirmado = carritoAnadirView.mostrarMensajePregunta(mi.get("carrito.msj.confirmar.vaciar"));
        if (confirmado) {
            carrito.vaciarCarrito();
            actualizarTabla();
            actualizarTotales();
            carritoAnadirView.limpiarCampos();
        }else{
            carritoAnadirView.mostrarMensaje(mi.get("carrito.msj.vaciado"));
        }
    }

    private void buscarCarritoCodigo() {
        String txtCod = carritoListaView.getTxtCodigo().getText().trim();
        if (txtCod.isEmpty()) {
            carritoListaView.cargarDatos(List.of());
            return;
        }
        if (!txtCod.matches("[1-9]\\d*|0")) {
            carritoListaView.mostrarMensaje(mi.get("carrito.msj.codigo.invalido"));
            carritoListaView.cargarDatos(List.of());
            return;
        }
        int codigo = Integer.parseInt(txtCod);
        Carrito carrito = carritoDAO.buscarPorCodigo(codigo);
        if (carrito != null) {
            if (usuario.getRol().equals(Rol.ADMINISTRADOR)
                    || carrito.getUsuario().getUsername().equals(usuario.getUsername())) {
                carritoListaView.cargarDatos(List.of(carrito));
            } else {
                carritoListaView.cargarDatos(List.of());
            }
        } else {
            carritoListaView.cargarDatos(List.of());
        }
    }

    private void listarCarritos() {
        List<Carrito> carritosAMostrar;

        if (usuario.getRol().equals(Rol.ADMINISTRADOR)) {
            carritosAMostrar = carritoDAO.listarTodos();
        } else {
            carritosAMostrar = carritoDAO.buscarPorUsuario(usuario);
        }

        carritoListaView.cargarDatos(carritosAMostrar);
    }
    private void mostrarDetalle() {
        int filaSeleccionada = carritoListaView.getTblProducto().getSelectedRow();

        if (filaSeleccionada != -1) {
            int codigoCarrito = (int) carritoListaView.getModelo().getValueAt(filaSeleccionada, 0);

            Carrito carrito = carritoDAO.buscarPorCodigo(codigoCarrito);
            if (carrito != null) {
                if (carritoDetalleView == null || carritoDetalleView.isClosed()) {
                    carritoDetalleView = new CarritoDetalleView(mi);
                    carritoListaView.getDesktopPane().add(carritoDetalleView);
                }

                carritoDetalleView.cargarDatos(carrito);

                Locale locale = mi.getLocale();
                carritoDetalleView.getTxtSubtotal().setText(Formateador.formatearMoneda(carrito.calcularSubtotal(), locale));
                carritoDetalleView.getTxtIVA().setText(Formateador.formatearMoneda(carrito.calcularIVA(), locale));
                carritoDetalleView.getTxtTotal().setText(Formateador.formatearMoneda(carrito.calcularTotal(), locale));

                carritoDetalleView.setVisible(true);
                carritoDetalleView.moveToFront();
                carritoDetalleView.requestFocusInWindow();

            } else {
                carritoListaView.mostrarMensaje(mi.get("carrito.msj.no.encontrado"));
            }
        } else {
            carritoListaView.mostrarMensaje(mi.get("carrito.msj.seleccione.carrito"));
        }
    }

    private void buscarCarritoCodigoModificar() {
        String txtCod = carritoModificarView.getTxtCodigo().getText().trim();
        if (txtCod.isEmpty()) {
            carritoModificarView.mostrarMensaje(mi.get("carrito.msj.ingrese.codigo"));
            return;
        }
        if (!txtCod.matches("[1-9]\\d*|0")) {
            carritoModificarView.mostrarMensaje(mi.get("carrito.msj.codigo.invalido"));
            return;
        }
        int codigo = Integer.parseInt(txtCod);
        Carrito carritoEncontrado = carritoDAO.buscarPorCodigo(codigo);

        if (carritoEncontrado != null) {
            Locale locale = mi.getLocale();
            carritoModificarView.getTxtFecha().setText(
                    Formateador.formatearFecha(carrito.getFechaCreacion().getTime(), locale)
            );
            carritoModificarView.cargarDatos(carritoEncontrado);
            carritoModificarView.getTxtSubtotal().setText(Formateador.formatearMoneda(carritoEncontrado.calcularSubtotal(), locale));
            carritoModificarView.getTxtIVA().setText(Formateador.formatearMoneda(carritoEncontrado.calcularIVA(), locale));
            carritoModificarView.getTxtTotal().setText(Formateador.formatearMoneda(carritoEncontrado.calcularTotal(), locale));
        } else {
            carritoModificarView.mostrarMensaje(mi.get("carrito.msj.no.encontrado"));
        }
    }

    private void editarCarrito() {
        if (carritoModificarView.getTblProducto().getSelectedRow() != -1) {
            String cantidadStr = carritoModificarView.cantidad(mi.get("carrito.msj.modificar.cantidad"));
            if (cantidadStr != null) {
                cantidadStr = cantidadStr.trim();
                if (!cantidadStr.matches("[1-9]\\d*")) {
                    carritoModificarView.mostrarMensaje(mi.get("carrito.msj.cantidad.invalida"));carritoModificarView.mostrarMensaje("La cantidad debe ser un número entero positivo mayor que cero.");
                    return;
                }
                int nuevaCantidad = Integer.parseInt(cantidadStr);
                int codigoCarrito = Integer.parseInt(carritoModificarView.getTxtCodigo().getText().trim());
                Carrito carritoEncontrado = carritoDAO.buscarPorCodigo(codigoCarrito);
                if (carritoEncontrado == null) {
                    carritoModificarView.mostrarMensaje(mi.get("carrito.msj.no.encontrado"));
                    return;
                }
                boolean itemEncontrado = false;
                for (ItemCarrito item : carritoEncontrado.obtenerItems()) {
                    int codigoProducto = (Integer) carritoModificarView.getTblProducto().getValueAt(
                            carritoModificarView.getTblProducto().getSelectedRow(), 0);
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

    private void buscarCarritoCodigoEliminar() {
        String txtCod = carritoEliminarView.getTxtCodigo().getText().trim();
        if (txtCod.isEmpty()) {
            carritoEliminarView.mostrarMensaje(mi.get("carrito.msj.ingrese.codigo"));
            carritoEliminarView.getBtnEliminar().setEnabled(false);
            return;
        }
        if (!txtCod.matches("[1-9]\\d*|0")) {
            carritoEliminarView.mostrarMensaje(mi.get("carrito.msj.codigo.invalido"));
            carritoEliminarView.getBtnEliminar().setEnabled(false);
            return;
        }
        int codigo = Integer.parseInt(txtCod);
        Carrito carritoEncontrado = carritoDAO.buscarPorCodigo(codigo);
        if (carritoEncontrado != null) {
            Locale locale = mi.getLocale();
            carritoEliminarView.getTxtFecha().setText(
                    Formateador.formatearFecha(carrito.getFechaCreacion().getTime(), locale)
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
