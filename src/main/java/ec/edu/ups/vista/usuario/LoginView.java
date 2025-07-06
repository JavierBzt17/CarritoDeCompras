package ec.edu.ups.vista.usuario;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.util.Locale;
import java.net.URL;

public class LoginView extends JFrame{
    private JPanel panelPrincipal;
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnIniciarSesion;
    private JButton btnRegistrarse;
    private JButton btnOlvide;
    private JButton btnSalir;
    private JLabel lblInicio;
    private JLabel lblUsuario;
    private JLabel lblContrasena;
    private JComboBox cbxIdioma;
    private JLabel lblIdioma;
    private MensajeInternacionalizacionHandler mi;

    public LoginView(MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        setTitle("Iniciar Sesion");
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(panelPrincipal);
        inicializarImagenes();
        inicializarComponentes();
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public void setTxtUsuario(JTextField txtUsuario) {
        this.txtUsuario = txtUsuario;
    }

    public JPasswordField getTxtContrasena() {
        return txtContrasena;
    }

    public void setTxtContrasena(JPasswordField txtContrasena) {
        this.txtContrasena = txtContrasena;
    }

    public JButton getBtnIniciarSesion() {
        return btnIniciarSesion;
    }

    public void setBtnIniciarSesion(JButton btnIniciarSesion) {
        this.btnIniciarSesion = btnIniciarSesion;
    }

    public JButton getBtnRegistrarse() {
        return btnRegistrarse;
    }

    public void setBtnRegistrarse(JButton btnRegistrarse) {
        this.btnRegistrarse = btnRegistrarse;
    }

    public JButton getBtnOlvide() {
        return btnOlvide;
    }

    public void setBtnOlvide(JButton btnOlvide) {
        this.btnOlvide = btnOlvide;
    }

    public JButton getBtnSalir() {
        return btnSalir;
    }

    public void setBtnSalir(JButton btnSalir) {
        this.btnSalir = btnSalir;
    }

    public JLabel getLblInicio() {
        return lblInicio;
    }

    public void setLblInicio(JLabel lblInicio) {
        this.lblInicio = lblInicio;
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

    public JComboBox getCbxIdioma() {
        return cbxIdioma;
    }

    public void setCbxIdioma(JComboBox cbxIdioma) {
        this.cbxIdioma = cbxIdioma;
    }

    public JLabel getLblIdioma() {
        return lblIdioma;
    }

    public void setLblIdioma(JLabel lblIdioma) {
        this.lblIdioma = lblIdioma;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public boolean mostrarMensajePregunta(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, "Confirmación",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }

    public void inicializarComponentes() {
        cbxIdioma.removeAllItems();
        cbxIdioma.addItem(mi.get("menu.idioma.es"));
        cbxIdioma.addItem(mi.get("menu.idioma.en"));
        cbxIdioma.addItem(mi.get("menu.idioma.fr"));
        actualizarTextos();
    }

    public void inicializarImagenes(){
        URL loginURL = LoginView.class.getClassLoader().getResource("imagenes/login.png");
        if (loginURL != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(loginURL);
            btnIniciarSesion.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error, No se cargo el icono Login");
        }

        URL registrarseURL = LoginView.class.getClassLoader().getResource("imagenes/registrarse.png");
        if (registrarseURL != null) {
            ImageIcon iconoBtnRegistrarse = new ImageIcon(registrarseURL);
            btnRegistrarse.setIcon(iconoBtnRegistrarse);
        } else {
            System.err.println("Error, No se cargo el icono Registrarse");
        }

        URL olvido = LoginView.class.getClassLoader().getResource("imagenes/confusion.png");
        if (olvido != null) {
            ImageIcon iconoBtnRegistrarse = new ImageIcon(olvido);
            btnOlvide.setIcon(iconoBtnRegistrarse);
        } else {
            System.err.println("Error, No se cargo el icono Registrarse");
        }

        URL salir = LoginView.class.getClassLoader().getResource("imagenes/salir.png");
        if (salir != null) {
            ImageIcon iconoBtnRegistrarse = new ImageIcon(salir);
            btnSalir.setIcon(iconoBtnRegistrarse);
        } else {
            System.err.println("Error, No se cargo el icono Registrarse");
        }

    }

    public void actualizarTextos() {
        setTitle(mi.get("app.titulo"));
        lblInicio.setText(mi.get("login.titulo"));
        lblUsuario.setText(mi.get("login.usuario"));
        lblContrasena.setText(mi.get("login.contrasenia"));
        lblIdioma.setText(mi.get("login.idioma"));

        btnIniciarSesion.setText(mi.get("login.boton.iniciar"));
        btnRegistrarse.setText(mi.get("login.boton.registrar"));
        btnOlvide.setText(mi.get("login.boton.olvidar"));
        btnSalir.setText(mi.get("login.boton.salir"));
        Locale currentLocale = mi.getLocale();
        int selectedIndex = 0;
        if ("en".equals(currentLocale.getLanguage()) && "US".equals(currentLocale.getCountry())) {
            selectedIndex = 1;
        } else if ("fr".equals(currentLocale.getLanguage()) && "FR".equals(currentLocale.getCountry())) {
            selectedIndex = 2;
        }
        if (cbxIdioma.getSelectedIndex() != selectedIndex) {
            cbxIdioma.setSelectedIndex(selectedIndex);
        }
        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }

}
