package ec.edu.ups;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.controlador.UsuarioController;
import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.CuestionarioDAO;
import ec.edu.ups.dao.FabricaDAO; // Importar FabricaDAO
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.dao.impl.CarritoDAOMemoria;
import ec.edu.ups.dao.impl.CuestionarioDAOMemoria;
import ec.edu.ups.dao.impl.ProductoDAOMemoria;
// import ec.edu.ups.dao.impl.UsuarioDAOMemoria; // ⬅️ Ya no necesitamos importar la implementación de memoria directamente
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.*;
import ec.edu.ups.vista.carrito.CarritoAnadirView;
import ec.edu.ups.vista.carrito.CarritoEliminarView;
import ec.edu.ups.vista.carrito.CarritoListaView;
import ec.edu.ups.vista.carrito.CarritoModificarView;
import ec.edu.ups.vista.producto.ProductoAnadirView;
import ec.edu.ups.vista.producto.ProductoEditarView;
import ec.edu.ups.vista.producto.ProductoEliminarView;
import ec.edu.ups.vista.producto.ProductoListaView;
import ec.edu.ups.vista.usuario.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * La clase **Main** es el punto de entrada principal de la aplicación.
 * Se encarga de inicializar todos los componentes del sistema, como los DAOs,
 * las vistas y los controladores, y de establecer la interacción entre ellos.
 * Configura la vista de login y, tras una autenticación exitosa, despliega la
 * ventana del menú principal con las funcionalidades correspondientes al rol del usuario.
 * También gestiona el cambio de idioma de la interfaz.
 */
public class Main {
    /**
     * El método `main` es el punto de inicio de la aplicación.
     * Se ejecuta en el hilo de despacho de eventos de AWT para asegurar
     * la correcta manipulación de la interfaz gráfica.
     *
     * @param args Argumentos de la línea de comandos (no utilizados en esta aplicación).
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            MensajeInternacionalizacionHandler mi = new MensajeInternacionalizacionHandler("es", "EC");

            // Inicialización de los DAOs (capa de datos)
            ProductoDAO productoDAO = new ProductoDAOMemoria();
            CarritoDAO carritoDAO = new CarritoDAOMemoria();
            CuestionarioDAO cuestionarioDAO = new CuestionarioDAOMemoria();

            // ⬅️ CAMBIO CLAVE AQUÍ: Usar FabricaDAO para obtener el UsuarioDAO
            // Puedes elegir "TEXTO" o "BINARIO" para determinar qué DAO usar.
            // Por ejemplo, para usar el DAO de texto:
            UsuarioDAO usuarioDAO = FabricaDAO.getUsuarioDAO("TEXTO");
            // O para usar el DAO binario:
            // UsuarioDAO usuarioDAO = FabricaDAO.getUsuarioDAO("BINARIO");
            // ---------------------------------------------------------------


            // Inicialización de la vista de Login y su controlador
            LoginView loginView = new LoginView(mi);
            UsuarioController loginController = new UsuarioController(usuarioDAO, loginView, cuestionarioDAO, mi);
            loginView.setVisible(true);

            loginView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    Usuario usuarioAutenticado = loginController.getUsuarioAutenticado();

                    if (usuarioAutenticado != null) {
                        MenuPrincipalView principalView = new MenuPrincipalView(mi, usuarioAutenticado.getNombre());

                        // --- Inicialización de todas las vistas ---
                        CarritoAnadirView carritoAnadirView = new CarritoAnadirView(mi);
                        CarritoListaView carritoListaView = new CarritoListaView(mi);
                        CarritoModificarView carritoModificarView = new CarritoModificarView(mi);
                        CarritoEliminarView carritoEliminarView = new CarritoEliminarView(mi);

                        ProductoAnadirView productoAnadirView = new ProductoAnadirView(mi);
                        ProductoEditarView productoEditarView = new ProductoEditarView(mi);
                        ProductoEliminarView productoEliminarView = new ProductoEliminarView(mi);
                        ProductoListaView productoListaView = new ProductoListaView(mi);

                        CrearUsuarioView usuarioCrearView = new CrearUsuarioView(mi);
                        EliminarUsuarioView usuarioEliminarView = new EliminarUsuarioView(mi);
                        ModificarUsuarioView usuarioModificarView = new ModificarUsuarioView(mi);
                        ListarUsuarioView usuarioListarView = new ListarUsuarioView(mi);

                        // --- Inicialización de los controladores para las vistas internas ---
                        ProductoController productoController = new ProductoController(productoDAO, productoAnadirView,
                                productoListaView, productoEditarView, productoEliminarView, carritoAnadirView, mi);

                        CarritoController carritoController = new CarritoController(carritoAnadirView, productoDAO, carritoDAO, usuarioAutenticado,
                                carritoListaView, carritoModificarView, carritoEliminarView, mi);

                        UsuarioController userManagementController = new UsuarioController(usuarioDAO, usuarioCrearView, usuarioEliminarView, usuarioModificarView,
                                usuarioListarView, mi);

                        principalView.mostrarMensaje(mi.get("principal.bienvenido") + " " + usuarioAutenticado.getNombre());
                        principalView.setTitle(mi.get("principal.titulo") + " - " + usuarioAutenticado.getNombre());

                        if (usuarioAutenticado.getRol().equals(Rol.USUARIO)) {
                            principalView.deshabilitarMenusAdministrador();
                        }

                        // Acción para Crear Producto
                        principalView.getMenuItemCrearProducto().addActionListener(e1 -> {
                            if (principalView.getjDesktopPane().getComponents().length > 0) {
                                principalView.getjDesktopPane().removeAll();
                            }
                            principalView.getjDesktopPane().add(productoAnadirView);
                            productoAnadirView.setVisible(true);
                            productoAnadirView.limpiarCampos();
                        });

                        // Acción para Eliminar Producto
                        principalView.getMenuItemEliminarProducto().addActionListener(e1 -> {
                            if (principalView.getjDesktopPane().getComponents().length > 0) {
                                principalView.getjDesktopPane().removeAll();
                            }
                            principalView.getjDesktopPane().add(productoEliminarView);
                            productoEliminarView.setVisible(true);
                            productoEliminarView.limpiarCampos();
                        });

                        // Acción para Actualizar Producto
                        principalView.getMenuItemActualizarProducto().addActionListener(e1 -> {
                            if (principalView.getjDesktopPane().getComponents().length > 0) {
                                principalView.getjDesktopPane().removeAll();
                            }
                            principalView.getjDesktopPane().add(productoEditarView);
                            productoEditarView.setVisible(true);
                            productoEditarView.limpiarCampos();
                        });

                        // Acción para Buscar Producto (realmente es Listar Productos)
                        principalView.getMenuItemBuscarProducto().addActionListener(e1 -> {
                            if (principalView.getjDesktopPane().getComponents().length > 0) {
                                principalView.getjDesktopPane().removeAll();
                            }
                            principalView.getjDesktopPane().add(productoListaView);
                            productoListaView.setVisible(true);
                        });

                        // Acción para Crear Carrito
                        principalView.getMenuItemCrearCarrito().addActionListener(e1 -> {
                            if (principalView.getjDesktopPane().getComponents().length > 0) {
                                principalView.getjDesktopPane().removeAll();
                            }
                            principalView.getjDesktopPane().add(carritoAnadirView);
                            carritoAnadirView.setVisible(true);
                            carritoAnadirView.limpiarCampos();
                        });

                        // Acción para Listar Carrito
                        principalView.getMenuItemListarCarrito().addActionListener(e1 -> {
                            if (principalView.getjDesktopPane().getComponents().length > 0) {
                                principalView.getjDesktopPane().removeAll();
                            }
                            principalView.getjDesktopPane().add(carritoListaView);
                            carritoListaView.setVisible(true);
                        });

                        // Acción para Editar Carrito
                        principalView.getMenuItemEditarCarrito().addActionListener(e1 -> {
                            if (principalView.getjDesktopPane().getComponents().length > 0) {
                                principalView.getjDesktopPane().removeAll();
                            }
                            principalView.getjDesktopPane().add(carritoModificarView);
                            carritoModificarView.setVisible(true);
                        });

                        // Acción para Eliminar Carrito
                        principalView.getMenuItemEliminarCarrito().addActionListener(e1 -> {
                            if (principalView.getjDesktopPane().getComponents().length > 0) {
                                principalView.getjDesktopPane().removeAll();
                            }
                            principalView.getjDesktopPane().add(carritoEliminarView);
                            carritoEliminarView.setVisible(true);
                            carritoEliminarView.limpiarCampos();
                        });

                        // Acciones de Usuario (para roles de administrador)
                        principalView.getMenuItemCrearUsuario().addActionListener(e1 -> {
                            if (principalView.getjDesktopPane().getComponents().length > 0) {
                                principalView.getjDesktopPane().removeAll();
                            }
                            principalView.getjDesktopPane().add(usuarioCrearView);
                            usuarioCrearView.setVisible(true);
                            usuarioCrearView.limpiarCampos();
                        });

                        principalView.getMenuItemEliminarUsuario().addActionListener(e1 -> {
                            if (principalView.getjDesktopPane().getComponents().length > 0) {
                                principalView.getjDesktopPane().removeAll();
                            }
                            principalView.getjDesktopPane().add(usuarioEliminarView);
                            usuarioEliminarView.setVisible(true);
                            usuarioEliminarView.limpiarCampos();
                        });

                        principalView.getMenuItemEditarUsuario().addActionListener(e1 -> {
                            if (principalView.getjDesktopPane().getComponents().length > 0) {
                                principalView.getjDesktopPane().removeAll();
                            }
                            principalView.getjDesktopPane().add(usuarioModificarView);
                            usuarioModificarView.setVisible(true);
                            usuarioModificarView.limpiarCampos();
                        });

                        principalView.getMenuItemListarUsuario().addActionListener(e1 -> {
                            if (principalView.getjDesktopPane().getComponents().length > 0) {
                                principalView.getjDesktopPane().removeAll();
                            }
                            principalView.getjDesktopPane().add(usuarioListarView);
                            usuarioListarView.setVisible(true);
                        });

                        principalView.getMenuItemCerrarSesion().addActionListener(e1 -> {
                            boolean confirmado = principalView.mostrarMensajePregunta(mi.get("principal.cerrar"));
                            if(confirmado) {
                                principalView.dispose();
                                loginController.setUsuarioAutenticado(null);
                                loginView.actualizarTextos();
                                loginView.setVisible(true);
                            }
                        });

                        principalView.getMenuItemSalir().addActionListener(e1 -> {
                            boolean confirmado = principalView.mostrarMensajePregunta(mi.get("principal.salir"));
                            if(confirmado) {
                                System.exit(0);
                            }
                        });

                        // Lógica para cambiar de idioma
                        ActionListener languageListener = e1 -> {
                            if (e1.getSource() == principalView.getMenuItemEspanol()) {
                                mi.setLenguaje("es", "EC");
                            } else if (e1.getSource() == principalView.getMenuItemIngles()) {
                                mi.setLenguaje("en", "US");
                            } else if (e1.getSource() == principalView.getMenuItemFrances()) {
                                mi.setLenguaje("fr", "FR");
                            }
                            principalView.cambiarIdioma();
                            userManagementController.actualizarIdiomaEnVistas();
                            productoController.actualizarIdiomaEnVistas();
                            carritoController.actualizarIdiomaEnVistas();
                        };
                        principalView.getMenuItemEspanol().addActionListener(languageListener);
                        principalView.getMenuItemIngles().addActionListener(languageListener);
                        principalView.getMenuItemFrances().addActionListener(languageListener);
                    }
                }
            });
        });
    }
}