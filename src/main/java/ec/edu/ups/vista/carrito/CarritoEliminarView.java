package ec.edu.ups.vista.carrito;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.Formateador;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
import java.util.Locale;

/**
 * La clase **CarritoEliminarView** representa la vista de la interfaz gráfica
 * para la eliminación de carritos de compra. Permite buscar carritos por código,
 * visualizar sus productos, y eliminar carritos.
 * También soporta la internacionalización del texto y la carga de imágenes.
 */
public class CarritoEliminarView extends JInternalFrame {
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JTextField txtFecha;
    private JTable tblProducto;
    private JButton btnEliminar;
    private JTextField txtSubtotal;
    private JTextField txtIVA;
    private JTextField txtTotal;
    private JPanel panelPrincipal;
    private JLabel lblCodigo;
    private JLabel lblFecha;
    private JLabel lblSubtotal;
    private JLabel lblIVA;
    private JLabel lblTotal;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mi;

    /**
     * Constructor para la vista CarritoEliminarView.
     * Inicializa los componentes de la interfaz, configura la tabla de productos,
     * y aplica la internacionalización y las imágenes.
     *
     * @param mi El manejador de internacionalización de mensajes para la vista.
     */
    public CarritoEliminarView(MensajeInternacionalizacionHandler mi) {
        setContentPane(panelPrincipal);
        setTitle("Edicion de Carrito");
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
        inicializarImagenes();
    }

    /**
     * Muestra un mensaje informativo en un cuadro de diálogo.
     *
     * @param mensaje El mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Carga los datos de un objeto **Carrito** en la tabla de productos.
     * Limpia la tabla existente y añade filas con la información de cada **ItemCarrito**,
     * formateando el precio de cada ítem según la configuración regional.
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
                    itemCarrito.getCantidad()
            };
            modelo.addRow(fila);
        }
    }

    /**
     * Muestra un cuadro de diálogo de confirmación con una pregunta y devuelve la respuesta del usuario.
     *
     * @param mensaje La pregunta a mostrar.
     * @return true si el usuario selecciona "Sí", false en caso contrario.
     */
    public boolean mostrarMensajePregunta(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, "Confirmación",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }

    /**
     * Limpia todos los campos de texto y la tabla de productos en la vista.
     */
    public void limpiarCampos() {
        txtCodigo.setText("");
        txtFecha.setText("");
        txtSubtotal.setText("");
        txtIVA.setText("");
        txtTotal.setText("");
        modelo.setRowCount(0);
    }

    /**
     * Obtiene el campo de texto para el código del carrito.
     *
     * @return El JTextField para el código.
     */
    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    /**
     * Establece el campo de texto para el código del carrito.
     *
     * @param txtCodigo El JTextField a establecer.
     */
    public void setTxtCodigo(JTextField txtCodigo) {
        this.txtCodigo = txtCodigo;
    }

    /**
     * Obtiene el botón de búsqueda.
     *
     * @return El JButton de búsqueda.
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Establece el botón de búsqueda.
     *
     * @param btnBuscar El JButton a establecer.
     */
    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    /**
     * Obtiene el campo de texto para la fecha del carrito.
     *
     * @return El JTextField para la fecha.
     */
    public JTextField getTxtFecha() {
        return txtFecha;
    }

    /**
     * Establece el campo de texto para la fecha del carrito.
     *
     * @param txtFecha El JTextField a establecer.
     */
    public void setTxtFecha(JTextField txtFecha) {
        this.txtFecha = txtFecha;
    }

    /**
     * Obtiene la tabla de productos del carrito.
     *
     * @return La JTable de productos.
     */
    public JTable getTblProducto() {
        return tblProducto;
    }

    /**
     * Establece la tabla de productos del carrito.
     *
     * @param tblProducto La JTable a establecer.
     */
    public void setTblProducto(JTable tblProducto) {
        this.tblProducto = tblProducto;
    }

    /**
     * Obtiene el botón de eliminar carrito.
     *
     * @return El JButton de eliminar.
     */
    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    /**
     * Establece el botón de eliminar carrito.
     *
     * @param btnEliminar El JButton a establecer.
     */
    public void setBtnEliminar(JButton btnEliminar) {
        this.btnEliminar = btnEliminar;
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
     * Obtiene el panel principal de la vista.
     *
     * @return El JPanel principal.
     */
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    /**
     * Establece el panel principal de la vista.
     *
     * @param panelPrincipal El JPanel a establecer como principal.
     */
    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    /**
     * Obtiene la etiqueta del código del carrito.
     *
     * @return El JLabel del código.
     */
    public JLabel getLblCodigo() {
        return lblCodigo;
    }

    /**
     * Establece la etiqueta del código del carrito.
     *
     * @param lblCodigo El JLabel a establecer.
     */
    public void setLblCodigo(JLabel lblCodigo) {
        this.lblCodigo = lblCodigo;
    }

    /**
     * Obtiene la etiqueta de la fecha del carrito.
     *
     * @return El JLabel de la fecha.
     */
    public JLabel getLblFecha() {
        return lblFecha;
    }

    /**
     * Establece la etiqueta de la fecha del carrito.
     *
     * @param lblFecha El JLabel a establecer.
     */
    public void setLblFecha(JLabel lblFecha) {
        this.lblFecha = lblFecha;
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
     * Actualiza los textos de etiquetas, botones y encabezados de la tabla.
     */
    public void cambiarIdioma(){
        mi.setLenguaje(mi.getLocale().getLanguage(), mi.getLocale().getCountry());

        setTitle(mi.get("carrito.eliminar.titulo"));
        lblCodigo.setText(mi.get("carrito.eliminar.codigo"));
        lblFecha.setText(mi.get("carrito.eliminar.fecha"));
        lblSubtotal.setText(mi.get("carrito.eliminar.subtotal"));
        lblIVA.setText(mi.get("carrito.eliminar.iva"));
        lblTotal.setText(mi.get("carrito.eliminar.total"));
        btnBuscar.setText(mi.get("carrito.eliminar.boton.buscar"));
        btnEliminar.setText(mi.get("carrito.eliminar.boton.eliminar"));

        modelo.setColumnIdentifiers(new Object[]{
                mi.get("carrito.eliminar.tabla.codigo"),
                mi.get("carrito.eliminar.tabla.nombre"),
                mi.get("carrito.eliminar.tabla.precio"),
                mi.get("carrito.eliminar.tabla.cantidad")
        });
        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }

    /**
     * Inicializa las imágenes de los botones cargando los iconos desde las rutas especificadas.
     */
    public void inicializarImagenes(){
        URL eliminar = CarritoEliminarView.class.getClassLoader().getResource("imagenes/eliminar.png");
        if (eliminar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(eliminar);
            btnEliminar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("No se ha cargo el icono Login");
        }

        URL buscar = CarritoEliminarView.class.getClassLoader().getResource("imagenes/buscar.png");
        if (buscar != null) {
            ImageIcon iconoBtnRegistrarse = new ImageIcon(buscar);
            btnBuscar.setIcon(iconoBtnRegistrarse);
        } else {
            System.err.println("No se ha cargado el icono Registrarse");
        }
    }
}