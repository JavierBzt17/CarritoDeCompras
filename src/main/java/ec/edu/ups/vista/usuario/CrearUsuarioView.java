package ec.edu.ups.vista.usuario;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Calendar; // Importación necesaria

/**
 * La clase **CrearUsuarioView** representa la vista de la interfaz gráfica de usuario
 * para el registro de nuevos usuarios en el sistema. Permite al usuario ingresar
 * su cédula, contraseña, nombre, teléfono, correo y fecha de nacimiento.
 * Soporta la internacionalización del texto y la carga de imágenes para los botones.
 */
public class CrearUsuarioView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtCedula;
    private JPasswordField txtContrasena;
    private JButton btnAceptar;
    private JButton btnLimpiar;
    private JLabel lblRegistrar;
    private JTextField txtCorreo;
    private JTextField txtTelefono;
    private JTextField txtNombre;
    private JSpinner spnDia;
    private JSpinner spnMes;
    private JSpinner spnAno;
    private JLabel lblNombre;
    private JLabel lblFecha;
    private JLabel lblTelefono;
    private JLabel lblCorreo;
    private JLabel lblCedula;
    private JLabel lblContrasena;
    private MensajeInternacionalizacionHandler mi;

    /**
     * Constructor para la vista CrearUsuarioView.
     * Inicializa la ventana interna, configura sus propiedades,
     * y establece el manejador de internacionalización.
     * Configura los modelos de los spinners para la fecha de nacimiento.
     * Asocia un `ActionListener` al botón "Limpiar" para limpiar los campos de texto.
     * También, carga las traducciones y las imágenes de los botones.
     *
     * @param mi El manejador de internacionalización de mensajes para la vista.
     */
    public CrearUsuarioView(MensajeInternacionalizacionHandler mi) {
        super("Registrar Usuarios", true,true,false,true);
        this.mi = mi;
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(700, 700);

        int anioActual = Calendar.getInstance().get(Calendar.YEAR);
        spnDia.setModel(new SpinnerNumberModel(1, 1, 31, 1));
        spnMes.setModel(new SpinnerNumberModel(1, 1, 12, 1));
        spnAno.setModel(new SpinnerNumberModel(2000, 1920, anioActual, 1));

        btnLimpiar.addActionListener(e -> limpiarCampos());
        cambiarIdioma();
        inicializarImagenes();
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
     * Obtiene el botón "Aceptar".
     *
     * @return El JButton `btnAceptar`.
     */
    public JButton getBtnAceptar() { return btnAceptar; }

    /**
     * Establece el botón "Aceptar".
     *
     * @param btnAceptar El JButton a establecer.
     */
    public void setBtnAceptar(JButton btnAceptar) { this.btnAceptar = btnAceptar; }

    /**
     * Obtiene el botón "Limpiar".
     *
     * @return El JButton `btnLimpiar`.
     */
    public JButton getBtnLimpiar() { return btnLimpiar; }

    /**
     * Establece el botón "Limpiar".
     *
     * @param btnLimpiar El JButton a establecer.
     */
    public void setBtnLimpiar(JButton btnLimpiar) { this.btnLimpiar = btnLimpiar; }

    /**
     * Obtiene la etiqueta de "Registrar Usuario".
     *
     * @return El JLabel `lblRegistrar`.
     */
    public JLabel getLblRegistrar() { return lblRegistrar; }

    /**
     * Establece la etiqueta de "Registrar Usuario".
     *
     * @param lblRegistrar El JLabel a establecer.
     */
    public void setLblRegistrar(JLabel lblRegistrar) { this.lblRegistrar = lblRegistrar; }

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
     * Inicializa las imágenes para los botones **Aceptar** y **Limpiar**
     * cargándolos desde la carpeta "imagenes" en los recursos del proyecto.
     * Si no se encuentran los recursos, se imprime un mensaje de error en la consola.
     */
    public void inicializarImagenes(){
        URL aceptarUrl = getClass().getClassLoader().getResource("imagenes/aceptar.png");
        if (aceptarUrl != null) {
            btnAceptar.setIcon(new ImageIcon(aceptarUrl));
        } else {
            System.err.println("Error: No se pudo cargar el icono 'aceptar.png'");
        }

        URL limpiarUrl = getClass().getClassLoader().getResource("imagenes/limpiar.png");
        if (limpiarUrl != null) {
            btnLimpiar.setIcon(new ImageIcon(limpiarUrl));
        } else {
            System.err.println("Error: No se pudo cargar el icono 'limpiar.png'");
        }
    }

    /**
     * Cambia el idioma de todos los componentes de la vista utilizando el `MensajeInternacionalizacionHandler`.
     * Actualiza el título de la ventana, los textos de etiquetas y botones.
     * También actualiza los textos predeterminados de los botones de `JOptionPane`.
     */
    public void cambiarIdioma() {
        setTitle(mi.get("usuario.crear.titulo.ventana"));
        lblRegistrar.setText(mi.get("usuario.crear.titulo"));
        lblCedula.setText(mi.get("usuario.crear.cedula"));
        lblContrasena.setText(mi.get("usuario.crear.contrasena"));
        lblNombre.setText(mi.get("usuario.crear.nombre"));
        lblFecha.setText(mi.get("usuario.crear.fecha"));
        lblTelefono.setText(mi.get("usuario.crear.telefono"));
        lblCorreo.setText(mi.get("usuario.crear.correo"));

        btnAceptar.setText(mi.get("usuario.crear.aceptar"));
        btnLimpiar.setText(mi.get("usuario.crear.limpiar"));

        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }
}