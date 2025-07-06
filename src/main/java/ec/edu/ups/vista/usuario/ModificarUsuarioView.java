package ec.edu.ups.vista.usuario;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URL;

public class ModificarUsuarioView extends JInternalFrame {
    private JLabel lblModificar;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JTextField txtContrasena;
    private JButton btnEditar;
    private JPanel panelPrincipal;
    private JTextField txtUsuario;
    private JLabel lblCodigo;
    private JLabel lblUsuario;
    private JLabel lblContrasena;
    private JTextField txtNombre;
    private JTextField txtTelefono;
    private JTextField txtCorreo;
    private JSpinner spnDia;
    private JSpinner spnMes;
    private JSpinner spnAno;
    private JLabel lblNombre;
    private JLabel lblFecha;
    private JLabel lblTelefono;
    private JLabel lblCorreo;
    private JTextField txtUs;
    private JLabel lblUs;
    private MensajeInternacionalizacionHandler mi;

    public ModificarUsuarioView(MensajeInternacionalizacionHandler mi) {
        super("Editar Usuarios", true,true,false,true);
        this.mi = mi;
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        cambiarIdioma();
        inicializarImagenes();
        spnDia.setModel(new SpinnerNumberModel(1, 1, 31, 1));
        spnMes.setModel(new SpinnerNumberModel(1, 1, 12, 1));
        spnAno.setModel(new SpinnerNumberModel(2000, 1, 2100, 1));
        habilitarCampos(false);
    }

    public JLabel getLblModificar() {
        return lblModificar;
    }

    public void setLblModificar(JLabel lblModificar) {
        this.lblModificar = lblModificar;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public void setTxtCodigo(JTextField txtCodigo) {
        this.txtCodigo = txtCodigo;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JTextField getTxtContrasena() {
        return txtContrasena;
    }

    public void setTxtContrasena(JTextField txtContrasena) {
        this.txtContrasena = txtContrasena;
    }

    public JButton getBtnEditar() {
        return btnEditar;
    }

    public void setBtnEditar(JButton btnEditar) {
        this.btnEditar = btnEditar;
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

    public JLabel getLblCodigo() {
        return lblCodigo;
    }

    public void setLblCodigo(JLabel lblCodigo) {
        this.lblCodigo = lblCodigo;
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

    public JTextField getTxtUs() {
        return txtUs;
    }

    public void setTxtUs(JTextField txtUs) {
        this.txtUs = txtUs;
    }

    public JLabel getLblUs() {
        return lblUs;
    }

    public void setLblUs(JLabel lblUs) {
        this.lblUs = lblUs;
    }

    public void cambiarIdioma() {
        setTitle(mi.get("usuario.modificar.titulo.ventana"));
        lblUs.setText(mi.get("usuario.modificar.buscar"));
        lblUsuario.setText(mi.get("usuario.modificar.nombre"));
        lblContrasena.setText(mi.get("usuario.modificar.contrasena"));
        btnBuscar.setText(mi.get("usuario.modificar.buscar.btn"));
        btnEditar.setText(mi.get("usuario.modificar.editar.btn"));

        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public boolean mostrarMensajePregunta(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, "Confirmación",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }
    public void limpiarCampos() {
        txtUs.setText("");
        txtUsuario.setText("");
        txtContrasena.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        spnDia.setValue(0);
        spnMes.setValue(0);
        spnAno.setValue(0);
    }

    public void inicializarImagenes(){
        URL buscar = ModificarUsuarioView.class.getClassLoader().getResource("imagenes/buscar.png");
        if (buscar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(buscar);
            btnBuscar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error, No se cargo el icono Login");
        }

        URL editar = ModificarUsuarioView.class.getClassLoader().getResource("imagenes/editar.png");
        if (editar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(editar);
            btnEditar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error, No se cargo el icono Login");
        }
    }
    public void habilitarCampos(boolean habilitar) {
        txtUsuario.setEnabled(habilitar);
        txtContrasena.setEnabled(habilitar);
        txtNombre.setEnabled(habilitar);
        txtTelefono.setEnabled(habilitar);
        txtCorreo.setEnabled(habilitar);
        spnDia.setEnabled(habilitar);
        spnMes.setEnabled(habilitar);
        spnAno.setEnabled(habilitar);
    }
}







