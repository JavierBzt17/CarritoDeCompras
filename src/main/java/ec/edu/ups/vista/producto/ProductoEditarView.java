package ec.edu.ups.vista.producto;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;

public class ProductoEditarView extends JInternalFrame{
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JLabel lblEditar;
    private JButton btnActualizar;
    private JButton btnBuscar;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mi;

    public ProductoEditarView(MensajeInternacionalizacionHandler mi) {
        setContentPane(panelPrincipal);
        setTitle("Edición de Productos");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        //setResizable(false);
        //setLocation(100,500);
        //setVisible(true);
        //pack();
        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setIconifiable(true);
        this.mi = mi;
        cambiarIdioma();
        inicializarImagenes();
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
        txtNombre.setText("");
        txtPrecio.setText("");
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public void setTxtCodigo(JTextField txtCodigo) {
        this.txtCodigo = txtCodigo;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public void setTxtPrecio(JTextField txtPrecio) {
        this.txtPrecio = txtPrecio;
    }

    public JLabel getLblCodigo() {
        return lblCodigo;
    }

    public void setLblCodigo(JLabel lblCodigo) {
        this.lblCodigo = lblCodigo;
    }

    public JLabel getLblNombre() {
        return lblNombre;
    }

    public void setLblNombre(JLabel lblNombre) {
        this.lblNombre = lblNombre;
    }

    public JLabel getLblPrecio() {
        return lblPrecio;
    }

    public void setLblPrecio(JLabel lblPrecio) {
        this.lblPrecio = lblPrecio;
    }

    public JLabel getLblEditar() {
        return lblEditar;
    }

    public void setLblEditar(JLabel lblEditar) {
        this.lblEditar = lblEditar;
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public void setBtnActualizar(JButton btnActualizar) {
        this.btnActualizar = btnActualizar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    public void cambiarIdioma() {
        setTitle(mi.get("producto.editar.titulo.ventana"));

        if (lblCodigo != null) lblCodigo.setText(mi.get("producto.editar.codigo"));
        if (lblNombre != null) lblNombre.setText(mi.get("producto.editar.nombre"));
        if (lblPrecio != null) lblPrecio.setText(mi.get("producto.editar.precio"));

        if (btnBuscar != null) btnBuscar.setText(mi.get("producto.editar.buscar"));
        if (btnActualizar != null) btnActualizar.setText(mi.get("producto.editar.actualizar"));
        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }

    public void inicializarImagenes(){
        URL buscar = ProductoEditarView.class.getClassLoader().getResource("imagenes/buscar.png");
        if (buscar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(buscar);
            btnBuscar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error, No se ha cargo el icono Login");
        }

        URL editar = ProductoEditarView.class.getClassLoader().getResource("imagenes/editar.png");
        if (editar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(editar);
            btnActualizar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error, No se cargo el iconoLogin");
        }
    }
}
