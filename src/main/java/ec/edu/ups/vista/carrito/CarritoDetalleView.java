package ec.edu.ups.vista.carrito;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.Formateador;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Locale;

/**
 * La clase **CarritoDetalleView** representa la vista de la interfaz gráfica
 * para mostrar los detalles de un carrito de compras.
 * Presenta los productos en el carrito, sus cantidades, precios, y calcula
 * el subtotal, IVA y total de la compra. También soporta la internacionalización del texto.
 */
public class CarritoDetalleView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTable tblProducto;
    private JTextField txtSubtotal;
    private JTextField txtIVA;
    private JTextField txtTotal;
    private JLabel lblSubtotal;
    private JLabel lblCarrito;
    private JLabel lblIVA;
    private JLabel lblTotal;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mi;

    /**
     * Constructor para la vista CarritoDetalleView.
     * Inicializa los componentes de la interfaz, configura la tabla de productos,
     * y aplica la internacionalización.
     *
     * @param mi El manejador de internacionalización de mensajes para la vista.
     */
    public CarritoDetalleView(MensajeInternacionalizacionHandler mi) {
        setContentPane(panelPrincipal);
        setTitle("Listado de Carritos");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(700, 700);
        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setIconifiable(true);

        modelo = new DefaultTableModel();
        tblProducto.setModel(modelo);

        this.mi = mi;
        cambiarIdioma();
    }

    /**
     * Carga los datos de un objeto **Carrito** en la tabla de productos.
     * Limpia la tabla existente y añade filas con la información de cada **ItemCarrito**,
     * formateando el precio y el subtotal de cada ítem según la configuración regional.
     *
     * @param carrito El objeto **Carrito** cuyos ítems se van a mostrar.
     */
    public void cargarDatos(Carrito carrito) {
        modelo.setRowCount(0);
        Locale locale = mi.getLocale();

        for (ItemCarrito itemCarrito : carrito.obtenerItems()) {
            Producto producto = itemCarrito.getProducto();
            Object[] fila = {
                    producto.getCodigo(),
                    producto.getNombre(),
                    Formateador.formatearMoneda(producto.getPrecio(), locale),
                    itemCarrito.getCantidad(),
                    Formateador.formatearMoneda(producto.getPrecio() * itemCarrito.getCantidad(), locale)
            };
            modelo.addRow(fila);
        }
    }

    /**
     * Obtiene la tabla de productos en el carrito.
     *
     * @return La JTable de productos.
     */
    public JTable getTblProducto() {
        return tblProducto;
    }

    /**
     * Establece la tabla de productos en el carrito.
     *
     * @param tblProducto La JTable a establecer.
     */
    public void setTblProducto(JTable tblProducto) {
        this.tblProducto = tblProducto;
    }

    /**
     * Obtiene el campo de texto para el subtotal del carrito.
     *
     * @return El JTextField del subtotal.
     */
    public JTextField getTxtSubtotal() {
        return txtSubtotal;
    }

    /**
     * Establece el campo de texto para el subtotal del carrito.
     *
     * @param txtSubtotal El JTextField a establecer.
     */
    public void setTxtSubtotal(JTextField txtSubtotal) {
        this.txtSubtotal = txtSubtotal;
    }

    /**
     * Obtiene el campo de texto para el IVA del carrito.
     *
     * @return El JTextField del IVA.
     */
    public JTextField getTxtIVA() {
        return txtIVA;
    }

    /**
     * Establece el campo de texto para el IVA del carrito.
     *
     * @param txtIVA El JTextField a establecer.
     */
    public void setTxtIVA(JTextField txtIVA) {
        this.txtIVA = txtIVA;
    }

    /**
     * Obtiene el campo de texto para el total del carrito.
     *
     * @return El JTextField del total.
     */
    public JTextField getTxtTotal() {
        return txtTotal;
    }

    /**
     * Establece el campo de texto para el total del carrito.
     *
     * @param txtTotal El JTextField a establecer.
     */
    public void setTxtTotal(JTextField txtTotal) {
        this.txtTotal = txtTotal;
    }

    /**
     * Obtiene la etiqueta del subtotal.
     *
     * @return El JLabel del subtotal.
     */
    public JLabel getLblSubtotal() {
        return lblSubtotal;
    }

    /**
     * Establece la etiqueta del subtotal.
     *
     * @param lblSubtotal El JLabel a establecer.
     */
    public void setLblSubtotal(JLabel lblSubtotal) {
        this.lblSubtotal = lblSubtotal;
    }

    /**
     * Obtiene la etiqueta principal del carrito.
     *
     * @return El JLabel del carrito.
     */
    public JLabel getLblCarrito() {
        return lblCarrito;
    }

    /**
     * Establece la etiqueta principal del carrito.
     *
     * @param lblCarrito El JLabel a establecer.
     */
    public void setLblCarrito(JLabel lblCarrito) {
        this.lblCarrito = lblCarrito;
    }

    /**
     * Obtiene la etiqueta del IVA.
     *
     * @return El JLabel del IVA.
     */
    public JLabel getLblIVA() {
        return lblIVA;
    }

    /**
     * Establece la etiqueta del IVA.
     *
     * @param lblIVA El JLabel a establecer.
     */
    public void setLblIVA(JLabel lblIVA) {
        this.lblIVA = lblIVA;
    }

    /**
     * Obtiene la etiqueta del total.
     *
     * @return El JLabel del total.
     */
    public JLabel getLblTotal() {
        return lblTotal;
    }

    /**
     * Establece la etiqueta del total.
     *
     * @param lblTotal El JLabel a establecer.
     */
    public void setLblTotal(JLabel lblTotal) {
        this.lblTotal = lblTotal;
    }

    /**
     * Obtiene el modelo de tabla por defecto para la tabla de productos.
     *
     * @return El DefaultTableModel de la tabla.
     */
    public DefaultTableModel getModelo() {
        return modelo;
    }

    /**
     * Establece el modelo de tabla por defecto para la tabla de productos.
     *
     * @param modelo El DefaultTableModel a establecer.
     */
    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    /**
     * Cambia el idioma de todos los componentes de la vista utilizando el `MensajeInternacionalizacionHandler`.
     * Actualiza los textos de etiquetas, el título de la ventana y los encabezados de la tabla.
     */
    public void cambiarIdioma() {
        mi.setLenguaje(mi.getLocale().getLanguage(), mi.getLocale().getCountry());

        setTitle(mi.get("carrito.detalle.titulo.ventana"));
        lblCarrito.setText(mi.get("carrito.detalle.titulo"));
        lblSubtotal.setText(mi.get("carrito.detalle.subtotal"));
        lblIVA.setText(mi.get("carrito.detalle.iva"));
        lblTotal.setText(mi.get("carrito.detalle.total"));

        Object[] columnas = {
                mi.get("carrito.detalle.columna.codigo"),
                mi.get("carrito.detalle.columna.nombre"),
                mi.get("carrito.detalle.columna.precio"),
                mi.get("carrito.detalle.columna.cantidad"),
                mi.get("carrito.detalle.columna.subtotal")
        };
        modelo.setColumnIdentifiers(columnas);

        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }
}