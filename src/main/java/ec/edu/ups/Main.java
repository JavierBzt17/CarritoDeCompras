package ec.edu.ups;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.controlador.UsuarioController;
import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.CuestionarioDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.dao.impl.CarritoDAOMemoria;
import ec.edu.ups.dao.impl.CuestionarioDAOMemoria;
import ec.edu.ups.dao.impl.ProductoDAOMemoria;
import ec.edu.ups.dao.impl.UsuarioDAOMemoria;
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

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            MensajeInternacionalizacionHandler  mi = new MensajeInternacionalizacionHandler("es", "EC");

            ProductoDAO productoDAO = new ProductoDAOMemoria();
            CarritoDAO carritoDAO = new CarritoDAOMemoria();

            CuestionarioDAO cuestionarioDAO = new CuestionarioDAOMemoria();
            UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();

            LoginView loginView = new LoginView(mi);

            loginView.setVisible(true);

            UsuarioController usuarioController = new UsuarioController(usuarioDAO, loginView, cuestionarioDAO, mi);


            loginView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    Usuario usuarioAuntenticado = usuarioController.getUsuarioAutenticado();
                    if(usuarioAuntenticado != null) {

                        MenuPrincipalView principalView = new MenuPrincipalView(mi,usuarioAuntenticado.getUsuario());
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

                        ProductoController productoController = new ProductoController(productoDAO, productoAnadirView,
                                productoListaView, productoEditarView, productoEliminarView, carritoAnadirView, mi);

                        CarritoController carritoController = new CarritoController(carritoAnadirView, productoDAO, carritoDAO,usuarioAuntenticado,
                                carritoListaView, carritoModificarView, carritoEliminarView, mi);

                        UsuarioController usuarioController = new UsuarioController(usuarioDAO, usuarioCrearView, usuarioEliminarView, usuarioModificarView,
                                usuarioListarView, mi);

                        principalView.mostrarMensaje(mi.get("principal.bienvenido") + usuarioAuntenticado.getUsuario());
                        principalView.setTitle(mi.get("principal.titulo") + " - " + usuarioAuntenticado.getUsuario());
                        if (usuarioAuntenticado.getRol().equals(Rol.USUARIO)) {
                            principalView.deshabilitarMenusAdministrador();
                        }

                        principalView.getMenuItemCrearUsuario().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(!usuarioCrearView.isVisible()) {
                                    usuarioCrearView.setVisible(true);
                                    principalView.getjDesktopPane().add(usuarioCrearView);
                                }
                            }
                        });
                        principalView.getMenuItemEliminarUsuario().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(!usuarioEliminarView.isVisible()) {
                                    usuarioEliminarView.setVisible(true);
                                    principalView.getjDesktopPane().add(usuarioEliminarView);
                                }
                            }
                        });
                        principalView.getMenuItemEditarUsuario().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(!usuarioModificarView.isVisible()) {
                                    usuarioModificarView.setVisible(true);
                                    principalView.getjDesktopPane().add(usuarioModificarView);
                                }
                            }
                        });
                        principalView.getMenuItemListarUsuario().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(!usuarioListarView.isVisible()) {
                                    usuarioListarView.setVisible(true);
                                    principalView.getjDesktopPane().add(usuarioListarView);
                                }
                            }
                        });
                        principalView.getMenuItemCrearCarrito().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(!carritoAnadirView.isVisible()) {
                                    carritoAnadirView.setVisible(true);
                                    principalView.getjDesktopPane().add(carritoAnadirView);
                                }
                            }
                        });
                        principalView.getMenuItemListarCarrito().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(!carritoListaView.isVisible()) {
                                    carritoListaView.setVisible(true);
                                    principalView.getjDesktopPane().add(carritoListaView);
                                }
                            }
                        });
                        principalView.getMenuItemEditarCarrito().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(!carritoModificarView.isVisible()) {
                                    carritoModificarView.setVisible(true);
                                    principalView.getjDesktopPane().add(carritoModificarView);
                                }
                            }
                        });
                        principalView.getMenuItemEliminarCarrito().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(!carritoEliminarView.isVisible()) {
                                    carritoEliminarView.setVisible(true);
                                    principalView.getjDesktopPane().add(carritoEliminarView);
                                }
                            }
                        });
                        principalView.getMenuItemCrearProducto().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(!productoAnadirView.isVisible()) {
                                    productoAnadirView.setVisible(true);
                                    principalView.getjDesktopPane().add(productoAnadirView);
                                }
                            }
                        });
                        principalView.getMenuItemActualizarProducto().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(!productoEditarView.isVisible()) {
                                    productoEditarView.setVisible(true);
                                    principalView.getjDesktopPane().add(productoEditarView);
                                }
                            }
                        });
                        principalView.getMenuItemEliminarProducto().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(!productoEliminarView.isVisible()) {
                                    productoEliminarView.setVisible(true);
                                    principalView.getjDesktopPane().add(productoEliminarView);
                                }
                            }
                        });
                        principalView.getMenuItemBuscarProducto().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(!productoListaView.isVisible()) {
                                    productoListaView.setVisible(true);
                                    principalView.getjDesktopPane().add(productoListaView);
                                }
                            }
                        });
                        principalView.getMenuItemCerrarSesion().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                boolean confirmado = principalView.mostrarMensajePregunta(mi.get("principal.cerrar"));
                                if(confirmado) {
                                    principalView.dispose();
                                    usuarioController.setUsuarioAutenticado(null);
                                    loginView.actualizarTextos();
                                    loginView.setVisible(true);
                                }
                            }
                        });
                        principalView.getMenuItemSalir().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                boolean confirmado = principalView.mostrarMensajePregunta(mi.get("principal.salir"));
                                if(confirmado) {
                                    principalView.dispose();
                                    System.exit(0);
                                }
                            }
                        });
                        principalView.getMenuItemEspanol().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                mi.setLenguaje("es", "EC");
                                principalView.cambiarIdioma();
                                usuarioController.actualizarIdiomaEnVistas();
                                productoController.actualizarIdiomaEnVistas();
                                carritoController.actualizarIdiomaEnVistas();
                            }
                        });
                        principalView.getMenuItemIngles().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                mi.setLenguaje("en", "US");
                                principalView.cambiarIdioma();
                                usuarioController.actualizarIdiomaEnVistas();
                                productoController.actualizarIdiomaEnVistas();
                                carritoController.actualizarIdiomaEnVistas();
                            }
                        });
                        principalView.getMenuItemFrances().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                mi.setLenguaje("fr", "FR");
                                principalView.cambiarIdioma();
                                usuarioController.actualizarIdiomaEnVistas();
                                productoController.actualizarIdiomaEnVistas();
                                carritoController.actualizarIdiomaEnVistas();
                            }
                        });
                    }
                }
            });
        });
    }
}