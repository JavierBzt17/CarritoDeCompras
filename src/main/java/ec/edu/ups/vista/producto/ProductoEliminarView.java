package ec.edu.ups.vista.producto;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URL;

/**
 * La clase **ProductoEliminarView** representa la vista de la interfaz gráfica
 * para eliminar productos del sistema. Permite al usuario buscar un producto por su código,
 * visualizar su información (nombre y precio) y luego proceder a eliminarlo.
 * Soporta la internacionalización del texto y la carga de imágenes para los botones.
 */
public class ProductoEliminarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JLabel lblEliminar;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JButton btnEliminar;
    private JLabel lblPrecio;
    private JLabel lblNombre;
    private JLabel lblCodigo;
    private JButton btnBuscar;
    private JTextField txtStock;
    private JLabel lblStock;
    private MensajeInternacionalizacionHandler mi;

    /**
     * Constructor para la vista ProductoEliminarView.
     * Inicializa la ventana interna, configura sus propiedades,
     * y establece el manejador de internacionalización.
     * También, carga las traducciones y las imágenes de los botones.
     *
     * @param mi El manejador de internacionalización de mensajes para la vista.
     */
    public ProductoEliminarView(MensajeInternacionalizacionHandler mi){
        setContentPane(panelPrincipal);
        setTitle("Edición de Productos");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setIconifiable(true);
        this.mi = mi;
        cambiarIdioma();
        inicializarImagenes();
    }

    /**
     * Muestra un cuadro de diálogo de confirmación con una pregunta y devuelve la respuesta del usuario.
     * El título del diálogo es "Confirmación".
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
     * Muestra un mensaje informativo en un cuadro de diálogo.
     *
     * @param mensaje El mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Limpia los campos de texto del nombre y precio del producto.
     */
    public void limpiarCampos() {
        txtNombre.setText("");
        txtPrecio.setText("");
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
     * Obtiene la etiqueta principal de "Eliminar Producto".
     *
     * @return El JLabel `lblEliminar`.
     */
    public JLabel getLblEliminar() {
        return lblEliminar;
    }

    /**
     * Establece la etiqueta principal de "Eliminar Producto".
     *
     * @param lblEliminar El JLabel a establecer.
     */
    public void setLblEliminar(JLabel lblEliminar) {
        this.lblEliminar = lblEliminar;
    }

    /**
     * Obtiene el campo de texto para el código del producto.
     *
     * @return El JTextField `txtCodigo`.
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
     * Obtiene el campo de texto para el nombre del producto.
     *
     * @return El JTextField `txtNombre`.
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
     * @return El JTextField `txtPrecio`.
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
     * Obtiene el botón "Eliminar".
     *
     * @return El JButton `btnEliminar`.
     */
    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    /**
     * Establece el botón "Eliminar".
     *
     * @param btnEliminar El JButton a establecer.
     */
    public void setBtnEliminar(JButton btnEliminar) {
        this.btnEliminar = btnEliminar;
    }

    /**
     * Obtiene la etiqueta del precio del producto.
     *
     * @return El JLabel `lblPrecio`.
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
     * Obtiene la etiqueta del nombre del producto.
     *
     * @return El JLabel `lblNombre`.
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
     * Obtiene la etiqueta del código del producto.
     *
     * @return El JLabel `lblCodigo`.
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
     * Obtiene el botón "Buscar".
     *
     * @return El JButton `btnBuscar`.
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Establece el botón "Buscar".
     *
     * @param btnBuscar El JButton a establecer.
     */
    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JTextField getTxtStock() {
        return txtStock;
    }

    public void setTxtStock(JTextField txtStock) {
        this.txtStock = txtStock;
    }

    public JLabel getLblStock() {
        return lblStock;
    }

    public void setLblStock(JLabel lblStock) {
        this.lblStock = lblStock;
    }

    /**
     * Cambia el idioma de todos los componentes de la vista utilizando el `MensajeInternacionalizacionHandler`.
     * Actualiza el título de la ventana, los textos de etiquetas y botones.
     * También actualiza los textos predeterminados de los botones de `JOptionPane`.
     */
    public void cambiarIdioma() {
        if (mi == null) return;

        setTitle(mi.get("producto.eliminar.titulo.ventana"));

        if (lblCodigo != null) lblCodigo.setText(mi.get("producto.eliminar.codigo"));
        if (lblNombre != null) lblNombre.setText(mi.get("producto.eliminar.nombre"));
        if (lblPrecio != null) lblPrecio.setText(mi.get("producto.eliminar.precio"));

        if (btnBuscar != null) btnBuscar.setText(mi.get("producto.eliminar.buscar"));
        if (btnEliminar != null) btnEliminar.setText(mi.get("producto.eliminar.eliminar"));

        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }

    /**
     * Inicializa las imágenes para los botones **Buscar** y **Eliminar**
     * cargándolos desde la carpeta "imagenes" en los recursos del proyecto.
     * Si no se encuentran los recursos, se imprime un mensaje de error en la consola.
     */
    public void inicializarImagenes(){
        URL buscar = ProductoEditarView.class.getClassLoader().getResource("imagenes/buscar.png");
        if (buscar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(buscar);
            btnBuscar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error, No se cargo el icono Login");
        }

        URL eliminar = ProductoEditarView.class.getClassLoader().getResource("imagenes/eliminar.png");
        if (eliminar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(eliminar);
            btnEliminar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error, No se ha cargo el icono Login");
        }
    }
}