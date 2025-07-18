package ec.edu.ups.vista.carrito;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.util.Formateador;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
import java.util.List;
import java.util.Locale;

/**
 * La clase **CarritoListaView** representa la vista de la interfaz gráfica
 * para listar y visualizar carritos de compras. Permite buscar carritos
 * por código, listar todos los carritos y ver el detalle de un carrito seleccionado.
 * También soporta la internacionalización del texto y la carga de imágenes.
 */
public class CarritoListaView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JButton btnVer;
    private JTable tblProducto;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JButton btnListar;
    private JLabel lblCodigo;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mi;

    /**
     * Constructor para la vista CarritoListaView.
     * Inicializa los componentes de la interfaz, configura la tabla de productos,
     * y aplica la internacionalización y las imágenes.
     *
     * @param mi El manejador de internacionalización de mensajes para la vista.
     */
    public CarritoListaView(MensajeInternacionalizacionHandler mi) {
        super("Listado de Carritos", true, true, false, true); // El título inicial se sobrescribe en cambiarIdioma()
        setContentPane(panelPrincipal);
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
     * Carga los datos de una lista de objetos **Carrito** en la tabla.
     * Limpia la tabla existente y añade filas con la información de cada carrito,
     * incluyendo código, usuario, fecha de creación (formateada), subtotal, IVA y total (todos formateados como moneda).
     *
     * @param listaCarritos La lista de objetos **Carrito** a mostrar en la tabla.
     */
    public void cargarDatos(List<Carrito> listaCarritos) {
        modelo.setNumRows(0);
        Locale locale = mi.getLocale();

        for (Carrito carrito : listaCarritos) {
            String fechaFormateada = Formateador.formatearFecha(carrito.getFechaCreacion().getTime(), locale);

            Object[] fila = {
                    carrito.getCodigo(),
                    carrito.getUsuario().getCedula(),
                    fechaFormateada,
                    Formateador.formatearMoneda(carrito.calcularSubtotal(), locale),
                    Formateador.formatearMoneda(carrito.calcularIVA(), locale),
                    Formateador.formatearMoneda(carrito.calcularTotal(), locale)
            };
            modelo.addRow(fila);
        }
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
     * Cambia el idioma de todos los componentes de la vista utilizando el `MensajeInternacionalizacionHandler`.
     * Actualiza el título de la ventana, los textos de etiquetas, botones y los encabezados de la tabla.
     */
    public void cambiarIdioma() {
        setTitle(mi.get("carrito.lista.titulo.ventana"));
        lblCodigo.setText(mi.get("carrito.lista.buscar.codigo"));
        btnBuscar.setText(mi.get("carrito.lista.boton.buscar"));
        btnListar.setText(mi.get("carrito.lista.boton.listar"));
        btnVer.setText(mi.get("carrito.lista.boton.detalle"));
        Object[] columnas = {
                mi.get("carrito.lista.columna.codigo"),
                mi.get("carrito.lista.columna.usuario"),
                mi.get("carrito.lista.columna.fecha"),
                mi.get("carrito.lista.columna.subtotal"),
                mi.get("carrito.lista.columna.iva"),
                mi.get("carrito.lista.columna.total")
        };
        modelo.setColumnIdentifiers(columnas);
        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }

    /**
     * Inicializa las imágenes de los botones cargando los iconos desde las rutas de recursos especificadas.
     * Muestra un mensaje de error en la consola si un icono no se puede cargar.
     */
    public void inicializarImagenes() {
        URL listarUrl = CarritoListaView.class.getClassLoader().getResource("imagenes/listar.png");
        if (listarUrl != null) {
            btnListar.setIcon(new ImageIcon(listarUrl));
        } else {
            System.err.println("Error: No se pudo cargar el icono 'listar.png'");
        }

        URL buscarUrl = CarritoListaView.class.getClassLoader().getResource("imagenes/buscar.png");
        if (buscarUrl != null) {
            btnBuscar.setIcon(new ImageIcon(buscarUrl));
        } else {
            System.err.println("Error: No se pudo cargar el icono 'buscar.png'");
        }

        URL detalleUrl = CarritoListaView.class.getClassLoader().getResource("imagenes/detalles.png");
        if (detalleUrl != null) {
            btnVer.setIcon(new ImageIcon(detalleUrl));
        } else {
            System.err.println("Error: No se pudo cargar el icono 'detalles.png'");
        }
    }

    /**
     * Obtiene el panel principal de la vista.
     *
     * @return El JPanel principal.
     */
    public JPanel getPanelPrincipal() { return panelPrincipal; }

    /**
     * Establece el panel principal de la vista.
     *
     * @param panelPrincipal El JPanel a establecer.
     */
    public void setPanelPrincipal(JPanel panelPrincipal) { this.panelPrincipal = panelPrincipal; }

    /**
     * Obtiene el botón "Ver Detalles".
     *
     * @return El JButton para ver detalles.
     */
    public JButton getBtnVer() { return btnVer; }

    /**
     * Establece el botón "Ver Detalles".
     *
     * @param btnVer El JButton a establecer.
     */
    public void setBtnVer(JButton btnVer) { this.btnVer = btnVer; }

    /**
     * Obtiene la tabla de productos o carritos.
     *
     * @return La JTable utilizada en la vista.
     */
    public JTable getTblProducto() { return tblProducto; }

    /**
     * Establece la tabla de productos o carritos.
     *
     * @param tblProducto La JTable a establecer.
     */
    public void setTblProducto(JTable tblProducto) { this.tblProducto = tblProducto; }

    /**
     * Obtiene el campo de texto para el código de búsqueda.
     *
     * @return El JTextField para el código.
     */
    public JTextField getTxtCodigo() { return txtCodigo; }

    /**
     * Establece el campo de texto para el código de búsqueda.
     *
     * @param txtCodigo El JTextField a establecer.
     */
    public void setTxtCodigo(JTextField txtCodigo) { this.txtCodigo = txtCodigo; }

    /**
     * Obtiene el botón de búsqueda.
     *
     * @return El JButton de búsqueda.
     */
    public JButton getBtnBuscar() { return btnBuscar; }

    /**
     * Establece el botón de búsqueda.
     *
     * @param btnBuscar El JButton a establecer.
     */
    public void setBtnBuscar(JButton btnBuscar) { this.btnBuscar = btnBuscar; }

    /**
     * Obtiene el botón para listar todos los carritos.
     *
     * @return El JButton de listar.
     */
    public JButton getBtnListar() { return btnListar; }

    /**
     * Establece el botón para listar todos los carritos.
     *
     * @param btnListar El JButton a establecer.
     */
    public void setBtnListar(JButton btnListar) { this.btnListar = btnListar; }

    /**
     * Obtiene la etiqueta del código de búsqueda.
     *
     * @return El JLabel del código.
     */
    public JLabel getLblCodigo() { return lblCodigo; }

    /**
     * Establece la etiqueta del código de búsqueda.
     *
     * @param lblCodigo El JLabel a establecer.
     */
    public void setLblCodigo(JLabel lblCodigo) { this.lblCodigo = lblCodigo; }

    /**
     * Obtiene el modelo de tabla por defecto para la tabla.
     *
     * @return El DefaultTableModel de la tabla.
     */
    public DefaultTableModel getModelo() { return modelo; }

    /**
     * Establece el modelo de tabla por defecto para la tabla.
     *
     * @param modelo El DefaultTableModel a establecer.
     */
    public void setModelo(DefaultTableModel modelo) { this.modelo = modelo; }
}