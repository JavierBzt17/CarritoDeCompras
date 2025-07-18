package ec.edu.ups.vista.usuario;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.util.Locale;
import java.net.URL;

/**
 * La clase **LoginView** representa la interfaz gráfica de usuario para el inicio de sesión.
 * Permite a los usuarios ingresar su cédula y contraseña, iniciar sesión, registrarse,
 * recuperar su contraseña u salir de la aplicación. También incluye funcionalidad para
 * cambiar el idioma de la interfaz.
 */
public class LoginView extends JFrame {
    private JPanel panelPrincipal;
    private JTextField txtCedula;
    private JPasswordField txtContrasena;
    private JButton btnIniciarSesion;
    private JButton btnRegistrarse;
    private JButton btnOlvide;
    private JButton btnSalir;
    private JLabel lblInicio;
    private JLabel lblCedula;
    private JLabel lblContrasena;
    private JComboBox cbxIdioma;
    private JLabel lblIdioma;
    private MensajeInternacionalizacionHandler mi;

    /**
     * Constructor para la vista LoginView.
     * Inicializa la ventana principal, establece su tamaño, posición y operación de cierre.
     * Carga las imágenes de los botones y los componentes iniciales, y actualiza los textos según el idioma.
     *
     * @param mi El manejador de internacionalización de mensajes para la vista.
     */
    public LoginView(MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        setTitle("Iniciar Sesion");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(panelPrincipal);
        inicializarImagenes();
        inicializarComponentes();
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
     * Obtiene el campo de texto para la cédula del usuario.
     *
     * @return El JTextField `txtCedula`.
     */
    public JTextField getTxtCedula() {
        return txtCedula;
    }

    /**
     * Establece el campo de texto para la cédula del usuario.
     *
     * @param txtCedula El JTextField a establecer.
     */
    public void setTxtCedula(JTextField txtCedula) {
        this.txtCedula = txtCedula;
    }

    /**
     * Obtiene el campo de contraseña para el usuario.
     *
     * @return El JPasswordField `txtContrasena`.
     */
    public JPasswordField getTxtContrasena() {
        return txtContrasena;
    }

    /**
     * Establece el campo de contraseña para el usuario.
     *
     * @param txtContrasena El JPasswordField a establecer.
     */
    public void setTxtContrasena(JPasswordField txtContrasena) {
        this.txtContrasena = txtContrasena;
    }

    /**
     * Obtiene el botón "Iniciar Sesión".
     *
     * @return El JButton `btnIniciarSesion`.
     */
    public JButton getBtnIniciarSesion() {
        return btnIniciarSesion;
    }

    /**
     * Obtiene el botón "Registrarse".
     *
     * @return El JButton `btnRegistrarse`.
     */
    public JButton getBtnRegistrarse() {
        return btnRegistrarse;
    }

    /**
     * Obtiene el botón "Olvidé mi contraseña".
     *
     * @return El JButton `btnOlvide`.
     */
    public JButton getBtnOlvide() {
        return btnOlvide;
    }

    /**
     * Obtiene el botón "Salir".
     *
     * @return El JButton `btnSalir`.
     */
    public JButton getBtnSalir() {
        return btnSalir;
    }

    /**
     * Obtiene el JComboBox para la selección de idioma.
     *
     * @return El JComboBox `cbxIdioma`.
     */
    public JComboBox getCbxIdioma() {
        return cbxIdioma;
    }

    /**
     * Muestra un mensaje informativo en un cuadro de diálogo.
     *
     * @param mensaje El mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Muestra un cuadro de diálogo de confirmación con una pregunta y devuelve la respuesta del usuario.
     * El título del diálogo es "Confirmación".
     *
     * @param mensaje La pregunta a mostrar.
     * @return true si el usuario selecciona "Sí", false en caso contrario.
     */
    public boolean mostrarMensajePregunta(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, "Confirmación",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }

    /**
     * Inicializa los componentes de la vista, como el JComboBox de idiomas,
     * poblándolo con opciones traducidas y actualizando los textos de la interfaz.
     */
    public void inicializarComponentes() {
        cbxIdioma.removeAllItems();
        cbxIdioma.addItem(mi.get("menu.idioma.es"));
        cbxIdioma.addItem(mi.get("menu.idioma.en"));
        cbxIdioma.addItem(mi.get("menu.idioma.fr"));
        actualizarTextos();
    }

    /**
     * Inicializa las imágenes para los botones **Iniciar Sesión**, **Registrarse**,
     * **Olvide** y **Salir**, cargándolos desde la carpeta "imagenes" en los recursos del proyecto.
     * Si no se encuentran los recursos, se imprime un mensaje de error en la consola.
     */
    public void inicializarImagenes() {
        URL loginURL = getClass().getClassLoader().getResource("imagenes/login.png");
        if (loginURL != null) {
            btnIniciarSesion.setIcon(new ImageIcon(loginURL));
        } else {
            System.err.println("Error: No se pudo cargar el icono 'login.png'");
        }

        URL registrarseURL = getClass().getClassLoader().getResource("imagenes/registrarse.png");
        if (registrarseURL != null) {
            btnRegistrarse.setIcon(new ImageIcon(registrarseURL));
        } else {
            System.err.println("Error: No se pudo cargar el icono 'registrarse.png'");
        }

        URL olvidoURL = getClass().getClassLoader().getResource("imagenes/confusion.png");
        if (olvidoURL != null) {
            btnOlvide.setIcon(new ImageIcon(olvidoURL));
        } else {
            System.err.println("Error: No se pudo cargar el icono 'confusion.png'");
        }

        URL salirURL = getClass().getClassLoader().getResource("imagenes/salir.png");
        if (salirURL != null) {
            btnSalir.setIcon(new ImageIcon(salirURL));
        } else {
            System.err.println("Error: No se pudo cargar el icono 'salir.png'");
        }
    }

    /**
     * Actualiza los textos de las etiquetas y botones en la interfaz de usuario
     * utilizando el `MensajeInternacionalizacionHandler` para soportar diferentes idiomas.
     * También selecciona el idioma correcto en el `cbxIdioma` y actualiza los textos
     * predeterminados de los botones de `JOptionPane`.
     */
    public void actualizarTextos() {
        setTitle(mi.get("app.titulo"));
        lblInicio.setText(mi.get("login.titulo"));
        lblCedula.setText(mi.get("login.cedula"));
        lblContrasena.setText(mi.get("login.contrasenia"));
        lblIdioma.setText(mi.get("login.idioma"));

        btnIniciarSesion.setText(mi.get("login.boton.iniciar"));
        btnRegistrarse.setText(mi.get("login.boton.registrar"));
        btnOlvide.setText(mi.get("login.boton.olvidar"));
        btnSalir.setText(mi.get("login.boton.salir"));

        Locale currentLocale = mi.getLocale();
        int selectedIndex = 0; // Español por defecto
        if ("en".equals(currentLocale.getLanguage())) {
            selectedIndex = 1;
        } else if ("fr".equals(currentLocale.getLanguage())) {
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