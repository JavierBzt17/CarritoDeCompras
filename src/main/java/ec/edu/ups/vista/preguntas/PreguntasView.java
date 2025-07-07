package ec.edu.ups.vista.preguntas;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URL;
public class PreguntasView extends JFrame{
    private JPanel panelPrincipal;
    private JLabel lblVentPregunta;
    private JTextField txtNombre;
    private JTextField txtTelefono;
    private JTextField txtCorreo;
    private JTextField txtUsuario;
    private JTextField txtContrasena;
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
    private JLabel lblUsuario;
    private JLabel lblContrasena;
    private JLabel lblPreguntasR;
    private JTextField txtPreguntas;

    private MensajeInternacionalizacionHandler mi;

    public PreguntasView(MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        setTitle(mi.get("cuestionario.titulo"));
        setSize(600, 400);
        setContentPane(panelPrincipal);
        setLocationRelativeTo(null);
        actualizarTextos();
        inicializarImagenes();
        habilitarPreguntas(false);
        spnDia.setModel(new SpinnerNumberModel(1, 1, 31, 1));
        spnMes.setModel(new SpinnerNumberModel(1, 1, 12, 1));
        spnAno.setModel(new SpinnerNumberModel(2000, 1, 2100, 1));
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

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

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JLabel getLblVentPregunta() {
        return lblVentPregunta;
    }

    public void setLblVentPregunta(JLabel lblVentPregunta) {
        this.lblVentPregunta = lblVentPregunta;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    public JTextField getTxtTelefono() {
        return txtTelefono;
    }

    public void setTxtTelefono(JTextField txtTelefono) {
        this.txtTelefono = txtTelefono;
    }

    public JTextField getTxtCorreo() {
        return txtCorreo;
    }

    public void setTxtCorreo(JTextField txtCorreo) {
        this.txtCorreo = txtCorreo;
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public void setTxtUsuario(JTextField txtUsuario) {
        this.txtUsuario = txtUsuario;
    }

    public JTextField getTxtContrasena() {
        return txtContrasena;
    }

    public void setTxtContrasena(JTextField txtContrasena) {
        this.txtContrasena = txtContrasena;
    }

    public JSpinner getSpnDia() {
        return spnDia;
    }

    public void setSpnDia(JSpinner spnDia) {
        this.spnDia = spnDia;
    }

    public JSpinner getSpnMes() {
        return spnMes;
    }

    public void setSpnMes(JSpinner spnMes) {
        this.spnMes = spnMes;
    }

    public JSpinner getSpnAno() {
        return spnAno;
    }

    public void setSpnAno(JSpinner spnAno) {
        this.spnAno = spnAno;
    }

    public JButton getBtnGenerar() {
        return btnGenerar;
    }

    public void setBtnGenerar(JButton btnGenerar) {
        this.btnGenerar = btnGenerar;
    }

    public JTextField getTxtRespuesta() {
        return txtRespuesta;
    }

    public void setTxtRespuesta(JTextField txtRespuesta) {
        this.txtRespuesta = txtRespuesta;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public void setBtnGuardar(JButton btnGuardar) {
        this.btnGuardar = btnGuardar;
    }

    public JButton getBtnTerminar() {
        return btnTerminar;
    }

    public void setBtnTerminar(JButton btnTerminar) {
        this.btnTerminar = btnTerminar;
    }

    public JLabel getLblNombre() {
        return lblNombre;
    }

    public void setLblNombre(JLabel lblNombre) {
        this.lblNombre = lblNombre;
    }

    public JLabel getLblNacimiento() {
        return lblNacimiento;
    }

    public void setLblNacimiento(JLabel lblNacimiento) {
        this.lblNacimiento = lblNacimiento;
    }

    public JLabel getLblTelefono() {
        return lblTelefono;
    }

    public void setLblTelefono(JLabel lblTelefono) {
        this.lblTelefono = lblTelefono;
    }

    public JLabel getLblCorreo() {
        return lblCorreo;
    }

    public void setLblCorreo(JLabel lblCorreo) {
        this.lblCorreo = lblCorreo;
    }

    public JLabel getLblUsuario() {
        return lblUsuario;
    }

    public void setLblUsuario(JLabel lblUsuario) {
        this.lblUsuario = lblUsuario;
    }

    public JLabel getLblContrasena() {
        return lblContrasena;
    }

    public void setLblContrasena(JLabel lblContrasena) {
        this.lblContrasena = lblContrasena;
    }

    public JLabel getLblPreguntasR() {
        return lblPreguntasR;
    }

    public void setLblPreguntasR(JLabel lblPreguntasR) {
        this.lblPreguntasR = lblPreguntasR;
    }

    public JTextField getTxtPreguntas() {
        return txtPreguntas;
    }

    public void setTxtPreguntas(JTextField txtPreguntas) {
        this.txtPreguntas = txtPreguntas;
    }

    public void inicializarImagenes(){
        URL guardar = PreguntasView.class.getClassLoader().getResource("imagenes/guardar.png");
        if (guardar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(guardar);
            btnGuardar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error, No se cargo el icono Login");
        }

        URL finalizar = PreguntasView.class.getClassLoader().getResource("imagenes/finalizar.png");
        if (finalizar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(finalizar);
            btnTerminar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error, No se cargo el icono Login");
        }
    }

    public void habilitarPreguntas(boolean habilitar) {
        txtPreguntas.setEnabled(habilitar);
        txtRespuesta.setEnabled(habilitar);
        btnGuardar.setEnabled(habilitar);
        btnTerminar.setEnabled(habilitar);
    }
}