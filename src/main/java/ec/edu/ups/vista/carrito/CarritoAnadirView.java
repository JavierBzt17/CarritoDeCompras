package ec.edu.ups.vista.carrito;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

public class CarritoAnadirView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JButton btnAnadir;
    private JTable tblProductos;
    private JTextField txtSubtotal;
    private JTextField txtIVA;
    private JTextField txtTotal;
    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JComboBox cbxCantidad;
    private JButton btnBorrar;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JLabel lblCantidad;
    private JLabel lblSubtotal;
    private JLabel lblIVA;
    private JLabel lblTotal;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mi;

    public CarritoAnadirView(MensajeInternacionalizacionHandler mi) {
        super("Carrito de Compras", true, true, false, true);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(700, 700);

        modelo = new DefaultTableModel();
        tblProductos.setModel(modelo);

        cargarDatos();
        this.mi = mi;
        cambiarIdioma();
        inicializarImagenes();
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

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
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

    public JButton getBtnAnadir() {
        return btnAnadir;
    }

    public void setBtnAnadir(JButton btnAnadir) {
        this.btnAnadir = btnAnadir;
    }

    public JTable getTblProductos() {
        return tblProductos;
    }

    public void setTblProductos(JTable tblProductos) {
        this.tblProductos = tblProductos;
    }

    public JTextField getTxtSubtotal() {
        return txtSubtotal;
    }

    public void setTxtSubtotal(JTextField txtSubtotal) {
        this.txtSubtotal = txtSubtotal;
    }

    public JTextField getTxtIVA() {
        return txtIVA;
    }

    public void setTxtIVA(JTextField txtIVA) {
        this.txtIVA = txtIVA;
    }

    public JTextField getTxtTotal() {
        return txtTotal;
    }

    public void setTxtTotal(JTextField txtTotal) {
        this.txtTotal = txtTotal;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public void setBtnGuardar(JButton btnGuardar) {
        this.btnGuardar = btnGuardar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public void setBtnLimpiar(JButton btnLimpiar) {
        this.btnLimpiar = btnLimpiar;
    }

    public JComboBox getCbxCantidad() {
        return cbxCantidad;
    }

    public void setCbxCantidad(JComboBox cbxCantidad) {
        this.cbxCantidad = cbxCantidad;
    }

    public JButton getBtnBorrar() {
        return btnBorrar;
    }

    public void setBtnBorrar(JButton btnBorrar) {
        this.btnBorrar = btnBorrar;
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

    public JLabel getLblCantidad() {
        return lblCantidad;
    }

    public void setLblCantidad(JLabel lblCantidad) {
        this.lblCantidad = lblCantidad;
    }

    public JLabel getLblSubtotal() {
        return lblSubtotal;
    }

    public void setLblSubtotal(JLabel lblSubtotal) {
        this.lblSubtotal = lblSubtotal;
    }

    public JLabel getLblIVA() {
        return lblIVA;
    }

    public void setLblIVA(JLabel lblIVA) {
        this.lblIVA = lblIVA;
    }

    public JLabel getLblTotal() {
        return lblTotal;
    }

    public void setLblTotal(JLabel lblTotal) {
        this.lblTotal = lblTotal;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    private void cargarDatos() {
        cbxCantidad.removeAllItems();
        for (int i = 0; i < 20; i++) {
            cbxCantidad.addItem(String.valueOf(i + 1));
        }
    }

    public void cambiarIdioma() {
        mi.setLenguaje(mi.getLocale().getLanguage(), mi.getLocale().getCountry());

        try {
            setTitle(mi.get("carrito.anadir.titulo"));
            lblCodigo.setText(mi.get("carrito.anadir.buscar.codigo"));
            lblNombre.setText(mi.get("carrito.anadir.nombre"));
            lblPrecio.setText(mi.get("carrito.anadir.precio"));
            lblCantidad.setText(mi.get("carrito.anadir.cantidad"));
            lblSubtotal.setText(mi.get("carrito.anadir.subtotal"));
            lblIVA.setText(mi.get("carrito.anadir.iva"));
            lblTotal.setText(mi.get("carrito.anadir.total"));

            btnBuscar.setText(mi.get("carrito.anadir.boton.buscar"));
            btnAnadir.setText(mi.get("carrito.anadir.boton.anadir"));
            btnGuardar.setText(mi.get("carrito.anadir.boton.aceptar"));
            btnLimpiar.setText(mi.get("carrito.anadir.boton.limpiar"));
            btnBorrar.setText(mi.get("carrito.anadir.boton.borrar"));

            modelo.setColumnIdentifiers(new Object[]{
                    mi.get("carrito.anadir.tabla.codigo"),
                    mi.get("carrito.anadir.tabla.nombre"),
                    mi.get("carrito.anadir.tabla.precio"),
                    mi.get("carrito.anadir.tabla.cantidad"),
                    mi.get("carrito.anadir.tabla.subtotal")
            });

            UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
            UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
            UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
            UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));

        } catch (Exception e) {
            System.err.println("⚠️ Error de clave de idioma: " + e.getMessage());
        }
    }

    public void inicializarImagenes() {
        cargarIcono("imagenes/aceptar.png", btnGuardar, "guardar");
        cargarIcono("imagenes/limpiar.png", btnLimpiar, "limpiar");
        cargarIcono("imagenes/anadir.png", btnAnadir, "añadir");
        cargarIcono("imagenes/borrar.png", btnBorrar, "borrar");
        cargarIcono("imagenes/buscar.png", btnBuscar, "buscar");
    }

    private void cargarIcono(String ruta, JButton boton, String nombre) {
        URL recurso = getClass().getClassLoader().getResource(ruta);
        if (recurso != null) {
            boton.setIcon(new ImageIcon(recurso));
        } else {
            System.err.println("Error: No se cargo el icono " + nombre);
        }
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

}
