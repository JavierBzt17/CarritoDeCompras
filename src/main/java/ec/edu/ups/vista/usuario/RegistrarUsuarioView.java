package ec.edu.ups.vista.usuario;

import ec.edu.ups.excepciones.CedulaInvalidaException;
import ec.edu.ups.excepciones.CorreoInvalidoException;
import ec.edu.ups.excepciones.ContrasenaInvalidaException;
import ec.edu.ups.excepciones.TelefonoInvalidoException;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.util.ValidadorDatosUsuario; // Importar la nueva clase validadora

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Calendar;

public class RegistrarUsuarioView extends JFrame {
    // ... (todos tus componentes de la interfaz gráfica se mantienen igual)
    private JPanel panelPrincipal;
    private JLabel lblVentPregunta;
    private JTextField txtNombre;
    private JTextField txtTelefono;
    private JTextField txtCorreo;
    private JPasswordField txtContrasena;
    private JSpinner spnDia;
    private JSpinner spnMes;
    private JSpinner spnAno;
    private JButton btnGenerar;
    private JTextField txtRespuesta;
    private JButton btnGuardar;
    private JButton btnTerminar;
    private JLabel lblNombre;
    private JLabel lblNacimiento;
    private JLabel lblTelefono;
    private JLabel lblCorreo;
    private JLabel lblContrasena;
    private JLabel lblPreguntasR;
    private JTextField txtPreguntas;
    private JTextField txtCedula;
    private JLabel lblCedula;


    private MensajeInternacionalizacionHandler mi;

    public RegistrarUsuarioView(MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        setTitle(mi.get("cuestionario.titulo"));
        setSize(600, 400);
        setContentPane(panelPrincipal);
        setLocationRelativeTo(null);
        actualizarTextos();
        inicializarImagenes();

        habilitarPreguntas(true);

        int anioActual = Calendar.getInstance().get(Calendar.YEAR);
        spnDia.setModel(new SpinnerNumberModel(1, 1, 31, 1));
        spnMes.setModel(new SpinnerNumberModel(1, 1, 12, 1));
        spnAno.setModel(new SpinnerNumberModel(2000, 1920, anioActual, 1));

        // ActionListener actualizado para manejar todas las validaciones
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // 1. Obtener los datos de los campos de texto
                    String cedula = txtCedula.getText();
                    String telefono = txtTelefono.getText();
                    String correo = txtCorreo.getText();
                    String contrasena = new String(txtContrasena.getPassword());

                    // 2. Realizar todas las validaciones usando la clase ValidadorDatosUsuario
                    ValidadorDatosUsuario.validarCedula(cedula);
                    ValidadorDatosUsuario.validarTelefono(telefono);
                    ValidadorDatosUsuario.validarCorreo(correo);
                    ValidadorDatosUsuario.validarContrasena(contrasena);

                    // 3. Si todo es correcto, mostrar un mensaje de éxito
                    // Aquí iría la lógica para guardar el usuario en el DAO
                    mostrarMensaje("¡Usuario validado y guardado correctamente!");

                } catch (CedulaInvalidaException | TelefonoInvalidoException | CorreoInvalidoException | ContrasenaInvalidaException ex) {
                    // 4. Si alguna validación falla, capturar la excepción y mostrar el mensaje de error
                    mostrarMensaje(ex.getMessage());
                }
            }
        });
    }

    public void mostrarMensaje(String mensaje) {
        // Usar el título del manejador de internacionalización para el diálogo
        JOptionPane.showMessageDialog(this, mensaje, mi.get("cuestionario.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }

    // El resto de la clase (getters, setters, actualizarTextos, inicializarImagenes, etc.) permanece igual.
    // **IMPORTANTE**: Asegúrate de eliminar la clase interna "public class algoritmo" de este archivo.
    //...

    //<editor-fold defaultstate="collapsed" desc="GETTERS Y SETTERS">
    public void actualizarTextos() {
        lblVentPregunta.setText(mi.get("cuestionario.titulo"));
        btnGuardar.setText(mi.get("cuestionario.boton.guardar"));
        btnTerminar.setText(mi.get("cuestionario.boton.finalizar"));
        lblPreguntasR.setText(mi.get("cuestionario.pregunta"));

        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }

    /**
     * Inicializa las imágenes para los botones **Guardar** y **Terminar**
     * cargándolos desde la carpeta "imagenes" en los recursos del proyecto.
     * Si no se encuentran los recursos, se imprime un mensaje de error en la consola.
     */
    public void inicializarImagenes() {
        URL guardarUrl = getClass().getClassLoader().getResource("imagenes/guardar.png");
        if (guardarUrl != null) {
            btnGuardar.setIcon(new ImageIcon(guardarUrl));
        } else {
            System.err.println("Error: No se pudo cargar el ícono 'guardar.png'");
        }

        URL finalizarUrl = getClass().getClassLoader().getResource("imagenes/finalizar.png");
        if (finalizarUrl != null) {
            btnTerminar.setIcon(new ImageIcon(finalizarUrl));
        } else {
            System.err.println("Error: No se pudo cargar el ícono 'finalizar.png'");
        }
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
     * Obtiene la etiqueta para la ventana de preguntas.
     *
     * @return El JLabel `lblVentPregunta`.
     */
    public JLabel getLblVentPregunta() {
        return lblVentPregunta;
    }

    /**
     * Establece la etiqueta para la ventana de preguntas.
     *
     * @param lblVentPregunta El JLabel a establecer.
     */
    public void setLblVentPregunta(JLabel lblVentPregunta) {
        this.lblVentPregunta = lblVentPregunta;
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
     * Obtiene el botón para generar preguntas de seguridad.
     *
     * @return El JButton `btnGenerar`.
     */
    public JButton getBtnGenerar() {
        return btnGenerar;
    }

    /**
     * Establece el botón para generar preguntas de seguridad.
     *
     * @param btnGenerar El JButton a establecer.
     */
    public void setBtnGenerar(JButton btnGenerar) {
        this.btnGenerar = btnGenerar;
    }

    /**
     * Obtiene el campo de texto para la respuesta de la pregunta de seguridad.
     *
     * @return El JTextField `txtRespuesta`.
     */
    public JTextField getTxtRespuesta() {
        return txtRespuesta;
    }

    /**
     * Establece el campo de texto para la respuesta de la pregunta de seguridad.
     *
     * @param txtRespuesta El JTextField a establecer.
     */
    public void setTxtRespuesta(JTextField txtRespuesta) {
        this.txtRespuesta = txtRespuesta;
    }

    /**
     * Obtiene el botón para guardar la respuesta de seguridad.
     *
     * @return El JButton `btnGuardar`.
     */
    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    /**
     * Establece el botón para guardar la respuesta de seguridad.
     *
     * @param btnGuardar El JButton a establecer.
     */
    public void setBtnGuardar(JButton btnGuardar) {
        this.btnGuardar = btnGuardar;
    }

    /**
     * Obtiene el botón para finalizar el registro.
     *
     * @return El JButton `btnTerminar`.
     */
    public JButton getBtnTerminar() {
        return btnTerminar;
    }

    /**
     * Establece el botón para finalizar el registro.
     *
     * @param btnTerminar El JButton a establecer.
     */
    public void setBtnTerminar(JButton btnTerminar) {
        this.btnTerminar = btnTerminar;
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
     * Obtiene la etiqueta para la fecha de nacimiento.
     *
     * @return El JLabel `lblNacimiento`.
     */
    public JLabel getLblNacimiento() {
        return lblNacimiento;
    }

    /**
     * Establece la etiqueta para la fecha de nacimiento.
     *
     * @param lblNacimiento El JLabel a establecer.
     */
    public void setLblNacimiento(JLabel lblNacimiento) {
        this.lblNacimiento = lblNacimiento;
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
     * Obtiene la etiqueta para la contraseña.
     *
     * @return El JLabel `lblContrasena`.
     */
    public JLabel getLblContrasena() {
        return lblContrasena;
    }

    /**
     * Establece la etiqueta para la contraseña.
     *
     * @param lblContrasena El JLabel a establecer.
     */
    public void setLblContrasena(JLabel lblContrasena) {
        this.lblContrasena = lblContrasena;
    }

    /**
     * Obtiene la etiqueta para las preguntas de seguridad.
     *
     * @return El JLabel `lblPreguntasR`.
     */
    public JLabel getLblPreguntasR() {
        return lblPreguntasR;
    }

    /**
     * Establece la etiqueta para las preguntas de seguridad.
     *
     * @param lblPreguntasR El JLabel a establecer.
     */
    public void setLblPreguntasR(JLabel lblPreguntasR) {
        this.lblPreguntasR = lblPreguntasR;
    }

    /**
     * Obtiene el campo de texto para las preguntas de seguridad.
     *
     * @return El JTextField `txtPreguntas`.
     */
    public JTextField getTxtPreguntas() {
        return txtPreguntas;
    }

    /**
     * Establece el campo de texto para las preguntas de seguridad.
     *
     * @param txtPreguntas El JTextField a establecer.
     */
    public void setTxtPreguntas(JTextField txtPreguntas) {
        this.txtPreguntas = txtPreguntas;
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
     * Obtiene la etiqueta para la cédula.
     *
     * @return El JLabel `lblCedula`.
     */
    public JLabel getLblCedula() {
        return lblCedula;
    }

    /**
     * Establece la etiqueta para la cédula.
     *
     * @param lblCedula El JLabel a establecer.
     */
    public void setLblCedula(JLabel lblCedula) {
        this.lblCedula = lblCedula;
    }

    /**
     * Habilita o deshabilita los campos relacionados con las preguntas de seguridad y los botones de guardar/terminar.
     *
     * @param habilitar true para habilitar los campos y botones, false para deshabilitarlos.
     */
    public void habilitarPreguntas(boolean habilitar) {
        txtPreguntas.setEnabled(habilitar);
        txtRespuesta.setEnabled(habilitar);
        btnGuardar.setEnabled(habilitar);
        btnTerminar.setEnabled(habilitar);
    }
    //</editor-fold>
}