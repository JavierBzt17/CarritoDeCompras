package ec.edu.ups.vista.producto;

import ec.edu.ups.modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
import java.util.List;

import ec.edu.ups.util.Formateador;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

/**
 * La clase **ProductoListaView** representa la vista de la interfaz gráfica
 * para listar productos. Permite al usuario buscar productos por nombre o
 * listar todos los productos disponibles en una tabla.
 * Soporta la internacionalización del texto y la carga de imágenes para los botones.
 */
public class ProductoListaView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtNombre;
    private JButton btnBuscar;
    private JButton btnListar;
    private JTable tblProductos;
    private JLabel lblNombre;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mi;

    /**
     * Constructor para la vista ProductoListaView.
     * Inicializa la ventana interna, configura sus propiedades,
     * y establece el manejador de internacionalización.
     * También, carga las traducciones y las imágenes de los botones.
     *
     * @param mi El manejador de internacionalización de mensajes para la vista.
     */
    public ProductoListaView(MensajeInternacionalizacionHandler mi) {
        setContentPane(panelPrincipal);
        setTitle("Listado de Productos");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setIconifiable(true);

        modelo = new DefaultTableModel();
        tblProductos.setModel(modelo);

        this.mi = mi;
        cambiarIdioma();
        inicializarImagenes();
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
     * @param panelPrincipal El JPanel a establecer.
     */
    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    /**
     * Obtiene el campo de texto para buscar productos por nombre.
     *
     * @return El JTextField `txtNombre`.
     */
    public JTextField getTxtBuscar() {
        return txtNombre;
    }

    /**
     * Establece el campo de texto para buscar productos por nombre.
     *
     * @param txtBuscar El JTextField a establecer.
     */
    public void setTxtBuscar(JTextField txtBuscar) {
        this.txtNombre = txtBuscar;
    }

    /**
     * Obtiene el botón de búsqueda.
     *
     * @return El JButton `btnBuscar`.
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
     * Obtiene el botón para listar todos los productos.
     *
     * @return El JButton `btnListar`.
     */
    public JButton getBtnListar() {
        return btnListar;
    }

    /**
     * Establece el botón para listar todos los productos.
     *
     * @param btnListar El JButton a establecer.
     */
    public void setBtnListar(JButton btnListar) {
        this.btnListar = btnListar;
    }

    /**
     * Obtiene la tabla de productos.
     *
     * @return La JTable `tblProductos`.
     */
    public JTable getTblProductos() {
        return tblProductos;
    }

    /**
     * Establece la tabla de productos.
     *
     * @param tblProductos La JTable a establecer.
     */
    public void setTblProductos(JTable tblProductos) {
        this.tblProductos = tblProductos;
    }

    /**
     * Obtiene la etiqueta para el nombre del producto.
     *
     * @return El JLabel `lblNombre`.
     */
    public JLabel getLblNombre() {
        return lblNombre;
    }

    /**
     * Establece la etiqueta para el nombre del producto.
     *
     * @param lblNombre El JLabel a establecer.
     */
    public void setLblNombre(JLabel lblNombre) {
        this.lblNombre = lblNombre;
    }

    /**
     * Obtiene el modelo de tabla por defecto para la tabla de productos.
     *
     * @return El DefaultTableModel `modelo`.
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
     * Actualiza el título de la ventana, los textos de etiquetas, botones y los encabezados de la tabla.
     * También actualiza los textos predeterminados de los botones de `JOptionPane`.
     */
    public void cambiarIdioma() {
        mi.setLenguaje(mi.getLocale().getLanguage(), mi.getLocale().getCountry());

        setTitle(mi.get("producto.lista.titulo.ventana"));
        lblNombre.setText(mi.get("producto.lista.nombre"));
        btnBuscar.setText(mi.get("producto.lista.boton.buscar"));
        btnListar.setText(mi.get("producto.lista.boton.listar"));

        modelo.setColumnIdentifiers(new String[] {
                mi.get("producto.lista.tabla.codigo"),
                mi.get("producto.lista.tabla.nombre"),
                mi.get("producto.lista.tabla.precio")
        });

        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }

    /**
     * Carga los datos de una lista de objetos **Producto** en la tabla.
     * Limpia la tabla existente y añade filas con el código, nombre y precio de cada producto,
     * formateando el precio como moneda según la configuración regional.
     *
     * @param listaProductos La lista de objetos **Producto** a mostrar en la tabla.
     */
    public void cargarDatos(List<Producto> listaProductos) {
        modelo.setNumRows(0);
        for (Producto producto : listaProductos) {
            Object[] fila = {
                    producto.getCodigo(),
                    producto.getNombre(),
                    Formateador.formatearMoneda(producto.getPrecio(), mi.getLocale())
            };
            modelo.addRow(fila);
        }
    }

    /**
     * Inicializa las imágenes para los botones **Buscar** y **Listar**
     * cargándolos desde la carpeta "imagenes" en los recursos del proyecto.
     * Si no se encuentran los recursos, se imprime un mensaje de error en la consola.
     */
    public void inicializarImagenes(){
        URL buscar = ProductoListaView.class.getClassLoader().getResource("imagenes/buscar.png");
        if (buscar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(buscar);
            btnBuscar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error, No se cargo el icono Login");
        }
        URL listar = ProductoListaView.class.getClassLoader().getResource("imagenes/listar.png");
        if (listar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(listar);
            btnListar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error, No se cargo el icono Login");
        }
    }
}