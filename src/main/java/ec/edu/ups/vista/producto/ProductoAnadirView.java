package ec.edu.ups.vista.producto;

import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;

/**
 * La clase **ProductoAnadirView** representa la vista de la interfaz gráfica
 * para añadir nuevos productos al sistema. Permite al usuario ingresar el código,
 * nombre y precio de un producto, y ofrece opciones para guardar o limpiar los campos.
 * Soporta la internacionalización del texto y la carga de imágenes para los botones.
 */
public class ProductoAnadirView extends JInternalFrame{
    private JPanel panelPrincipal;
    private JButton btnAceptar;
    private JButton btnLimpiar;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JLabel lblAñadir;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private MensajeInternacionalizacionHandler mi;

    /**
     * Constructor para la vista ProductoAnadirView.
     * Inicializa la ventana interna, configura sus propiedades,
     * y establece el manejador de internacionalización.
     * Asocia un `ActionListener` al botón "Limpiar" para limpiar los campos de texto.
     * También, carga las traducciones y las imágenes de los botones.
     *
     * @param mi El manejador de internacionalización de mensajes para la vista.
     */
    public ProductoAnadirView(MensajeInternacionalizacionHandler mi) {
        setContentPane(panelPrincipal);
        setTitle("Datos del Productos");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setIconifiable(true);
        this.mi = mi;

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
        cambiarIdioma();
        inicializarImagenes();
    }

    /**
     * Muestra una lista de productos en la consola (para depuración o demostración).
     *
     * @param productos La lista de objetos Producto a mostrar.
     */
    public void mostrarProductos(List<Producto> productos) {
        for (Producto producto : productos) {
            System.out.println(producto);
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
     * Limpia los campos de texto de código, nombre y precio del producto.
     */
    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }

    /**
     * Cambia el idioma de todos los componentes de la vista utilizando el `MensajeInternacionalizacionHandler`.
     * Actualiza el título de la ventana, los textos de etiquetas y botones.
     * También actualiza los textos predeterminados de los botones de `JOptionPane`.
     */
    public void cambiarIdioma() {
        setTitle(mi.get("producto.anadir.titulo.ventana"));
        lblAñadir.setText(mi.get("producto.anadir.titulo"));
        lblCodigo.setText(mi.get("producto.anadir.codigo"));
        lblNombre.setText(mi.get("producto.anadir.nombre"));
        lblPrecio.setText(mi.get("producto.anadir.precio"));
        btnAceptar.setText(mi.get("producto.anadir.aceptar"));
        btnLimpiar.setText(mi.get("producto.anadir.limpiar"));

        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
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
     * Obtiene el botón "Aceptar".
     *
     * @return El JButton `btnAceptar`.
     */
    public JButton getBtnAceptar() {
        return btnAceptar;
    }

    /**
     * Establece el botón "Aceptar".
     *
     * @param btnAceptar El JButton a establecer.
     */
    public void setBtnAceptar(JButton btnAceptar) {
        this.btnAceptar = btnAceptar;
    }

    /**
     * Obtiene el botón "Limpiar".
     *
     * @return El JButton `btnLimpiar`.
     */
    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    /**
     * Establece el botón "Limpiar".
     *
     * @param btnLimpiar El JButton a establecer.
     */
    public void setBtnLimpiar(JButton btnLimpiar) {
        this.btnLimpiar = btnLimpiar;
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
     * Obtiene la etiqueta principal de "Añadir Producto".
     *
     * @return El JLabel `lblAñadir`.
     */
    public JLabel getLblTitulo() {
        return lblAñadir;
    }

    /**
     * Establece la etiqueta principal de "Añadir Producto".
     *
     * @param lblTitulo El JLabel a establecer.
     */
    public void setLblTitulo(JLabel lblTitulo) {
        this.lblAñadir = lblTitulo;
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
     * Inicializa las imágenes para los botones **Aceptar** y **Limpiar**
     * cargándolos desde la carpeta "imagenes" en los recursos del proyecto.
     * Si no se encuentran los recursos, se imprime un mensaje de error en la consola.
     */
    public void inicializarImagenes(){
        URL aceptar = ProductoAnadirView.class.getClassLoader().getResource("imagenes/aceptar.png");
        if (aceptar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(aceptar);
            btnAceptar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error, No se cargo el icono Login");
        }

        URL limpiar = ProductoAnadirView.class.getClassLoader().getResource("imagenes/limpiar.png");
        if (limpiar != null) {
            ImageIcon iconoBtnRegistrarse = new ImageIcon(limpiar);
            btnLimpiar.setIcon(iconoBtnRegistrarse);
        } else {
            System.err.println("Error, No se cargo el icono Registrarse");
        }
    }
}