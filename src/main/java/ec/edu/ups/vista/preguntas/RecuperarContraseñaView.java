package ec.edu.ups.vista.preguntas;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URL;

/**
 * La clase **RecuperarContraseñaView** representa la interfaz gráfica de usuario
 * para el proceso de recuperación de contraseña, utilizando preguntas de seguridad.
 * Permite al usuario seleccionar una pregunta, ingresar una respuesta y guardar los cambios.
 * Soporta la internacionalización del texto y la carga de imágenes para los botones.
 */
public class RecuperarContraseñaView extends JFrame {
    private JLabel lblRecuperar;
    private JComboBox<String> cbxPreguntas;
    private JLabel lblTexto;
    private JTextField txtRespuesta;
    private JLabel lblPregunta;
    private JButton btnGuardar;
    private JPanel panelPrincipal;
    private JLabel lblTitulo;
    private JButton btnTerminar;
    private JButton btnGenerar;
    private JTextField txtPreguntas;
    private MensajeInternacionalizacionHandler mi;

    /**
     * Constructor para la vista RecuperarContraseñaView.
     * Inicializa la ventana, configura el manejador de internacionalización,
     * actualiza los textos de la interfaz y carga las imágenes de los botones.
     *
     * @param mi El manejador de internacionalización de mensajes para la vista.
     */
    public RecuperarContraseñaView(MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        setTitle(mi.get("cuestionario.recuperar.titulo"));
        setSize(600, 400);
        setContentPane(panelPrincipal);
        setLocationRelativeTo(null);
        actualizarTextos();
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
     * Actualiza los textos de las etiquetas y botones en la interfaz de usuario
     * utilizando el `MensajeInternacionalizacionHandler` para soportar diferentes idiomas.
     * También actualiza los textos predeterminados de los botones de `JOptionPane`.
     */
    public void actualizarTextos() {
        lblRecuperar.setText(mi.get("cuestionario.recuperar.titulo"));
        btnGuardar.setText(mi.get("cuestionario.boton.guardar"));
        btnTerminar.setText(mi.get("cuestionario.boton.finalizar"));

        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }

    /**
     * Obtiene la etiqueta principal de recuperación de contraseña.
     *
     * @return El JLabel `lblRecuperar`.
     */
    public JLabel getLblRecuperar() {
        return lblRecuperar;
    }

    /**
     * Establece la etiqueta principal de recuperación de contraseña.
     *
     * @param lblRecuperar El JLabel a establecer.
     */
    public void setLblRecuperar(JLabel lblRecuperar) {
        this.lblRecuperar = lblRecuperar;
    }

    /**
     * Obtiene el JComboBox que contiene las preguntas de seguridad.
     *
     * @return El JComboBox `cbxPreguntas`.
     */
    public JComboBox<String> getCbxPreguntas() {
        return cbxPreguntas;
    }

    /**
     * Establece el JComboBox que contiene las preguntas de seguridad.
     *
     * @param cbxPreguntas El JComboBox a establecer.
     */
    public void setCbxPreguntas(JComboBox<String> cbxPreguntas) {
        this.cbxPreguntas = cbxPreguntas;
    }

    /**
     * Obtiene la etiqueta de texto descriptivo.
     *
     * @return El JLabel `lblTexto`.
     */
    public JLabel getLblTexto() {
        return lblTexto;
    }

    /**
     * Establece la etiqueta de texto descriptivo.
     *
     * @param lblTexto El JLabel a establecer.
     */
    public void setLblTexto(JLabel lblTexto) {
        this.lblTexto = lblTexto;
    }

    /**
     * Obtiene el campo de texto para la respuesta a la pregunta de seguridad.
     *
     * @return El JTextField `txtRespuesta`.
     */
    public JTextField getTxtRespuesta() {
        return txtRespuesta;
    }

    /**
     * Establece el campo de texto para la respuesta a la pregunta de seguridad.
     *
     * @param txtRespuesta El JTextField a establecer.
     */
    public void setTxtRespuesta(JTextField txtRespuesta) {
        this.txtRespuesta = txtRespuesta;
    }

    /**
     * Obtiene la etiqueta de la pregunta de seguridad.
     *
     * @return El JLabel `lblPregunta`.
     */
    public JLabel getLblPregunta() {
        return lblPregunta;
    }

    /**
     * Establece la etiqueta de la pregunta de seguridad.
     *
     * @param lblPregunta El JLabel a establecer.
     */
    public void setLblPregunta(JLabel lblPregunta) {
        this.lblPregunta = lblPregunta;
    }

    /**
     * Obtiene el botón para guardar la respuesta.
     *
     * @return El JButton `btnGuardar`.
     */
    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    /**
     * Establece el botón para guardar la respuesta.
     *
     * @param btnGuardar El JButton a establecer.
     */
    public void setBtnGuardar(JButton btnGuardar) {
        this.btnGuardar = btnGuardar;
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
     * Obtiene la etiqueta del título.
     *
     * @return El JLabel `lblTitulo`.
     */
    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    /**
     * Establece la etiqueta del título.
     *
     * @param lblTitulo El JLabel a establecer.
     */
    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    /**
     * Obtiene el botón para finalizar el proceso.
     *
     * @return El JButton `btnTerminar`.
     */
    public JButton getBtnTerminar() {
        return btnTerminar;
    }

    /**
     * Establece el botón para finalizar el proceso.
     *
     * @param btnTerminar El JButton a establecer.
     */
    public void setBtnTerminar(JButton btnTerminar) {
        this.btnTerminar = btnTerminar;
    }

    /**
     * Obtiene el botón para generar preguntas.
     *
     * @return El JButton `btnGenerar`.
     */
    public JButton getBtnGenerar() {
        return btnGenerar;
    }

    /**
     * Establece el botón para generar preguntas.
     *
     * @param btnGenerar El JButton a establecer.
     */
    public void setBtnGenerar(JButton btnGenerar) {
        this.btnGenerar = btnGenerar;
    }

    /**
     * Obtiene el campo de texto para las preguntas.
     *
     * @return El JTextField `txtPreguntas`.
     */
    public JTextField getTxtPreguntas() {
        return txtPreguntas;
    }

    /**
     * Establece el campo de texto para las preguntas.
     *
     * @param txtPreguntas El JTextField a establecer.
     */
    public void setTxtPreguntas(JTextField txtPreguntas) {
        this.txtPreguntas = txtPreguntas;
    }

    /**
     * Inicializa las imágenes para los botones **Guardar** y **Terminar**
     * cargándolos desde la carpeta "imagenes" en los recursos del proyecto.
     * Si no se encuentran los recursos, se imprime un mensaje de error en la consola.
     */
    public void inicializarImagenes() {
        URL guardar = RecuperarContraseñaView.class.getClassLoader().getResource("imagenes/guardar.png");
        if (guardar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(guardar);
            btnGuardar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error, No se cargo el icono Login");
        }

        URL finalizar = RecuperarContraseñaView.class.getClassLoader().getResource("imagenes/finalizar.png");
        if (finalizar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(finalizar);
            btnTerminar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error, No se cargo el icono Login");
        }
    }
}