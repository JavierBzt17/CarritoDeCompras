package ec.edu.ups.vista.usuario;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URL;
import java.util.Calendar;

/**
 * La clase **ModificarUsuarioView** representa la vista de la interfaz gráfica de usuario
 * para modificar la información de usuarios existentes. Permite al usuario buscar un usuario
 * por su cédula, visualizar y editar sus detalles como contraseña, nombre, fecha de nacimiento,
 * teléfono y correo electrónico. Soporta la internacionalización del texto y la carga de imágenes
 * para los botones.
 */
public class ModificarUsuarioView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JLabel lblModificar;
    private JLabel lblCedulaBuscar;
    private JTextField txtCedulaBuscar;
    private JButton btnBuscar;
    private JLabel lblContrasena;
    private JPasswordField txtContrasena;
    private JLabel lblNombre;
    private JTextField txtNombre;
    private JLabel lblFecha;
    private JSpinner spnDia;
    private JSpinner spnMes;
    private JSpinner spnAno;
    private JLabel lblTelefono;
    private JTextField txtTelefono;
    private JLabel lblCorreo;
    private JTextField txtCorreo;
    private JButton btnEditar;
    private MensajeInternacionalizacionHandler mi;

    /**
     * Constructor para la vista ModificarUsuarioView.
     * Inicializa la ventana interna, configura sus propiedades,
     * establece el manejador de internacionalización.
     * Configura los modelos de los spinners para la fecha de nacimiento.
     * Carga las imágenes de los botones y los textos traducidos.
     * Los campos de detalles del usuario se deshabilitan inicialmente.
     *
     * @param mi El manejador de internacionalización de mensajes para la vista.
     */
    public ModificarUsuarioView(MensajeInternacionalizacionHandler mi) {
        super("Editar Usuarios", true, true, false, true);
        this.mi = mi;
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);

        configurarSpinnersDeFecha();
        inicializarImagenes();

        habilitarCampos(false);
        cambiarIdioma();
    }

    /**
     * Configura los modelos para los JSpinner de día, mes y año,
     * estableciendo rangos válidos para las fechas, siendo el año máximo el año actual.
     */
    private void configurarSpinnersDeFecha() {
        int anioActual = Calendar.getInstance().get(Calendar.YEAR);
        spnDia.setModel(new SpinnerNumberModel(1, 1, 31, 1));
        spnMes.setModel(new SpinnerNumberModel(1, 1, 12, 1));
        spnAno.setModel(new SpinnerNumberModel(2000, 1920, anioActual, 1));
    }

    /**
     * Cambia el idioma de todos los componentes de la vista utilizando el `MensajeInternacionalizacionHandler`.
     * Actualiza el título de la ventana, los textos de etiquetas y botones.
     * También actualiza los textos predeterminados de los botones de `JOptionPane`.
     */
    public void cambiarIdioma() {
        setTitle(mi.get("usuario.modificar.titulo.ventana"));
        lblModificar.setText(mi.get("usuario.modificar.titulo"));
        lblCedulaBuscar.setText(mi.get("usuario.modificar.cedula"));
        lblContrasena.setText(mi.get("usuario.modificar.contrasena"));
        lblNombre.setText(mi.get("usuario.modificar.nombre"));
        lblFecha.setText(mi.get("usuario.modificar.fecha"));
        lblTelefono.setText(mi.get("usuario.modificar.telefono"));
        lblCorreo.setText(mi.get("usuario.modificar.correo"));
        btnBuscar.setText(mi.get("usuario.modificar.buscar.btn"));
        btnEditar.setText(mi.get("usuario.modificar.editar.btn"));

        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }

    /**
     * Inicializa las imágenes para los botones **Buscar** y **Editar**
     * cargándolos desde la carpeta "imagenes" en los recursos del proyecto.
     * Si no se encuentran los recursos, se imprime un mensaje de error en la consola.
     */
    public void inicializarImagenes() {
        URL buscarUrl = getClass().getClassLoader().getResource("imagenes/buscar.png");
        if (buscarUrl != null) {
            btnBuscar.setIcon(new ImageIcon(buscarUrl));
        } else {
            System.err.println("Error: No se pudo cargar el icono 'buscar.png'");
        }

        URL editarUrl = getClass().getClassLoader().getResource("imagenes/editar.png");
        if (editarUrl != null) {
            btnEditar.setIcon(new ImageIcon(editarUrl));
        } else {
            System.err.println("Error: No se pudo cargar el icono 'editar.png'");
        }
    }

    /**
     * Habilita o deshabilita los campos de entrada y spinners para los detalles del usuario.
     *
     * @param habilitar true para habilitar los campos, false para deshabilitarlos.
     */
    public void habilitarCampos(boolean habilitar) {
        txtCedulaBuscar.setEnabled(habilitar);
        txtContrasena.setEnabled(habilitar);
        txtNombre.setEnabled(habilitar);
        txtTelefono.setEnabled(habilitar);
        txtCorreo.setEnabled(habilitar);
        spnDia.setEnabled(habilitar);
        spnMes.setEnabled(habilitar);
        spnAno.setEnabled(habilitar);
    }

    /**
     * Muestra un mensaje informativo en un cuadro de diálogo.
     * El título del diálogo se obtiene del manejador de internacionalización.
     *
     * @param mensaje El mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mi.get("dialogo.titulo.informacion"), JOptionPane.INFORMATION_MESSAGE);
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
     * Limpia todos los campos de texto y restablece los valores de los spinners de fecha
     * a sus valores predeterminados.
     */
    public void limpiarCampos() {
        txtCedulaBuscar.setText("");
        txtContrasena.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        spnDia.setValue(1);
        spnMes.setValue(1);
        spnAno.setValue(2000);
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
     * Obtiene la etiqueta principal de "Modificar Usuario".
     *
     * @return El JLabel `lblModificar`.
     */
    public JLabel getLblModificar() {
        return lblModificar;
    }

    /**
     * Establece la etiqueta principal de "Modificar Usuario".
     *
     * @param lblModificar El JLabel a establecer.
     */
    public void setLblModificar(JLabel lblModificar) {
        this.lblModificar = lblModificar;
    }

    /**
     * Obtiene la etiqueta para buscar por cédula.
     *
     * @return El JLabel `lblCedulaBuscar`.
     */
    public JLabel getLblCedulaBuscar() {
        return lblCedulaBuscar;
    }

    /**
     * Establece la etiqueta para buscar por cédula.
     *
     * @param lblCedulaBuscar El JLabel a establecer.
     */
    public void setLblCedulaBuscar(JLabel lblCedulaBuscar) {
        this.lblCedulaBuscar = lblCedulaBuscar;
    }

    /**
     * Obtiene el campo de texto para la cédula de búsqueda.
     *
     * @return El JTextField `txtCedulaBuscar`.
     */
    public JTextField getTxtCedulaBuscar() {
        return txtCedulaBuscar;
    }

    /**
     * Establece el campo de texto para la cédula de búsqueda.
     *
     * @param txtCedulaBuscar El JTextField a establecer.
     */
    public void setTxtCedulaBuscar(JTextField txtCedulaBuscar) {
        this.txtCedulaBuscar = txtCedulaBuscar;
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
     * Obtiene la etiqueta para la contraseña del usuario.
     *
     * @return El JLabel `lblContrasena`.
     */
    public JLabel getLblContrasena() {
        return lblContrasena;
    }

    /**
     * Establece la etiqueta para la contraseña del usuario.
     *
     * @param lblContrasena El JLabel a establecer.
     */
    public void setLblContrasena(JLabel lblContrasena) {
        this.lblContrasena = lblContrasena;
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
     * Obtiene la etiqueta para el nombre del usuario.
     *
     * @return El JLabel `lblNombre`.
     */
    public JLabel getLblNombre() {
        return lblNombre;
    }

    /**
     * Establece la etiqueta para el nombre del usuario.
     *
     * @param lblNombre El JLabel a establecer.
     */
    public void setLblNombre(JLabel lblNombre) {
        this.lblNombre = lblNombre;
    }

    /**
     * Obtiene el campo de texto para el nombre del usuario.
     *
     * @return El JTextField `txtNombre`.
     */
    public JTextField getTxtNombre() {
        return txtNombre;
    }

    /**
     * Establece el campo de texto para el nombre del usuario.
     *
     * @param txtNombre El JTextField a establecer.
     */
    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    /**
     * Obtiene la etiqueta para la fecha de nacimiento.
     *
     * @return El JLabel `lblFecha`.
     */
    public JLabel getLblFecha() {
        return lblFecha;
    }

    /**
     * Establece la etiqueta para la fecha de nacimiento.
     *
     * @param lblFecha El JLabel a establecer.
     */
    public void setLblFecha(JLabel lblFecha) {
        this.lblFecha = lblFecha;
    }

    /**
     * Obtiene el spinner para el día de la fecha de nacimiento.
     *
     * @return El JSpinner `spnDia`.
     */
    public JSpinner getSpnDia() {
        return spnDia;
    }

    /**
     * Establece el spinner para el día de la fecha de nacimiento.
     *
     * @param spnDia El JSpinner a establecer.
     */
    public void setSpnDia(JSpinner spnDia) {
        this.spnDia = spnDia;
    }

    /**
     * Obtiene el spinner para el mes de la fecha de nacimiento.
     *
     * @return El JSpinner `spnMes`.
     */
    public JSpinner getSpnMes() {
        return spnMes;
    }

    /**
     * Establece el spinner para el mes de la fecha de nacimiento.
     *
     * @param spnMes El JSpinner a establecer.
     */
    public void setSpnMes(JSpinner spnMes) {
        this.spnMes = spnMes;
    }

    /**
     * Obtiene el spinner para el año de la fecha de nacimiento.
     *
     * @return El JSpinner `spnAno`.
     */
    public JSpinner getSpnAno() {
        return spnAno;
    }

    /**
     * Establece el spinner para el año de la fecha de nacimiento.
     *
     * @param spnAno El JSpinner a establecer.
     */
    public void setSpnAno(JSpinner spnAno) {
        this.spnAno = spnAno;
    }

    /**
     * Obtiene la etiqueta para el número de teléfono.
     *
     * @return El JLabel `lblTelefono`.
     */
    public JLabel getLblTelefono() {
        return lblTelefono;
    }

    /**
     * Establece la etiqueta para el número de teléfono.
     *
     * @param lblTelefono El JLabel a establecer.
     */
    public void setLblTelefono(JLabel lblTelefono) {
        this.lblTelefono = lblTelefono;
    }

    /**
     * Obtiene el campo de texto para el número de teléfono del usuario.
     *
     * @return El JTextField `txtTelefono`.
     */
    public JTextField getTxtTelefono() {
        return txtTelefono;
    }

    /**
     * Establece el campo de texto para el número de teléfono del usuario.
     *
     * @param txtTelefono El JTextField a establecer.
     */
    public void setTxtTelefono(JTextField txtTelefono) {
        this.txtTelefono = txtTelefono;
    }

    /**
     * Obtiene la etiqueta para el correo electrónico.
     *
     * @return El JLabel `lblCorreo`.
     */
    public JLabel getLblCorreo() {
        return lblCorreo;
    }

    /**
     * Establece la etiqueta para el correo electrónico.
     *
     * @param lblCorreo El JLabel a establecer.
     */
    public void setLblCorreo(JLabel lblCorreo) {
        this.lblCorreo = lblCorreo;
    }

    /**
     * Obtiene el campo de texto para el correo electrónico del usuario.
     *
     * @return El JTextField `txtCorreo`.
     */
    public JTextField getTxtCorreo() {
        return txtCorreo;
    }

    /**
     * Establece el campo de texto para el correo electrónico del usuario.
     *
     * @param txtCorreo El JTextField a establecer.
     */
    public void setTxtCorreo(JTextField txtCorreo) {
        this.txtCorreo = txtCorreo;
    }

    /**
     * Obtiene el botón "Editar".
     *
     * @return El JButton `btnEditar`.
     */
    public JButton getBtnEditar() {
        return btnEditar;
    }

    /**
     * Establece el botón "Editar".
     *
     * @param btnEditar El JButton a establecer.
     */
    public void setBtnEditar(JButton btnEditar) {
        this.btnEditar = btnEditar;
    }
}