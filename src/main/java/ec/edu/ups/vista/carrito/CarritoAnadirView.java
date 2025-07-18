package ec.edu.ups.vista.carrito;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

/**
 * La clase **CarritoAnadirView** representa la vista de la interfaz gráfica
 * para añadir productos a un carrito de compras. Permite buscar productos,
 * añadirlos a una tabla, ajustar cantidades, y ver subtotales, IVA y el total.
 * También soporta la internacionalización del texto y la carga de imágenes.
 */
public class CarritoAnadirView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JButton btnAnadir;
    private JTable tblProductos;
    private JTextField txtSubtotal;
    private JTextField txtIVA;
    private JTextField txtTotal;
    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JComboBox cbxCantidad;
    private JButton btnBorrar;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JLabel lblCantidad;
    private JLabel lblSubtotal;
    private JLabel lblIVA;
    private JLabel lblTotal;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mi;

    /**
     * Constructor para la vista CarritoAnadirView.
     * Inicializa los componentes de la interfaz, configura la tabla de productos,
     * carga datos iniciales, aplica el idioma y las imágenes.
     *
     * @param mi El manejador de internacionalización de mensajes para la vista.
     */
    public CarritoAnadirView(MensajeInternacionalizacionHandler mi) {
        super("Carrito de Compras", true, true, false, true);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(700, 700);

        modelo = new DefaultTableModel();
        tblProductos.setModel(modelo);

        cargarDatos();
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
     * @param panelPrincipal El JPanel a establecer como principal.
     */
    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    /**
     * Obtiene el campo de texto para el código del producto.
     *
     * @return El JTextField para el código.
     */
    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    /**
     * Establece el campo de texto para el código del producto.
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
     * Obtiene el campo de texto para el nombre del producto.
     *
     * @return El JTextField para el nombre.
     */
    public JTextField getTxtNombre() {
        return txtNombre;
    }

    /**
     * Establece el campo de texto para el nombre del producto.
     *
     * @param txtNombre El JTextField a establecer.
     */
    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    /**
     * Obtiene el campo de texto para el precio del producto.
     *
     * @return El JTextField para el precio.
     */
    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    /**
     * Establece el campo de texto para el precio del producto.
     *
     * @param txtPrecio El JTextField a establecer.
     */
    public void setTxtPrecio(JTextField txtPrecio) {
        this.txtPrecio = txtPrecio;
    }

    /**
     * Obtiene el botón de añadir producto al carrito.
     *
     * @return El JButton de añadir.
     */
    public JButton getBtnAnadir() {
        return btnAnadir;
    }

    /**
     * Establece el botón de añadir producto al carrito.
     *
     * @param btnAnadir El JButton a establecer.
     */
    public void setBtnAnadir(JButton btnAnadir) {
        this.btnAnadir = btnAnadir;
    }

    /**
     * Obtiene la tabla de productos en el carrito.
     *
     * @return La JTable de productos.
     */
    public JTable getTblProductos() {
        return tblProductos;
    }

    /**
     * Establece la tabla de productos en el carrito.
     *
     * @param tblProductos La JTable a establecer.
     */
    public void setTblProductos(JTable tblProductos) {
        this.tblProductos = tblProductos;
    }

    /**
     * Obtiene el campo de texto para el subtotal.
     *
     * @return El JTextField para el subtotal.
     */
    public JTextField getTxtSubtotal() {
        return txtSubtotal;
    }

    /**
     * Establece el campo de texto para el subtotal.
     *
     * @param txtSubtotal El JTextField a establecer.
     */
    public void setTxtSubtotal(JTextField txtSubtotal) {
        this.txtSubtotal = txtSubtotal;
    }

    /**
     * Obtiene el campo de texto para el IVA.
     *
     * @return El JTextField para el IVA.
     */
    public JTextField getTxtIVA() {
        return txtIVA;
    }

    /**
     * Establece el campo de texto para el IVA.
     *
     * @param txtIVA El JTextField a establecer.
     */
    public void setTxtIVA(JTextField txtIVA) {
        this.txtIVA = txtIVA;
    }

    /**
     * Obtiene el campo de texto para el total.
     *
     * @return El JTextField para el total.
     */
    public JTextField getTxtTotal() {
        return txtTotal;
    }

    /**
     * Establece el campo de texto para el total.
     *
     * @param txtTotal El JTextField a establecer.
     */
    public void setTxtTotal(JTextField txtTotal) {
        this.txtTotal = txtTotal;
    }

    /**
     * Obtiene el botón para guardar o aceptar.
     *
     * @return El JButton de guardar.
     */
    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    /**
     * Establece el botón para guardar o aceptar.
     *
     * @param btnGuardar El JButton a establecer.
     */
    public void setBtnGuardar(JButton btnGuardar) {
        this.btnGuardar = btnGuardar;
    }

    /**
     * Obtiene el botón para limpiar campos.
     *
     * @return El JButton de limpiar.
     */
    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    /**
     * Establece el botón para limpiar campos.
     *
     * @param btnLimpiar El JButton a establecer.
     */
    public void setBtnLimpiar(JButton btnLimpiar) {
        this.btnLimpiar = btnLimpiar;
    }

    /**
     * Obtiene el JComboBox para seleccionar la cantidad.
     *
     * @return El JComboBox de cantidad.
     */
    public JComboBox getCbxCantidad() {
        return cbxCantidad;
    }

    /**
     * Establece el JComboBox para seleccionar la cantidad.
     *
     * @param cbxCantidad El JComboBox a establecer.
     */
    public void setCbxCantidad(JComboBox cbxCantidad) {
        this.cbxCantidad = cbxCantidad;
    }

    /**
     * Obtiene el botón para borrar un ítem del carrito.
     *
     * @return El JButton de borrar.
     */
    public JButton getBtnBorrar() {
        return btnBorrar;
    }

    /**
     * Establece el botón para borrar un ítem del carrito.
     *
     * @param btnBorrar El JButton a establecer.
     */
    public void setBtnBorrar(JButton btnBorrar) {
        this.btnBorrar = btnBorrar;
    }

    /**
     * Obtiene la etiqueta del código del producto.
     *
     * @return El JLabel del código.
     */
    public JLabel getLblCodigo() {
        return lblCodigo;
    }

    /**
     * Establece la etiqueta del código del producto.
     *
     * @param lblCodigo El JLabel a establecer.
     */
    public void setLblCodigo(JLabel lblCodigo) {
        this.lblCodigo = lblCodigo;
    }

    /**
     * Obtiene la etiqueta del nombre del producto.
     *
     * @return El JLabel del nombre.
     */
    public JLabel getLblNombre() {
        return lblNombre;
    }

    /**
     * Establece la etiqueta del nombre del producto.
     *
     * @param lblNombre El JLabel a establecer.
     */
    public void setLblNombre(JLabel lblNombre) {
        this.lblNombre = lblNombre;
    }

    /**
     * Obtiene la etiqueta del precio del producto.
     *
     * @return El JLabel del precio.
     */
    public JLabel getLblPrecio() {
        return lblPrecio;
    }

    /**
     * Establece la etiqueta del precio del producto.
     *
     * @param lblPrecio El JLabel a establecer.
     */
    public void setLblPrecio(JLabel lblPrecio) {
        this.lblPrecio = lblPrecio;
    }

    /**
     * Obtiene la etiqueta de la cantidad del producto.
     *
     * @return El JLabel de cantidad.
     */
    public JLabel getLblCantidad() {
        return lblCantidad;
    }

    /**
     * Establece la etiqueta de la cantidad del producto.
     *
     * @param lblCantidad El JLabel a establecer.
     */
    public void setLblCantidad(JLabel lblCantidad) {
        this.lblCantidad = lblCantidad;
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
     * Carga los datos iniciales en el JComboBox de cantidad, poblándolo con números del 1 al 20.
     */
    private void cargarDatos() {
        cbxCantidad.removeAllItems();
        for (int i = 0; i < 20; i++) {
            cbxCantidad.addItem(String.valueOf(i + 1));
        }
    }

    /**
     * Cambia el idioma de todos los componentes de la vista utilizando el `MensajeInternacionalizacionHandler`.
     * Actualiza los textos de etiquetas, botones y encabezados de la tabla.
     */
    public void cambiarIdioma() {
        mi.setLenguaje(mi.getLocale().getLanguage(), mi.getLocale().getCountry());

        try {
            setTitle(mi.get("carrito.anadir.titulo"));
            lblCodigo.setText(mi.get("carrito.anadir.buscar.codigo"));
            lblNombre.setText(mi.get("carrito.anadir.nombre"));
            lblPrecio.setText(mi.get("carrito.anadir.precio"));
            lblCantidad.setText(mi.get("carrito.anadir.cantidad"));
            lblSubtotal.setText(mi.get("carrito.anadir.subtotal"));
            lblIVA.setText(mi.get("carrito.anadir.iva"));
            lblTotal.setText(mi.get("carrito.anadir.total"));

            btnBuscar.setText(mi.get("carrito.anadir.boton.buscar"));
            btnAnadir.setText(mi.get("carrito.anadir.boton.anadir"));
            btnGuardar.setText(mi.get("carrito.anadir.boton.aceptar"));
            btnLimpiar.setText(mi.get("carrito.anadir.boton.limpiar"));
            btnBorrar.setText(mi.get("carrito.anadir.boton.borrar"));

            modelo.setColumnIdentifiers(new Object[]{
                    mi.get("carrito.anadir.tabla.codigo"),
                    mi.get("carrito.anadir.tabla.nombre"),
                    mi.get("carrito.anadir.tabla.precio"),
                    mi.get("carrito.anadir.tabla.cantidad"),
                    mi.get("carrito.anadir.tabla.subtotal")
            });

            UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
            UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
            UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
            UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));

        } catch (Exception e) {
            System.err.println(" Error de clave de idioma: " + e.getMessage());
        }
    }

    /**
     * Inicializa las imágenes de los botones cargando los iconos desde las rutas especificadas.
     */
    public void inicializarImagenes() {
        cargarIcono("imagenes/aceptar.png", btnGuardar, "guardar");
        cargarIcono("imagenes/limpiar.png", btnLimpiar, "limpiar");
        cargarIcono("imagenes/anadir.png", btnAnadir, "añadir");
        cargarIcono("imagenes/borrar.png", btnBorrar, "borrar");
        cargarIcono("imagenes/buscar.png", btnBuscar, "buscar");
    }

    /**
     * Carga un icono desde una ruta de recurso y lo asigna a un botón.
     * Si el recurso no se encuentra, imprime un mensaje de error en la consola.
     *
     * @param ruta La ruta del archivo de imagen (ej. "imagenes/icono.png").
     * @param boton El JButton al que se le asignará el icono.
     * @param nombre El nombre descriptivo del icono para mensajes de error.
     */
    private void cargarIcono(String ruta, JButton boton, String nombre) {
        URL recurso = getClass().getClassLoader().getResource(ruta);
        if (recurso != null) {
            boton.setIcon(new ImageIcon(recurso));
        } else {
            System.err.println("Error: No se cargo el icono " + nombre);
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
     * Limpia los campos de texto del nombre y precio del producto.
     */
    public void limpiarCampos() {
        txtNombre.setText("");
        txtPrecio.setText("");
    }

}