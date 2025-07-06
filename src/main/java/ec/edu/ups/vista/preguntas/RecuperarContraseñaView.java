package ec.edu.ups.vista.preguntas;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URL;

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
    private MensajeInternacionalizacionHandler mi;


    public RecuperarContraseñaView (MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        setTitle(mi.get("cuestionario.recuperar.titulo"));
        setSize(600, 400);
        setContentPane(panelPrincipal);
        setLocationRelativeTo(null);
        actualizarTextos();
        inicializarImagenes();
    }
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void actualizarTextos() {
        lblRecuperar.setText(mi.get("cuestionario.recuperar.titulo"));
        btnGuardar.setText(mi.get("cuestionario.boton.guardar"));
        btnTerminar.setText(mi.get("cuestionario.boton.finalizar"));

        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }

    public JLabel getLblRecuperar() {
        return lblRecuperar;
    }

    public void setLblRecuperar(JLabel lblRecuperar) {
        this.lblRecuperar = lblRecuperar;
    }

    public JComboBox<String> getCbxPreguntas() {
        return cbxPreguntas;
    }

    public void setCbxPreguntas(JComboBox<String> cbxPreguntas) {
        this.cbxPreguntas = cbxPreguntas;
    }

    public JLabel getLblTexto() {
        return lblTexto;
    }

    public void setLblTexto(JLabel lblTexto) {
        this.lblTexto = lblTexto;
    }

    public JTextField getTxtRespuesta() {
        return txtRespuesta;
    }

    public void setTxtRespuesta(JTextField txtRespuesta) {
        this.txtRespuesta = txtRespuesta;
    }

    public JLabel getLblPregunta() {
        return lblPregunta;
    }

    public void setLblPregunta(JLabel lblPregunta) {
        this.lblPregunta = lblPregunta;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public void setBtnGuardar(JButton btnGuardar) {
        this.btnGuardar = btnGuardar;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    public JButton getBtnTerminar() {
        return btnTerminar;
    }

    public void setBtnTerminar(JButton btnTerminar) {
        this.btnTerminar = btnTerminar;
    }

    public void inicializarImagenes(){
        URL guardar = RecuperarContraseñaView .class.getClassLoader().getResource("imagenes/guardar.png");
        if (guardar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(guardar);
            btnGuardar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }

        URL finalizar = RecuperarContraseñaView .class.getClassLoader().getResource("imagenes/finalizar.png");
        if (finalizar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(finalizar);
            btnTerminar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }
    }





}
