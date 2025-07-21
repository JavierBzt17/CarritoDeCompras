package ec.edu.ups;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.controlador.UsuarioController;
import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.CuestionarioDAO;
import ec.edu.ups.dao.FabricaDAO; // Se mantiene la importación de la fábrica
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.UsuarioDAO;
// Ya no se necesitan las importaciones directas a las implementaciones de memoria
import ec.edu.ups.dao.impl.CuestionarioDAOMemoria;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.DirectorySelector; // Importación de la nueva utilidad
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

import javax.swing.*; // Importación para JOptionPane y UIManager
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * La clase Main es el punto de entrada principal de la aplicación.
 * Ahora, primero pregunta al usuario el modo de persistencia deseado
 * antes de inicializar los componentes del sistema.
 */
public class Main {
    /**
     * El método `main` es el punto de inicio de la aplicación.
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            try {
                // Mejora la apariencia de las ventanas para que coincida con el sistema operativo
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // --- INICIO: LÓGICA DE SELECCIÓN DE PERSISTENCIA ---
            String[] opciones = {"En Memoria", "Archivos de Texto", "Archivos Binarios"};
            int eleccion = JOptionPane.showOptionDialog(null, "¿Cómo deseas gestionar los datos?",
                    "Selector de Modo de Persistencia", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, opciones, opciones[0]);

            FabricaDAO fabrica = FabricaDAO.getFabrica();

            switch (eleccion) {
                case 0: // En Memoria
                    fabrica.setPersistencia(FabricaDAO.Tipo.MEMORIA);
                    break;
                case 1: // Archivos de Texto
                case 2: // Archivos Binarios
                    String ruta = DirectorySelector.selectDirectory();
                    if (ruta == null) {
                        System.out.println("Operación cancelada por el usuario. Saliendo.");
                        System.exit(0);
                    }
                    fabrica.setBasePath(ruta);
                    fabrica.setPersistencia(eleccion == 1 ? FabricaDAO.Tipo.TEXTO : FabricaDAO.Tipo.BINARIO);
                    break;
                default: // El usuario cerró la ventana
                    System.out.println("Aplicación cerrada.");
                    System.exit(0);
                    return; // Salir del método
            }
            // --- FIN: LÓGICA DE SELECCIÓN DE PERSISTENCIA ---


            MensajeInternacionalizacionHandler mi = new MensajeInternacionalizacionHandler("es", "EC");

            // --- CAMBIO CLAVE: Obtener todos los DAO desde la fábrica ya configurada ---
            UsuarioDAO usuarioDAO = fabrica.getUsuarioDAO();
            ProductoDAO productoDAO = fabrica.getProductoDAO();
            CarritoDAO carritoDAO = fabrica.getCarritoDAO();
            // CuestionarioDAO cuestionarioDAO = fabrica.getCuestionarioDAO(); // Descomentar cuando lo implementes en FabricaDAO

            // Inicialización de la vista de Login y su controlador
            LoginView loginView = new LoginView(mi);
            // Pasar un CuestionarioDAOMemoria temporal si aún no está en la fábrica
            UsuarioController loginController = new UsuarioController(usuarioDAO, loginView, new CuestionarioDAOMemoria(), mi);
            loginView.setVisible(true);

            loginView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    Usuario usuarioAutenticado = loginController.getUsuarioAutenticado();

                    if (usuarioAutenticado != null) {
                        // El resto del código permanece exactamente igual...
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

                        // ... Todos tus ActionListeners permanecen sin cambios ...
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