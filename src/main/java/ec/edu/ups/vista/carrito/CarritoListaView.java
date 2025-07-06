package ec.edu.ups.vista.carrito;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class CarritoListaView extends JInternalFrame{
    private JPanel panelPrincipal;
    private JButton btnVer;
    private JTable tblProducto;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JButton btnListar;
    private JLabel lblCodigo;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mi;

    public CarritoListaView(MensajeInternacionalizacionHandler mi) {
        setContentPane(panelPrincipal);
        setTitle("Listado de Carritos");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setIconifiable(true);

        modelo = new DefaultTableModel();
        tblProducto.setModel(modelo);

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

    public JButton getBtnVer() {
        return btnVer;
    }

    public void setBtnVer(JButton btnVer) {
        this.btnVer = btnVer;
    }

    public JTable getTblProducto() {
        return tblProducto;
    }

    public void setTblProducto(JTable tblProducto) {
        this.tblProducto = tblProducto;
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

    public JButton getBtnListar() {
        return btnListar;
    }

    public void setBtnListar(JButton btnListar) {
        this.btnListar = btnListar;
    }

    public JLabel getLblCodigo() {
        return lblCodigo;
    }

    public void setLblCodigo(JLabel lblCodigo) {
        this.lblCodigo = lblCodigo;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    public void cargarDatos(List<Carrito> listaCarritos) {
        modelo.setNumRows(0);

        for (Carrito carrito : listaCarritos) {
            System.out.println("Cargando carrito: Código = " + carrito.getCodigo() +
                    ", Subtotal = " + carrito.calcularSubtotal());
            String fechaFormateada = String.valueOf(carrito.getFechaCreacion());
            Object[] fila = {
                    carrito.getCodigo(),
                    carrito.getUsuario().getUsuario(),
                    fechaFormateada,
                    String.format("%.2f", carrito.calcularSubtotal()),
                    String.format("%.2f", carrito.calcularIVA()),
                    String.format("%.2f", carrito.calcularTotal())
            };
            modelo.addRow(fila);
        }
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void cambiarIdioma(){
        mi.setLenguaje(mi.getLocale().getLanguage(), mi.getLocale().getCountry());

        setTitle(mi.get("carrito.lista.titulo.ventana"));
        lblCodigo.setText(mi.get("carrito.lista.buscar.codigo"));
        btnBuscar.setText(mi.get("carrito.lista.boton.buscar"));
        btnListar.setText(mi.get("carrito.lista.boton.listar"));
        btnVer.setText(mi.get("carrito.lista.boton.detalle"));

        Object[] columnas = {
                mi.get("carrito.lista.columna.codigo"),
                mi.get("carrito.lista.columna.usuario"),
                mi.get("carrito.lista.columna.fecha"),
                mi.get("carrito.lista.columna.subtotal"),
                mi.get("carrito.lista.columna.iva"),
                mi.get("carrito.lista.columna.total")
        };
        modelo.setColumnIdentifiers(columnas);

        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }

    public void inicializarImagenes(){
        URL eliminar = CarritoListaView.class.getClassLoader().getResource("imagenes/listar.png");
        if (eliminar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(eliminar);
            btnListar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error, No se cargo el icono Login");
        }

        URL buscar = CarritoListaView.class.getClassLoader().getResource("imagenes/buscar.png");
        if (buscar != null) {
            ImageIcon iconoBtnRegistrarse = new ImageIcon(buscar);
            btnBuscar.setIcon(iconoBtnRegistrarse);
        } else {
            System.err.println("Error, No se cargo el icono Registrarse");
        }

        URL detalle = CarritoListaView.class.getClassLoader().getResource("imagenes/detalles.png");
        if (detalle != null) {
            ImageIcon iconoBtnRegistrarse = new ImageIcon(detalle);
            btnVer.setIcon(iconoBtnRegistrarse);
        } else {
            System.err.println("Error, No se cargo el icono Registrarse");
        }
    }
}
