package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;

/**
 * La clase **MenuPrincipalView** representa la ventana principal de la aplicación
 * de gestión de un sistema de carrito de compras. Incluye una barra de menú
 * con opciones para la gestión de productos, carritos y usuarios, así como
 * opciones de salida y cambio de idioma. Se adapta a la internacionalización
 * y puede restringir ciertas funcionalidades basadas en el rol del usuario autenticado.
 */
public class MenuPrincipalView extends JFrame {
    private JMenuBar menuBar;
    private JMenu menuProducto;
    private JMenuItem menuCarrito;
    private JMenuItem menuItemCrearProducto;
    private JMenuItem menuItemEliminarProducto;
    private JMenuItem menuItemActualizarProducto;
    private JMenuItem menuItemBuscarProducto;
    private JDesktopPane jDesktopPane;
    private JMenuItem menuItemCrearCarrito;
    private JMenuItem menuItemListarCarrito;
    private JMenuItem menuItemEditarCarrito;
    private JMenuItem menuItemEliminarCarrito;
    private JMenuItem menuSalir;
    private JMenuItem menuItemCerrarSesion;
    private JMenuItem menuItemSalir;
    private JMenuItem menuUsuario;
    private JMenuItem menuItemCrearUsuario;
    private JMenuItem menuItemEliminarUsuario;
    private JMenuItem menuItemEditarUsuario;
    private JMenuItem menuItemListarUsuario;
    private JMenuItem menuIdioma;
    private JMenuItem menuItemEspanol;
    private JMenuItem menuItemIngles;
    private JMenuItem menuItemFrances;
    private String usuarioAutenticado;
    private MensajeInternacionalizacionHandler mi;

    /**
     * Constructor para la vista **MenuPrincipalView**.
     * Inicializa la ventana principal, la barra de menú con sus elementos,
     * el `JDesktopPane` como área de trabajo, y configura la internacionalización.
     * También, establece el usuario que ha iniciado sesión.
     *
     * @param mi El manejador de internacionalización de mensajes.
     * @param usuarioAutenticado El nombre del usuario que ha iniciado sesión.
     */
    public MenuPrincipalView(MensajeInternacionalizacionHandler mi, String usuarioAutenticado) {
        this.mi = mi;
        this.usuarioAutenticado = usuarioAutenticado;

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        jDesktopPane = new JDesktopPane();

        menuBar = new JMenuBar();
        menuProducto = new JMenu("Producto");
        menuCarrito = new JMenu("Carrito");
        menuSalir = new JMenu("Salir");
        menuUsuario = new JMenu("Usuario");
        menuIdioma = new JMenu("Idioma");

        menuItemEspanol = new JMenuItem("Espanol");
        menuItemIngles = new JMenuItem("Ingles");
        menuItemFrances = new JMenuItem("Frances");
        menuItemCrearProducto = new JMenuItem("Crear Producto");
        menuItemEliminarProducto = new JMenuItem("Eliminar Producto");
        menuItemActualizarProducto = new JMenuItem("Actualizar Producto");
        menuItemBuscarProducto = new JMenuItem("Buscar Producto");
        menuItemCrearCarrito = new JMenuItem("Crear Carrito");
        menuItemListarCarrito = new JMenuItem("Listar Carrito");
        menuItemEditarCarrito = new JMenuItem("Editar Carrito");
        menuItemEliminarCarrito = new JMenuItem("Eliminar Carrito");
        menuItemSalir = new JMenuItem("Salir");
        menuItemCerrarSesion = new JMenuItem("Cerrar Sesion");
        menuItemCrearUsuario = new JMenuItem("Crear Usuario");
        menuItemEliminarUsuario = new JMenuItem("Eliminar Usuario");
        menuItemEditarUsuario = new JMenuItem("Editar Usuario");
        menuItemListarUsuario = new JMenuItem("Listar Usuario");

        menuIdioma.add(menuItemEspanol);
        menuIdioma.add(menuItemIngles);
        menuIdioma.add(menuItemFrances);
        menuUsuario.add(menuItemCrearUsuario);
        menuUsuario.add(menuItemEliminarUsuario);
        menuUsuario.add(menuItemEditarUsuario);
        menuUsuario.add(menuItemListarUsuario);
        menuSalir.add(menuItemSalir);
        menuSalir.add(menuItemCerrarSesion);
        menuCarrito.add(menuItemCrearCarrito);
        menuCarrito.add(menuItemListarCarrito);
        menuCarrito.add(menuItemEditarCarrito);
        menuCarrito.add(menuItemEliminarCarrito);
        menuProducto.add(menuItemCrearProducto);
        menuProducto.add(menuItemEliminarProducto);
        menuProducto.add(menuItemActualizarProducto);
        menuProducto.add(menuItemBuscarProducto);
        menuBar.add(menuCarrito);
        menuBar.add(menuProducto);
        menuBar.add(menuSalir);
        menuBar.add(menuUsuario);
        menuBar.add(menuIdioma);
        setJMenuBar(menuBar);

        panelPrincipal.add(jDesktopPane, BorderLayout.CENTER);

        setContentPane(panelPrincipal);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema Carrito de Compras");
        setLocationRelativeTo(null);
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        cambiarIdioma();
    }

    /**
     * Establece la barra de menú para la ventana principal.
     *
     * @param menuBar La JMenuBar a establecer.
     */
    public void setMenuBar(JMenuBar menuBar) {
        this.menuBar = menuBar;
    }

    /**
     * Obtiene el menú "Producto".
     *
     * @return El JMenu `menuProducto`.
     */
    public JMenu getMenuProducto() {
        return menuProducto;
    }

    /**
     * Establece el menú "Producto".
     *
     * @param menuProducto El JMenu a establecer.
     */
    public void setMenuProducto(JMenu menuProducto) {
        this.menuProducto = menuProducto;
    }

    /**
     * Obtiene el menú "Carrito".
     *
     * @return El JMenuItem `menuCarrito`.
     */
    public JMenuItem getMenuCarrito() {
        return menuCarrito;
    }

    /**
     * Establece el menú "Carrito".
     *
     * @param menuCarrito El JMenuItem a establecer.
     */
    public void setMenuCarrito(JMenuItem menuCarrito) {
        this.menuCarrito = menuCarrito;
    }

    /**
     * Obtiene el ítem de menú "Crear Producto".
     *
     * @return El JMenuItem `menuItemCrearProducto`.
     */
    public JMenuItem getMenuItemCrearProducto() {
        return menuItemCrearProducto;
    }

    /**
     * Establece el ítem de menú "Crear Producto".
     *
     * @param menuItemCrearProducto El JMenuItem a establecer.
     */
    public void setMenuItemCrearProducto(JMenuItem menuItemCrearProducto) {
        this.menuItemCrearProducto = menuItemCrearProducto;
    }

    /**
     * Obtiene el ítem de menú "Eliminar Producto".
     *
     * @return El JMenuItem `menuItemEliminarProducto`.
     */
    public JMenuItem getMenuItemEliminarProducto() {
        return menuItemEliminarProducto;
    }

    /**
     * Establece el ítem de menú "Eliminar Producto".
     *
     * @param menuItemEliminarProducto El JMenuItem a establecer.
     */
    public void setMenuItemEliminarProducto(JMenuItem menuItemEliminarProducto) {
        this.menuItemEliminarProducto = menuItemEliminarProducto;
    }

    /**
     * Obtiene el ítem de menú "Actualizar Producto".
     *
     * @return El JMenuItem `menuItemActualizarProducto`.
     */
    public JMenuItem getMenuItemActualizarProducto() {
        return menuItemActualizarProducto;
    }

    /**
     * Establece el ítem de menú "Actualizar Producto".
     *
     * @param menuItemActualizarProducto El JMenuItem a establecer.
     */
    public void setMenuItemActualizarProducto(JMenuItem menuItemActualizarProducto) {
        this.menuItemActualizarProducto = menuItemActualizarProducto;
    }

    /**
     * Obtiene el ítem de menú "Buscar Producto".
     *
     * @return El JMenuItem `menuItemBuscarProducto`.
     */
    public JMenuItem getMenuItemBuscarProducto() {
        return menuItemBuscarProducto;
    }

    /**
     * Establece el ítem de menú "Buscar Producto".
     *
     * @param menuItemBuscarProducto El JMenuItem a establecer.
     */
    public void setMenuItemBuscarProducto(JMenuItem menuItemBuscarProducto) {
        this.menuItemBuscarProducto = menuItemBuscarProducto;
    }

    /**
     * Obtiene el `JDesktopPane` principal de la vista.
     *
     * @return El JDesktopPane `jDesktopPane`.
     */
    public JDesktopPane getjDesktopPane() {
        return jDesktopPane;
    }

    /**
     * Establece el `JDesktopPane` principal de la vista.
     *
     * @param jDesktopPane El JDesktopPane a establecer.
     */
    public void setjDesktopPane(JDesktopPane jDesktopPane) {
        this.jDesktopPane = jDesktopPane;
    }

    /**
     * Obtiene el ítem de menú "Crear Carrito".
     *
     * @return El JMenuItem `menuItemCrearCarrito`.
     */
    public JMenuItem getMenuItemCrearCarrito() {
        return menuItemCrearCarrito;
    }

    /**
     * Establece el ítem de menú "Crear Carrito".
     *
     * @param menuItemCrearCarrito El JMenuItem a establecer.
     */
    public void setMenuItemCrearCarrito(JMenuItem menuItemCrearCarrito) {
        this.menuItemCrearCarrito = menuItemCrearCarrito;
    }

    /**
     * Obtiene el ítem de menú "Listar Carrito".
     *
     * @return El JMenuItem `menuItemListarCarrito`.
     */
    public JMenuItem getMenuItemListarCarrito() {
        return menuItemListarCarrito;
    }

    /**
     * Establece el ítem de menú "Listar Carrito".
     *
     * @param menuItemListarCarrito El JMenuItem a establecer.
     */
    public void setMenuItemListarCarrito(JMenuItem menuItemListarCarrito) {
        this.menuItemListarCarrito = menuItemListarCarrito;
    }

    /**
     * Obtiene el ítem de menú "Editar Carrito".
     *
     * @return El JMenuItem `menuItemEditarCarrito`.
     */
    public JMenuItem getMenuItemEditarCarrito() {
        return menuItemEditarCarrito;
    }

    /**
     * Establece el ítem de menú "Editar Carrito".
     *
     * @param menuItemEditarCarrito El JMenuItem a establecer.
     */
    public void setMenuItemEditarCarrito(JMenuItem menuItemEditarCarrito) {
        this.menuItemEditarCarrito = menuItemEditarCarrito;
    }

    /**
     * Obtiene el ítem de menú "Eliminar Carrito".
     *
     * @return El JMenuItem `menuItemEliminarCarrito`.
     */
    public JMenuItem getMenuItemEliminarCarrito() {
        return menuItemEliminarCarrito;
    }

    /**
     * Establece el ítem de menú "Eliminar Carrito".
     *
     * @param menuItemEliminarCarrito El JMenuItem a establecer.
     */
    public void setMenuItemEliminarCarrito(JMenuItem menuItemEliminarCarrito) {
        this.menuItemEliminarCarrito = menuItemEliminarCarrito;
    }

    /**
     * Obtiene el menú "Salir".
     *
     * @return El JMenuItem `menuSalir`.
     */
    public JMenuItem getMenuSalir() {
        return menuSalir;
    }

    /**
     * Establece el menú "Salir".
     *
     * @param menuSalir El JMenuItem a establecer.
     */
    public void setMenuSalir(JMenuItem menuSalir) {
        this.menuSalir = menuSalir;
    }

    /**
     * Obtiene el ítem de menú "Cerrar Sesión".
     *
     * @return El JMenuItem `menuItemCerrarSesion`.
     */
    public JMenuItem getMenuItemCerrarSesion() {
        return menuItemCerrarSesion;
    }

    /**
     * Establece el ítem de menú "Cerrar Sesión".
     *
     * @param menuItemCerrarSesion El JMenuItem a establecer.
     */
    public void setMenuItemCerrarSesion(JMenuItem menuItemCerrarSesion) {
        this.menuItemCerrarSesion = menuItemCerrarSesion;
    }

    /**
     * Obtiene el ítem de menú "Salir".
     *
     * @return El JMenuItem `menuItemSalir`.
     */
    public JMenuItem getMenuItemSalir() {
        return menuItemSalir;
    }

    /**
     * Establece el ítem de menú "Salir".
     *
     * @param menuItemSalir El JMenuItem a establecer.
     */
    public void setMenuItemSalir(JMenuItem menuItemSalir) {
        this.menuItemSalir = menuItemSalir;
    }

    /**
     * Obtiene el menú "Usuario".
     *
     * @return El JMenuItem `menuUsuario`.
     */
    public JMenuItem getMenuUsuario() {
        return menuUsuario;
    }

    /**
     * Establece el menú "Usuario".
     *
     * @param menuUsuario El JMenuItem a establecer.
     */
    public void setMenuUsuario(JMenuItem menuUsuario) {
        this.menuUsuario = menuUsuario;
    }

    /**
     * Obtiene el ítem de menú "Crear Usuario".
     *
     * @return El JMenuItem `menuItemCrearUsuario`.
     */
    public JMenuItem getMenuItemCrearUsuario() {
        return menuItemCrearUsuario;
    }

    /**
     * Establece el ítem de menú "Crear Usuario".
     *
     * @param menuItemCrearUsuario El JMenuItem a establecer.
     */
    public void setMenuItemCrearUsuario(JMenuItem menuItemCrearUsuario) {
        this.menuItemCrearUsuario = menuItemCrearUsuario;
    }

    /**
     * Obtiene el ítem de menú "Eliminar Usuario".
     *
     * @return El JMenuItem `menuItemEliminarUsuario`.
     */
    public JMenuItem getMenuItemEliminarUsuario() {
        return menuItemEliminarUsuario;
    }

    /**
     * Establece el ítem de menú "Eliminar Usuario".
     *
     * @param menuItemEliminarUsuario El JMenuItem a establecer.
     */
    public void setMenuItemEliminarUsuario(JMenuItem menuItemEliminarUsuario) {
        this.menuItemEliminarUsuario = menuItemEliminarUsuario;
    }

    /**
     * Obtiene el ítem de menú "Editar Usuario".
     *
     * @return El JMenuItem `menuItemEditarUsuario`.
     */
    public JMenuItem getMenuItemEditarUsuario() {
        return menuItemEditarUsuario;
    }

    /**
     * Establece el ítem de menú "Editar Usuario".
     *
     * @param menuItemEditarUsuario El JMenuItem a establecer.
     */
    public void setMenuItemEditarUsuario(JMenuItem menuItemEditarUsuario) {
        this.menuItemEditarUsuario = menuItemEditarUsuario;
    }

    /**
     * Obtiene el ítem de menú "Listar Usuario".
     *
     * @return El JMenuItem `menuItemListarUsuario`.
     */
    public JMenuItem getMenuItemListarUsuario() {
        return menuItemListarUsuario;
    }

    /**
     * Establece el ítem de menú "Listar Usuario".
     *
     * @param menuItemListarUsuario El JMenuItem a establecer.
     */
    public void setMenuItemListarUsuario(JMenuItem menuItemListarUsuario) {
        this.menuItemListarUsuario = menuItemListarUsuario;
    }

    /**
     * Obtiene el menú "Idioma".
     *
     * @return El JMenuItem `menuIdioma`.
     */
    public JMenuItem getMenuIdioma() {
        return menuIdioma;
    }

    /**
     * Establece el menú "Idioma".
     *
     * @param menuIdioma El JMenuItem a establecer.
     */
    public void setMenuIdioma(JMenuItem menuIdioma) {
        this.menuIdioma = menuIdioma;
    }

    /**
     * Obtiene el ítem de menú "Español".
     *
     * @return El JMenuItem `menuItemEspanol`.
     */
    public JMenuItem getMenuItemEspanol() {
        return menuItemEspanol;
    }

    /**
     * Establece el ítem de menú "Español".
     *
     * @param menuItemEspanol El JMenuItem a establecer.
     */
    public void setMenuItemEspanol(JMenuItem menuItemEspanol) {
        this.menuItemEspanol = menuItemEspanol;
    }

    /**
     * Obtiene el ítem de menú "Inglés".
     *
     * @return El JMenuItem `menuItemIngles`.
     */
    public JMenuItem getMenuItemIngles() {
        return menuItemIngles;
    }

    /**
     * Establece el ítem de menú "Inglés".
     *
     * @param menuItemIngles El JMenuItem a establecer.
     */
    public void setMenuItemIngles(JMenuItem menuItemIngles) {
        this.menuItemIngles = menuItemIngles;
    }

    /**
     * Obtiene el ítem de menú "Francés".
     *
     * @return El JMenuItem `menuItemFrances`.
     */
    public JMenuItem getMenuItemFrances() {
        return menuItemFrances;
    }

    /**
     * Establece el ítem de menú "Francés".
     *
     * @param menuItemFrances El JMenuItem a establecer.
     */
    public void setMenuItemFrances(JMenuItem menuItemFrances) {
        this.menuItemFrances = menuItemFrances;
    }

    /**
     * Obtiene el nombre del usuario autenticado.
     *
     * @return El nombre del usuario autenticado.
     */
    public String getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    /**
     * Establece el nombre del usuario autenticado.
     *
     * @param usuarioAutenticado El nombre del usuario a establecer.
     */
    public void setUsuarioAutenticado(String usuarioAutenticado) {
        this.usuarioAutenticado = usuarioAutenticado;
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
     * Deshabilita los elementos del menú que corresponden a funcionalidades
     * exclusivas del rol de administrador, ocultándolos para usuarios con roles diferentes.
     */
    public void deshabilitarMenusAdministrador() {
        getMenuItemCrearProducto().setEnabled(false);
        getMenuItemBuscarProducto().setEnabled(false);
        getMenuItemActualizarProducto().setEnabled(false);
        getMenuItemEliminarProducto().setEnabled(false);
        getMenuItemCrearUsuario().setEnabled(false);
        getMenuItemEliminarUsuario().setEnabled(false);
        getMenuItemListarUsuario().setEnabled(false);
        getMenuItemEditarUsuario().setEnabled(false);
    }

    /**
     * Cambia el idioma de todos los componentes de la interfaz de usuario
     * utilizando el `MensajeInternacionalizacionHandler`.
     * Actualiza el título de la ventana y los textos de todos los menús y sus ítems.
     * También actualiza los textos predeterminados de los botones de `JOptionPane`.
     */
    public void cambiarIdioma() {
        setTitle(mi.get("principal.titulo") + " - " + usuarioAutenticado);

        menuProducto.setText(mi.get("principal.menu.producto"));
        menuCarrito.setText(mi.get("principal.menu.carrito"));
        menuSalir.setText(mi.get("principal.menu.salir"));
        menuUsuario.setText(mi.get("principal.menu.usuario"));
        menuIdioma.setText(mi.get("principal.menu.idioma"));

        menuItemCrearProducto.setText(mi.get("principal.producto.crear"));
        menuItemEliminarProducto.setText(mi.get("principal.producto.eliminar"));
        menuItemActualizarProducto.setText(mi.get("principal.producto.actualizar"));
        menuItemBuscarProducto.setText(mi.get("principal.producto.buscar"));

        menuItemCrearCarrito.setText(mi.get("principal.carrito.crear"));
        menuItemListarCarrito.setText(mi.get("principal.carrito.listar"));
        menuItemEditarCarrito.setText(mi.get("principal.carrito.editar"));
        menuItemEliminarCarrito.setText(mi.get("principal.carrito.eliminar"));

        menuItemSalir.setText(mi.get("principal.menu.salir.sistema"));
        menuItemCerrarSesion.setText(mi.get("principal.menu.salir.sesion"));

        menuItemCrearUsuario.setText(mi.get("principal.usuario.crear"));
        menuItemEliminarUsuario.setText(mi.get("principal.usuario.eliminar"));
        menuItemEditarUsuario.setText(mi.get("principal.usuario.editar"));
        menuItemListarUsuario.setText(mi.get("principal.usuario.listar"));

        menuItemEspanol.setText(mi.get("principal.idioma.es"));
        menuItemIngles.setText(mi.get("principal.idioma.en"));
        menuItemFrances.setText(mi.get("principal.idioma.fr"));

        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }
}