package ec.edu.ups.vista.usuario;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import java.net.URL;

/**
 * La clase **EliminarUsuarioView** representa la vista de la interfaz gráfica de usuario
 * para la eliminación de usuarios en el sistema. Permite al usuario buscar un usuario
 * por su cédula, visualizar su información y, opcionalmente, eliminarlo.
 * Soporta la internacionalización del texto y la carga de imágenes para los botones.
 */
public class EliminarUsuarioView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtCedula;
    private JPasswordField txtContrasena;
    private JButton btnEliminar;
    private JButton btnBuscar;
    private JLabel lblEliminar;
    private JLabel lblCedula;
    private JLabel lblContrasena;
    private JTextField txtNombre;
    private JTextField txtCorreo;
    private JTextField txtTelefono;
    private JSpinner spnMes;
    private JSpinner spnAno;
    private JSpinner spnDia;
    private JLabel lblNombre;
    private JLabel lblFecha;
    private JLabel lblTelefono;
    private JLabel lblCorreo;
    private MensajeInternacionalizacionHandler mi;

    /**
     * Constructor para la vista EliminarUsuarioView.
     * Inicializa la ventana interna, configura sus propiedades,
     * establece el manejador de internacionalización, y carga las imágenes.
     * Los campos de detalles del usuario se deshabilitan inicialmente.
     *
     * @param mi El manejador de internacionalización de mensajes para la vista.
     */
    public EliminarUsuarioView(MensajeInternacionalizacionHandler mi) {
        super("Eliminar Usuarios", true, true, false, true);
        this.mi = mi;
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        cambiarIdioma();
        inicializarImagenes();
        habilitarCampos(false);
    }

    /**
     * Limpia todos los campos de texto y restablece los valores de los spinners de fecha
     * a sus valores predeterminados.
     */
    public void limpiarCampos() {
        txtCedula.setText("");
        txtContrasena.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        spnDia.setValue(1);
        spnMes.setValue(1);
        spnAno.setValue(2000);
    }

    /**
     * Habilita o deshabilita los campos de entrada y spinners para los detalles del usuario.
     *
     * @param habilitar true para habilitar los campos, false para deshabilitarlos.
     */
    public void habilitarCampos(boolean habilitar) {
        txtContrasena.setEnabled(false);
        txtNombre.setEnabled(false);
        txtTelefono.setEnabled(false);
        txtCorreo.setEnabled(false);
        spnDia.setEnabled(false);
        spnMes.setEnabled(false);
        spnAno.setEnabled(false);
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
     * Obtiene el panel principal de la vista.
     *
     * @return El JPanel `panelPrincipal`.
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
     * Obtiene el campo de texto para la cédula del usuario.
     *
     * @return El JTextField `txtCedula`.
     */
    public JTextField getTxtCedula() {
        return txtCedula;
    }

    /**
     * Establece el campo de texto para la cédula del usuario.
     *
     * @param txtCedula El JTextField a establecer.
     */
    public void setTxtCedula(JTextField txtCedula) {
        this.txtCedula = txtCedula;
    }

    /**
     * Obtiene el campo de contraseña para el usuario.
     *
     * @return El JPasswordField `txtContrasena`.
     */
    public JPasswordField getTxtContrasena() {
        return txtContrasena;
    }

    /**
     * Establece el campo de contraseña para el usuario.
     *
     * @param txtContrasena El JPasswordField a establecer.
     */
    public void setTxtContrasena(JPasswordField txtContrasena) {
        this.txtContrasena = txtContrasena;
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

    /**
     * Obtiene la etiqueta principal de "Eliminar Usuario".
     *
     * @return El JLabel `lblEliminar`.
     */
    public JLabel getLblEliminar() { return lblEliminar; }

    /**
     * Establece la etiqueta principal de "Eliminar Usuario".
     *
     * @param lblEliminar El JLabel a establecer.
     */
    public void setLblEliminar(JLabel lblEliminar) { this.lblEliminar = lblEliminar; }

    /**
     * Obtiene la etiqueta para la cédula del usuario.
     *
     * @return El JLabel `lblCedula`.
     */
    public JLabel getLblCedula() { return lblCedula; }

    /**
     * Establece la etiqueta para la cédula del usuario.
     *
     * @param lblCedula El JLabel a establecer.
     */
    public void setLblCedula(JLabel lblCedula) { this.lblCedula = lblCedula; }

    /**
     * Obtiene la etiqueta para la contraseña del usuario.
     *
     * @return El JLabel `lblContrasena`.
     */
    public JLabel getLblContrasena() { return lblContrasena; }

    /**
     * Establece la etiqueta para la contraseña del usuario.
     *
     * @param lblContrasena El JLabel a establecer.
     */
    public void setLblContrasena(JLabel lblContrasena) { this.lblContrasena = lblContrasena; }

    /**
     * Obtiene el campo de texto para el nombre del usuario.
     *
     * @return El JTextField `txtNombre`.
     */
    public JTextField getTxtNombre() { return txtNombre; }

    /**
     * Establece el campo de texto para el nombre del usuario.
     *
     * @param txtNombre El JTextField a establecer.
     */
    public void setTxtNombre(JTextField txtNombre) { this.txtNombre = txtNombre; }

    /**
     * Obtiene el campo de texto para el correo electrónico del usuario.
     *
     * @return El JTextField `txtCorreo`.
     */
    public JTextField getTxtCorreo() { return txtCorreo; }

    /**
     * Establece el campo de texto para el correo electrónico del usuario.
     *
     * @param txtCorreo El JTextField a establecer.
     */
    public void setTxtCorreo(JTextField txtCorreo) { this.txtCorreo = txtCorreo; }

    /**
     * Obtiene el campo de texto para el número de teléfono del usuario.
     *
     * @return El JTextField `txtTelefono`.
     */
    public JTextField getTxtTelefono() { return txtTelefono; }

    /**
     * Establece el campo de texto para el número de teléfono del usuario.
     *
     * @param txtTelefono El JTextField a establecer.
     */
    public void setTxtTelefono(JTextField txtTelefono) { this.txtTelefono = txtTelefono; }

    /**
     * Obtiene el spinner para el mes de la fecha de nacimiento.
     *
     * @return El JSpinner `spnMes`.
     */
    public JSpinner getSpnMes() { return spnMes; }

    /**
     * Establece el spinner para el mes de la fecha de nacimiento.
     *
     * @param spnMes El JSpinner a establecer.
     */
    public void setSpnMes(JSpinner spnMes) { this.spnMes = spnMes; }

    /**
     * Obtiene el spinner para el año de la fecha de nacimiento.
     *
     * @return El JSpinner `spnAno`.
     */
    public JSpinner getSpnAno() { return spnAno; }

    /**
     * Establece el spinner para el año de la fecha de nacimiento.
     *
     * @param spnAno El JSpinner a establecer.
     */
    public void setSpnAno(JSpinner spnAno) { this.spnAno = spnAno; }

    /**
     * Obtiene el spinner para el día de la fecha de nacimiento.
     *
     * @return El JSpinner `spnDia`.
     */
    public JSpinner getSpnDia() { return spnDia; }

    /**
     * Establece el spinner para el día de la fecha de nacimiento.
     *
     * @param spnDia El JSpinner a establecer.
     */
    public void setSpnDia(JSpinner spnDia) { this.spnDia = spnDia; }

    /**
     * Obtiene la etiqueta para el nombre del usuario.
     *
     * @return El JLabel `lblNombre`.
     */
    public JLabel getLblNombre() { return lblNombre; }

    /**
     * Establece la etiqueta para el nombre del usuario.
     *
     * @param lblNombre El JLabel a establecer.
     */
    public void setLblNombre(JLabel lblNombre) { this.lblNombre = lblNombre; }

    /**
     * Obtiene la etiqueta para la fecha de nacimiento.
     *
     * @return El JLabel `lblFecha`.
     */
    public JLabel getLblFecha() { return lblFecha; }

    /**
     * Establece la etiqueta para la fecha de nacimiento.
     *
     * @param lblFecha El JLabel a establecer.
     */
    public void setLblFecha(JLabel lblFecha) { this.lblFecha = lblFecha; }

    /**
     * Obtiene la etiqueta para el número de teléfono.
     *
     * @return El JLabel `lblTelefono`.
     */
    public JLabel getLblTelefono() { return lblTelefono; }

    /**
     * Establece la etiqueta para el número de teléfono.
     *
     * @param lblTelefono El JLabel a establecer.
     */
    public void setLblTelefono(JLabel lblTelefono) { this.lblTelefono = lblTelefono; }

    /**
     * Obtiene la etiqueta para el correo electrónico.
     *
     * @return El JLabel `lblCorreo`.
     */
    public JLabel getLblCorreo() { return lblCorreo; }

    /**
     * Establece la etiqueta para el correo electrónico.
     *
     * @param lblCorreo El JLabel a establecer.
     */
    public void setLblCorreo(JLabel lblCorreo) { this.lblCorreo = lblCorreo; }

    /**
     * Cambia el idioma de todos los componentes de la vista utilizando el `MensajeInternacionalizacionHandler`.
     * Actualiza el título de la ventana, los textos de etiquetas y botones.
     * También actualiza los textos predeterminados de los botones de `JOptionPane`.
     */
    public void cambiarIdioma() {
        mi.setLenguaje(mi.getLocale().getLanguage(), mi.getLocale().getCountry());

        setTitle(mi.get("usuario.eliminar.titulo.ventana"));
        lblEliminar.setText(mi.get("usuario.eliminar.titulo"));
        lblCedula.setText(mi.get("usuario.eliminar.cedula"));
        lblContrasena.setText(mi.get("usuario.eliminar.contrasena"));
        lblNombre.setText(mi.get("usuario.eliminar.nombre"));
        lblFecha.setText(mi.get("usuario.eliminar.fecha"));
        lblTelefono.setText(mi.get("usuario.eliminar.telefono"));
        lblCorreo.setText(mi.get("usuario.eliminar.correo"));

        btnBuscar.setText(mi.get("usuario.eliminar.buscar"));
        btnEliminar.setText(mi.get("usuario.eliminar.eliminar"));

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
        URL buscarUrl = getClass().getClassLoader().getResource("imagenes/buscar.png");
        if (buscarUrl != null) {
            btnBuscar.setIcon(new ImageIcon(buscarUrl));
        } else {
            System.err.println("Error: No se pudo cargar el icono 'buscar.png'");
        }

        URL eliminarUrl = getClass().getClassLoader().getResource("imagenes/eliminar.png");
        if (eliminarUrl != null) {
            btnEliminar.setIcon(new ImageIcon(eliminarUrl));
        } else {
            System.err.println("Error: No se pudo cargar el icono 'eliminar.png'");
        }
    }
}