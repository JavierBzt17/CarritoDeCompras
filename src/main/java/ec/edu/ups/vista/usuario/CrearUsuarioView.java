package ec.edu.ups.vista.usuario;


import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.net.URL;

public class CrearUsuarioView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtUsuario;
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
    private JLabel lblUsuario;
    private JLabel lblContrasena;
    private MensajeInternacionalizacionHandler mi;

    public CrearUsuarioView(MensajeInternacionalizacionHandler mi) {
        super("Registrar Usuarios", true,true,false,true);
        this.mi = mi;
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        spnDia.setModel(new SpinnerNumberModel(1, 1, 31, 1));
        spnMes.setModel(new SpinnerNumberModel(1, 1, 12, 1));
        spnAno.setModel(new SpinnerNumberModel(2000, 1, 2100, 1));

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
        cambiarIdioma();
        inicializarImagenes();
    }
    public void limpiarCampos() {
        txtUsuario.setText("");
        txtContrasena.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        spnDia.setValue(0);
        spnMes.setValue(0);
        spnAno.setValue(0);
    }
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public boolean mostrarMensajePregunta(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, "Confirmación",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
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

    public JButton getBtnAceptar() {
        return btnAceptar;
    }

    public void setBtnAceptar(JButton btnAceptar) {
        this.btnAceptar = btnAceptar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public void setBtnLimpiar(JButton btnLimpiar) {
        this.btnLimpiar = btnLimpiar;
    }

    public JLabel getLblRegistrar() {
        return lblRegistrar;
    }

    public void setLblRegistrar(JLabel lblRegistrar) {
        this.lblRegistrar = lblRegistrar;
    }

    public JTextField getTxtCorreo() {
        return txtCorreo;
    }

    public void setTxtCorreo(JTextField txtCorreo) {
        this.txtCorreo = txtCorreo;
    }

    public JTextField getTxtTelefono() {
        return txtTelefono;
    }

    public void setTxtTelefono(JTextField txtTelefono) {
        this.txtTelefono = txtTelefono;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
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

    public JLabel getLblNombre() {
        return lblNombre;
    }

    public void setLblNombre(JLabel lblNombre) {
        this.lblNombre = lblNombre;
    }

    public JLabel getLblFecha() {
        return lblFecha;
    }

    public void setLblFecha(JLabel lblFecha) {
        this.lblFecha = lblFecha;
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

    public void inicializarImagenes(){
        URL aceptar = CrearUsuarioView.class.getClassLoader().getResource("imagenes/aceptar.png");
        if (aceptar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(aceptar);
            btnAceptar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error, No se cargo el icono Login");
        }

        URL limpiar = CrearUsuarioView.class.getClassLoader().getResource("imagenes/limpiar.png");
        if (limpiar != null) {
            ImageIcon iconoBtnRegistrarse = new ImageIcon(limpiar);
            btnLimpiar.setIcon(iconoBtnRegistrarse);
        } else {
            System.err.println("Error, No se cargo el icono Registrarse");
        }
    }

    public void cambiarIdioma() {
        setTitle(mi.get("usuario.crear.titulo.ventana"));
        lblRegistrar.setText(mi.get("usuario.crear.titulo"));
        lblUsuario.setText(mi.get("usuario.crear.nombre"));
        lblContrasena.setText(mi.get("usuario.crear.contrasena"));
        btnAceptar.setText(mi.get("usuario.crear.aceptar"));
        btnLimpiar.setText(mi.get("usuario.crear.limpiar"));

        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }
}
