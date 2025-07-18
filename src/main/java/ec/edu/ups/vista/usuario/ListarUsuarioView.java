package ec.edu.ups.vista.usuario;

import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.Formateador;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
import java.util.List;
import java.util.Locale;

/**
 * La clase **ListarUsuarioView** representa la vista de la interfaz gráfica de usuario
 * para listar y buscar usuarios en el sistema. Permite a los usuarios buscar por cédula
 * y visualizar una tabla con la información detallada de los usuarios, incluyendo su cédula,
 * nombre, teléfono, correo y fecha de nacimiento. Soporta la internacionalización del texto
 * y la carga de imágenes para los botones.
 */
public class ListarUsuarioView extends JInternalFrame {
    private JTextField txtCedula;
    private JButton btnBuscar;
    private JButton btnListar;
    private JTable tblUsuario;
    private JPanel panelPrincipal;
    private JLabel lblCedula;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mi;

    /**
     * Constructor para la vista ListarUsuarioView.
     * Inicializa la ventana interna, configura sus propiedades,
     * y establece el manejador de internacionalización.
     * También, carga las traducciones y las imágenes de los botones.
     *
     * @param mi El manejador de internacionalización de mensajes para la vista.
     */
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

    /**
     * Obtiene el campo de texto para la cédula de búsqueda.
     *
     * @return El JTextField `txtCedula`.
     */
    public JTextField getTxtCedula() {
        return txtCedula;
    }

    /**
     * Establece el campo de texto para la cédula de búsqueda.
     *
     * @param txtCedula El JTextField a establecer.
     */
    public void setTxtCedula(JTextField txtCedula) {
        this.txtCedula = txtCedula;
    }

    /**
     * Obtiene el botón de búsqueda.
     *
     * @return El JButton `btnBuscar`.
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Establece el botón de búsqueda.
     *
     * @param btnBuscar El JButton a establecer.
     */
    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    /**
     * Obtiene el botón para listar todos los usuarios.
     *
     * @return El JButton `btnListar`.
     */
    public JButton getBtnListar() {
        return btnListar;
    }

    /**
     * Establece el botón para listar todos los usuarios.
     *
     * @param btnListar El JButton a establecer.
     */
    public void setBtnListar(JButton btnListar) {
        this.btnListar = btnListar;
    }

    /**
     * Obtiene la tabla de usuarios.
     *
     * @return La JTable `tblUsuario`.
     */
    public JTable getTblUsuario() {
        return tblUsuario;
    }

    /**
     * Establece la tabla de usuarios.
     *
     * @param tblUsuario La JTable a establecer.
     */
    public void setTblUsuario(JTable tblUsuario) {
        this.tblUsuario = tblUsuario;
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
     * Obtiene la etiqueta para la cédula de búsqueda.
     *
     * @return El JLabel `lblCedula`.
     */
    public JLabel getLblCedula() {
        return lblCedula;
    }

    /**
     * Establece la etiqueta para la cédula de búsqueda.
     *
     * @param lblCedula El JLabel a establecer.
     */
    public void setLblCedula(JLabel lblCedula) {
        this.lblCedula = lblCedula;
    }

    /**
     * Obtiene el modelo de tabla por defecto para la tabla de usuarios.
     *
     * @return El DefaultTableModel `modelo`.
     */
    public DefaultTableModel getModelo() {
        return modelo;
    }

    /**
     * Establece el modelo de tabla por defecto para la tabla de usuarios.
     *
     * @param modelo El DefaultTableModel a establecer.
     */
    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    /**
     * Cambia el idioma de todos los componentes de la vista utilizando el `MensajeInternacionalizacionHandler`.
     * Actualiza el título de la ventana, los textos de etiquetas, botones y los encabezados de la tabla.
     * También actualiza los textos predeterminados de los botones de `JOptionPane`.
     */
    public void cambiarIdioma() {
        setTitle(mi.get("usuario.listar.titulo"));
        lblCedula.setText(mi.get("usuario.listar.buscar.cedula"));
        btnBuscar.setText(mi.get("usuario.listar.boton.buscar"));
        btnListar.setText(mi.get("usuario.listar.boton.listar"));

        Object[] columnas = {
                mi.get("usuario.listar.columna.cedula"),
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

    /**
     * Carga los datos de una lista de objetos **Usuario** en la tabla.
     * Limpia la tabla existente y añade filas con la cédula, contraseña (oculta),
     * nombre, teléfono, correo y fecha de nacimiento (formateada) de cada usuario.
     *
     * @param listaUsuarios La lista de objetos **Usuario** a mostrar en la tabla.
     */
    public void cargarDatos(List<Usuario> listaUsuarios) {
        modelo.setNumRows(0);
        Locale locale = mi.getLocale();

        for (Usuario usuario : listaUsuarios) {
            String fechaFormateada = Formateador.formatearFecha(usuario.getFecha().getTime(), locale);

            Object[] fila = {
                    usuario.getCedula(),
                    "********", // Es una buena práctica no mostrar la contraseña en la tabla.
                    usuario.getNombre(),
                    usuario.getTelefono(),
                    usuario.getCorreo(),
                    fechaFormateada
            };
            modelo.addRow(fila);
        }
    }

    /**
     * Inicializa las imágenes para los botones **Buscar** y **Listar**
     * cargándolos desde la carpeta "imagenes" en los recursos del proyecto.
     * Si no se encuentran los recursos, se imprime un mensaje de error en la consola.
     */
    public void inicializarImagenes(){
        URL buscarUrl = getClass().getClassLoader().getResource("imagenes/buscar.png");
        if (buscarUrl != null) {
            btnBuscar.setIcon(new ImageIcon(buscarUrl));
        } else {
            System.err.println("Error: No se pudo cargar el icono 'buscar.png'");
        }
        URL listarUrl = getClass().getClassLoader().getResource("imagenes/listar.png");
        if (listarUrl != null) {
            btnListar.setIcon(new ImageIcon(listarUrl));
        } else {
            System.err.println("Error: No se pudo cargar el icono 'listar.png'");
        }
    }
}