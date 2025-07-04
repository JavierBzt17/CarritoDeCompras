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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class ProductoController {
    private final ProductoAnadirView productoAnadirView;
    private final ProductoListaView productoListaView;
    private final ProductoDAO productoDAO;
    private final ProductoEditarView productoEditarView;
    private final ProductoEliminarView productoEliminarView;
    private final CarritoAnadirView carritoAnadirView;
    private final MensajeInternacionalizacionHandler mi;


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


    private void configurarEventos() {
        productoAnadirView.getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarProducto();
            }
        });

        productoListaView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProducto();
            }
        });

        productoListaView.getBtnListar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarProductos();
            }
        });

        productoEditarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductoEdicion();
            }
        });

        productoEditarView.getBtnActualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarProducto();
            }
        });

        productoEliminarView.getBtnEliminar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });
        productoEliminarView.getBtnBuscar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductoEliminar();
            }
        });
        carritoAnadirView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductoCarrito();
            }
        });
    }

    private void guardarProducto() {
        String codigoTexto = productoAnadirView.getTxtCodigo().getText().trim();
        String nombre = productoAnadirView.getTxtNombre().getText().trim();
        String precioTexto = productoAnadirView.getTxtPrecio().getText().trim();

        if (codigoTexto.isEmpty() || nombre.isEmpty() || precioTexto.isEmpty()) {
            productoAnadirView.mostrarMensaje(mi.get("producto.mensaje.campos.vacios"));
            return;
        }

        if (!codigoTexto.matches("[1-9]\\d*|0")) {//sin letras, sin decimales
            productoAnadirView.mostrarMensaje(mi.get("producto.mensaje.codigo.invalido"));
            return;
        }

        int codigo = Integer.parseInt(codigoTexto);
        if (!precioTexto.matches("\\d+(\\.\\d+)?")) {//número decimal positivo
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




    private void buscarProducto() {
        String nombre = productoListaView.getTxtBuscar().getText();

        List<Producto> productosEncontrados = productoDAO.buscarPorNombre(nombre);
        productoListaView.cargarDatos(productosEncontrados);
    }

    private void listarProductos() {
        List<Producto> productos = productoDAO.listarTodos();
        productoListaView.cargarDatos(productos);
    }

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

    private void buscarProductoCarrito() {
        String txtCod = carritoAnadirView.getTxtBuscar().getText().trim();

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

    public void actualizarIdiomaEnVistas() {
        productoAnadirView.cambiarIdioma();
        productoEditarView.cambiarIdioma();
        productoEliminarView.cambiarIdioma();
        productoListaView.cambiarIdioma();
    }

}
