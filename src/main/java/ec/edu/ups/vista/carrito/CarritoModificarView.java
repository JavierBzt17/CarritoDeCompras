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
 * La clase **CarritoModificarView** representa la vista de la interfaz gráfica
 * para modificar carritos de compra existentes. Permite buscar un carrito por código,
 * visualizar sus productos y sus detalles, y ofrece la opción de modificar la cantidad
 * de un producto en el carrito. Soporta internacionalización y carga de imágenes.
 */
public class CarritoModificarView extends JInternalFrame {
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JTextField txtFecha;
    private JTable tblProducto;
    private JTextField txtSubtotal;
    private JTextField txtIVA;
    private JTextField txtTotal;
    private JButton btnEditar;
    private JPanel panelPrincipal;
    private JLabel lblFecha;
    private JLabel lblCodigo;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mi;

    /**
     * Constructor para la vista CarritoModificarView.
     * Inicializa los componentes de la interfaz, configura la tabla de productos,
     * y aplica la internacionalización y las imágenes.
     *
     * @param mi El manejador de internacionalización de mensajes para la vista.
     */
    public CarritoModificarView(MensajeInternacionalizacionHandler mi) {
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
                    itemCarrito.getCantidad()
            };
            modelo.addRow(fila);
        }
    }

    /**
     * Solicita al usuario que ingrese una cantidad a través de un cuadro de diálogo de entrada.
     * Previamente, muestra un mensaje de confirmación.
     *
     * @param mensaje El mensaje de confirmación antes de solicitar la cantidad.
     * @return La cantidad ingresada como String si el usuario confirma y la ingresa, de lo contrario, null.
     */
    public String cantidad(String mensaje) {
        if (mostrarMensajePregunta(mensaje)) {
            String respuesta = JOptionPane.showInputDialog(this, mi.get("carrito.modificar.ingresar.cantidad"));
            if (respuesta != null && !respuesta.trim().isEmpty()) {
                return respuesta.trim();
            }
        }
        return null;
    }

    /**
     * Muestra un cuadro de diálogo de confirmación con una pregunta y devuelve la respuesta del usuario.
     * El título del diálogo se obtiene del manejador de internacionalización.
     *
     * @param mensaje La pregunta a mostrar.
     * @return true si el usuario selecciona "Sí", false en caso contrario.
     */
    public boolean mostrarMensajePregunta(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, mi.get("dialogo.titulo.confirmacion"),
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
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
     * Obtiene el botón de edición.
     *
     * @return El JButton de edición.
     */
    public JButton getBtnEditar() {
        return btnEditar;
    }

    /**
     * Establece el botón de edición.
     *
     * @param btnEditar El JButton a establecer.
     */
    public void setBtnEditar(JButton btnEditar) {
        this.btnEditar = btnEditar;
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

        setTitle(mi.get("carrito.modificar.titulo.ventana"));
        lblCodigo.setText(mi.get("carrito.modificar.buscar.codigo"));
        lblFecha.setText(mi.get("carrito.modificar.fecha"));
        btnBuscar.setText(mi.get("carrito.modificar.boton.buscar"));
        btnEditar.setText(mi.get("carrito.modificar.boton.editar"));

        Object[] columnas = {
                mi.get("carrito.modificar.columna.codigo"),
                mi.get("carrito.modificar.columna.nombre"),
                mi.get("carrito.modificar.columna.precio"),
                mi.get("carrito.modificar.columna.cantidad")
        };
        modelo.setColumnIdentifiers(columnas);

        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }

    /**
     * Inicializa las imágenes de los botones cargando los iconos desde las rutas especificadas.
     * Muestra un mensaje de error en la consola si un icono no se puede cargar.
     */
    public void inicializarImagenes(){
        URL eliminar = CarritoModificarView.class.getClassLoader().getResource("imagenes/editar.png");
        if (eliminar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(eliminar);
            btnEditar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error, No se cargo el icono Login");
        }

        URL buscar = CarritoModificarView.class.getClassLoader().getResource("imagenes/buscar.png");
        if (buscar != null) {
            ImageIcon iconoBtnRegistrarse = new ImageIcon(buscar);
            btnBuscar.setIcon(iconoBtnRegistrarse);
        } else {
            System.err.println("Error, No se cargo el icono Registrarse");
        }
    }
}