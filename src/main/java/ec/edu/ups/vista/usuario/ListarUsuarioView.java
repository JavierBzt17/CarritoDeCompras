package ec.edu.ups.vista.usuario;

import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.Formateador;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
import java.util.List;
import java.util.Locale;


public class ListarUsuarioView extends JInternalFrame {
    private JTextField txtNombre;
    private JButton btnBuscar;
    private JButton btnListar;
    private JTable tblUsuario;
    private JPanel panelPrincipal;
    private JLabel lblNombre;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mi;


    public ListarUsuarioView(MensajeInternacionalizacionHandler mi) {
        super("Listar Usuarios", true,true,false,true);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);

        modelo = new DefaultTableModel();
        tblUsuario.setModel(modelo);
        this.mi = mi;
        cambiarIdioma();
        inicializarImagenes();
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
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

    public JTable getTblUsuario() {
        return tblUsuario;
    }

    public void setTblUsuario(JTable tblUsuario) {
        this.tblUsuario = tblUsuario;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JLabel getLblNombre() {
        return lblNombre;
    }

    public void setLblNombre(JLabel lblNombre) {
        this.lblNombre = lblNombre;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    public void cambiarIdioma() {
        setTitle(mi.get("usuario.listar.titulo"));
        lblNombre.setText(mi.get("usuario.listar.buscar"));
        btnBuscar.setText(mi.get("usuario.listar.boton.buscar"));
        btnListar.setText(mi.get("usuario.listar.boton.listar"));

        Object[] columnas = {
                mi.get("usuario.listar.columna.username"),
                mi.get("usuario.listar.columna.contrasenia"),
                mi.get("usuario.listar.columna.nombre"),
                mi.get("usuario.listar.columna.celular"),
                mi.get("usuario.listar.columna.correo"),
                mi.get("usuario.listar.columna.fecha")
        };
        modelo.setColumnIdentifiers(columnas);

        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }

    public void cargarDatos(List<Usuario> listaUsuarios) {
        modelo.setNumRows(0);
        Locale locale = mi.getLocale();

        for (Usuario usuario : listaUsuarios) {
            String fechaFormateada = Formateador.formatearFecha(usuario.getFecha().getTime(), locale);

            Object[] fila = {
                    usuario.getUsuario(),
                    usuario.getContrasena(),
                    usuario.getNombre(),
                    usuario.getTelefono(),
                    usuario.getCorreo(),
                    fechaFormateada
            };
            modelo.addRow(fila);
        }
    }


    public void inicializarImagenes(){
        URL buscar = ListarUsuarioView.class.getClassLoader().getResource("imagenes/buscar.png");
        if (buscar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(buscar);
            btnBuscar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error, No se cargo el icono Login");
        }
        URL listar = ListarUsuarioView.class.getClassLoader().getResource("imagenes/listar.png");
        if (listar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(listar);
            btnListar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error, No se cargo el icono Login");
        }
    }

}
