package ec.edu.ups.controlador;

import ec.edu.ups.dao.CuestionarioDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Cuestionario;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.preguntas.RecuperarContraseñaView;
import ec.edu.ups.vista.preguntas.CuestionarioView;
import ec.edu.ups.vista.usuario.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class UsuarioController {
    private Usuario usuario;
    private final UsuarioDAO usuarioDAO;
    private LoginView loginView;
    private CrearUsuarioView usuarioCrearView;
    private EliminarUsuarioView usuarioEliminarView;
    private ModificarUsuarioView usuarioModificarView;
    private ListarUsuarioView usuarioListarView;
    private CuestionarioDAO cuestionarioDAO;
    private final MensajeInternacionalizacionHandler mi;

    public UsuarioController(UsuarioDAO usuarioDAO, LoginView loginView, CuestionarioDAO cuestionarioDAO,
                             MensajeInternacionalizacionHandler mi) {
        this.usuarioDAO = usuarioDAO;
        this.loginView = loginView;
        this.usuario = null;
        this.cuestionarioDAO = cuestionarioDAO;
        this.mi = mi;

        configurarEventosLogin();
    }

    public UsuarioController (UsuarioDAO usuarioDAO, CrearUsuarioView usuarioCrearView, EliminarUsuarioView usuarioEliminarView,
                              ModificarUsuarioView usuarioModificarView, ListarUsuarioView usuarioListarView, MensajeInternacionalizacionHandler mi) {
        this.usuarioDAO = usuarioDAO;
        this.usuarioCrearView = usuarioCrearView;
        this.usuarioEliminarView = usuarioEliminarView;
        this.usuarioModificarView = usuarioModificarView;
        this.usuarioListarView = usuarioListarView;
        this.mi = mi;
        configurarEventosUsuario();
    }
    private void configurarEventosLogin(){
        loginView.getBtnIniciarSesion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autenticar();
            }
        });
        loginView.getBtnRegistrarse().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrar();
            }
        });
        loginView.getBtnOlvide().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recuperar();
            }
        });
        loginView.getBtnSalir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salir();
            }
        });
        loginView.getCbxIdioma().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarIdioma();
            }
        });
    }

    private void configurarEventosUsuario(){
        usuarioCrearView.getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crear();
            }
        });
        usuarioEliminarView.getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminar();
            }
        });
        usuarioEliminarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarEliminar();
            }
        });
        usuarioModificarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarModificar();
            }
        });
        usuarioModificarView.getBtnEditar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editar();
            }
        });
        usuarioListarView.getBtnListar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listar();
            }
        });
        usuarioListarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarUsario();
            }
        });
    }



    private void cambiarIdioma() {
        String[] clavesIdiomas = {"es", "en", "fr"};
        String[] paisesIdiomas = {"EC", "US", "FR"};
        int index = loginView.getCbxIdioma().getSelectedIndex();

        if (index >= 0 && index < 3) {
            mi.setLenguaje(clavesIdiomas[index], paisesIdiomas[index]);
            loginView.actualizarTextos();
        }
    }

    private void salir(){
        loginView.dispose();
        System.exit(0);
    }

    private void autenticar() {
        String username = loginView.getTxtUsuario().getText().trim();
        String contrasenia = loginView.getTxtContrasena().getText().trim();

        usuario = usuarioDAO.autenticar(username, contrasenia);

        if (usuario == null) {
            loginView.mostrarMensaje(mi.get("login.mensaje.error_autenticacion"));
        } else {
            Cuestionario cuestionario = cuestionarioDAO.buscarPorUsername(username);

            if (cuestionario == null || cuestionario.getRespuestas().size() < 3) {
                loginView.mostrarMensaje(mi.get("login.mensaje.incompleto"));

                CuestionarioView cuestionarioView = new CuestionarioView(mi);
                CuestionarioController controller = new CuestionarioController(
                        cuestionarioView, cuestionarioDAO, usuarioDAO, usuario, mi, true);
                cuestionarioView.setVisible(true);
                loginView.setVisible(false);

                cuestionarioView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                cuestionarioView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loginView.setVisible(true);
                    }
                });

            } else {
                loginView.dispose();
            }
        }

    }


    public Usuario getUsuarioAutenticado() {
        return usuario;
    }

    public void setUsuarioAutenticado(Usuario usuario) {
        this.usuario = usuario;
    }

    private void registrar() {
        boolean confirmado = loginView.mostrarMensajePregunta(mi.get("login.mensaje.pregunta_registro"));
        if (confirmado) {
            CuestionarioView cuestionarioView = new CuestionarioView(mi);
            CuestionarioController cuestionarioController = new CuestionarioController(
                    cuestionarioView, cuestionarioDAO, usuarioDAO, mi
            );
            cuestionarioView.setVisible(true);

            loginView.setVisible(false);

            cuestionarioView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            cuestionarioView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loginView.setVisible(true);
                }
            });
        } else {
            loginView.mostrarMensaje(mi.get("login.mensaje.creacion_cancelada"));
        }
    }

    private void recuperar() {
        boolean confirmado = loginView.mostrarMensajePregunta(mi.get("login.mensaje.pregunta_recuperar"));
        if (confirmado) {
            String username = loginView.getTxtUsuario().getText().trim();

            Usuario usuario = usuarioDAO.buscarPorUsername(username);
            if (usuario == null) {
                loginView.mostrarMensaje(mi.get("login.mensaje.usuario_no_encontrado"));
                return;
            }

            if (usuario.getRol() == Rol.ADMINISTRADOR) {
                loginView.mostrarMensaje(mi.get("login.mensaje.recuperacion_no_disponible_admin"));
                return;
            }

            Cuestionario cuestionario = cuestionarioDAO.buscarPorUsername(username);
            if (cuestionario == null || cuestionario.getRespuestas().isEmpty()) {
                loginView.mostrarMensaje(mi.get("login.mensaje.sin_preguntas"));
                return;
            }

            RecuperarContraseñaView recuperarView = new RecuperarContraseñaView(mi);
            CuestionarioController controller = new CuestionarioController(
                    recuperarView, cuestionarioDAO, username, usuario.getContrasena(), mi
            );

            recuperarView.setVisible(true);
            loginView.setVisible(false);

            recuperarView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            recuperarView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loginView.setVisible(true);
                }
            });

        } else {
            loginView.mostrarMensaje(mi.get("login.mensaje.recuperacion_cancelada"));
        }
    }


    private void crear() {
        boolean confirmado = usuarioCrearView.mostrarMensajePregunta(mi.get("usuario.mensaje.crear.pregunta"));
        if (!confirmado) {
            usuarioCrearView.mostrarMensaje(mi.get("usuario.mensaje.crear.cancelado"));
            return;
        }

        String usuario = usuarioCrearView.getTxtUsuario().getText().trim();
        String contrasena = usuarioCrearView.getTxtContrasena().getText().trim();
        String nombre = usuarioCrearView.getTxtNombre().getText().trim();
        String telefono = usuarioCrearView.getTxtTelefono().getText().trim();
        String correo = usuarioCrearView.getTxtCorreo().getText().trim();
        int dia = (int) usuarioCrearView.getSpnDia().getValue();
        int mes = (int) usuarioCrearView.getSpnMes().getValue();
        int ano = (int) usuarioCrearView.getSpnAno().getValue();

        if (usuario.isEmpty() || contrasena.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || correo.isEmpty()) {
            usuarioCrearView.mostrarMensaje(mi.get("cuestionario.finalizar.error_datos_vacios"));
            return;
        }

        if (!telefono.matches("\\d{7,15}")) {
            usuarioCrearView.mostrarMensaje("Número de celular inválido.");
            return;
        }

        if (!correo.matches("^[\\w\\.-]+@[\\w\\.-]+\\.[a-zA-Z]{2,}$")) {
            usuarioCrearView.mostrarMensaje("Correo electrónico inválido.");
            return;
        }

        if (dia < 1 || dia > 31 || mes < 1 || mes > 12 || ano < 1) {
            usuarioCrearView.mostrarMensaje("Fecha de nacimiento inválida.");
            return;
        }

        if (usuarioDAO.buscarPorUsername(usuario) != null) {
            usuarioCrearView.mostrarMensaje(mi.get("usuario.mensaje.crear.existe"));
            return;
        }

        GregorianCalendar fechaNacimiento = new GregorianCalendar(ano, mes - 1, dia); // mes - 1 porque enero = 0
        Usuario usuario1 = new Usuario(usuario, contrasena, Rol.USUARIO, nombre, telefono, fechaNacimiento, correo);
        usuarioDAO.crear(usuario1);

        usuarioCrearView.mostrarMensaje(mi.get("usuario.mensaje.creado"));
        usuarioCrearView.limpiarCampos();
    }


    private void eliminar() {
        boolean confirmado = usuarioEliminarView.mostrarMensajePregunta(mi.get("usuario.mensaje.eliminar.pregunta"));
        if (!confirmado) {
            usuarioEliminarView.mostrarMensaje(mi.get("usuario.mensaje.crear.cancelado"));
            return;
        }

        String user= usuarioEliminarView.getTxtUsuario().getText().trim();

        if (user.isEmpty()) {
            usuarioEliminarView.mostrarMensaje("Ingrese un nombre de usuario.");
            return;
        }

        Usuario usuario = usuarioDAO.buscarPorUsername(user);

        if (usuario == null) {
            usuarioEliminarView.mostrarMensaje("El usuario que usted busca no existe.");
            return;
        }

        usuarioDAO.eliminar(user);
        usuarioEliminarView.mostrarMensaje(mi.get("usuario.mensaje.eliminado"));
        usuarioEliminarView.limpiarCampos();
    }

    private void buscarEliminar() {
        String username = usuarioEliminarView.getTxtUsuario().getText().trim();

        if (username.isEmpty()) {
            usuarioEliminarView.mostrarMensaje("Ingrese el nombre de usuario que desea buscar.");
            return;
        }

        Usuario usuario = usuarioDAO.buscarPorUsername(username);

        if (usuario == null) {
            usuarioEliminarView.mostrarMensaje("Usuario no encontrado.");
            usuarioEliminarView.limpiarCampos();
            return;
        }

        usuarioEliminarView.getTxtContrasena().setText(usuario.getContrasena());
        usuarioEliminarView.getTxtNombre().setText(usuario.getNombre());
        usuarioEliminarView.getTxtTelefono().setText(usuario.getTelefono());
        usuarioEliminarView.getTxtCorreo().setText(usuario.getCorreo());

        GregorianCalendar fecha = usuario.getFecha();
        usuarioEliminarView.getSpnDia().setValue(fecha.get(Calendar.DAY_OF_MONTH));
        usuarioEliminarView.getSpnMes().setValue(fecha.get(Calendar.MONTH) + 1);
        usuarioEliminarView.getSpnAno().setValue(fecha.get(Calendar.YEAR));
    }

    private void buscarModificar() {
        String username = usuarioModificarView.getTxtUs().getText().trim();
        Usuario usuario1 = usuarioDAO.buscarPorUsername(username);

        if (usuario1 != null) {
            usuarioModificarView.getTxtUsuario().setText(usuario1.getUsuario());
            usuarioModificarView.getTxtContrasena().setText(usuario1.getContrasena());
            usuarioModificarView.getTxtNombre().setText(usuario1.getNombre());
            usuarioModificarView.getTxtTelefono().setText(usuario1.getTelefono());
            usuarioModificarView.getTxtCorreo().setText(usuario1.getCorreo());

            GregorianCalendar fecha = usuario1.getFecha();
            usuarioModificarView.getSpnDia().setValue(fecha.get(Calendar.DAY_OF_MONTH));
            usuarioModificarView.getSpnMes().setValue(fecha.get(Calendar.MONTH) + 1);
            usuarioModificarView.getSpnAno().setValue(fecha.get(Calendar.YEAR));

            usuarioModificarView.habilitarCampos(true);
        } else {
            usuarioModificarView.mostrarMensaje(mi.get("usuario.mensaje.no.encontrado"));
            usuarioModificarView.habilitarCampos(false);
        }
    }

    private void editar() {
        boolean confirmado = usuarioModificarView.mostrarMensajePregunta(mi.get("usuario.mensaje.editar.pregunta"));
        if (!confirmado) return;

        String usernameOriginal = usuarioModificarView.getTxtUs().getText().trim();
        Usuario usuario1 = usuarioDAO.buscarPorUsername(usernameOriginal);

        if (usuario1 == null) {
            usuarioModificarView.mostrarMensaje(mi.get("usuario.mensaje.no.encontrado"));
            return;
        }

        String usuarionuevo = usuarioModificarView.getTxtUsuario().getText().trim();
        String contrasena = usuarioModificarView.getTxtContrasena().getText().trim();
        String nombre = usuarioModificarView.getTxtNombre().getText().trim();
        String telefono = usuarioModificarView.getTxtTelefono().getText().trim();
        String correo = usuarioModificarView.getTxtCorreo().getText().trim();
        int dia = (int) usuarioModificarView.getSpnDia().getValue();
        int mes = (int) usuarioModificarView.getSpnMes().getValue();
        int anio = (int) usuarioModificarView.getSpnAno().getValue();

        if (usuarionuevo.isEmpty() || contrasena.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || correo.isEmpty()) {
            usuarioModificarView.mostrarMensaje(mi.get("cuestionario.finalizar.error_datos_vacios"));
            return;
        }

        if (!telefono.matches("\\d{7,15}")) {
            usuarioModificarView.mostrarMensaje("Número de celular inválido. Solo dígitos y mínimo 7 caracteres.");
            return;
        }

        if (!correo.matches("^[\\w\\.-]+@[\\w\\.-]+\\.[a-zA-Z]{2,}$")) {
            usuarioModificarView.mostrarMensaje("Correo electrónico inválido.");
            return;
        }

        if (dia < 1 || dia > 31 || mes < 1 || mes > 12 || anio < 1) {
            usuarioModificarView.mostrarMensaje("Fecha de nacimiento inválida.");
            return;
        }
        if (!usuarionuevo.equals(usernameOriginal) && usuarioDAO.buscarPorUsername(usuarionuevo) != null) {
            usuarioModificarView.mostrarMensaje(mi.get("usuario.mensaje.crear.existe"));
            return;
        }

        usuario1.setUsuario(usuarionuevo);
        usuario1.setContrasena(contrasena);
        usuario1.setNombre(nombre);
        usuario1.setTelefono(telefono);
        usuario1.setTelefono(correo);
        usuario1.setFecha(new GregorianCalendar(anio, mes - 1, dia));

        usuarioDAO.actualizar(usuario1);
        usuarioModificarView.mostrarMensaje(mi.get("usuario.mensaje.modificado"));
        usuarioModificarView.limpiarCampos();
        usuarioModificarView.habilitarCampos(false);
    }


    private void buscarUsario(){
        String username = usuarioListarView.getTxtNombre().getText();
        Usuario usuarioEncontrado = usuarioDAO.buscarPorUsername(username);
        if (usuarioEncontrado != null) {
            usuarioListarView.cargarDatos(List.of(usuarioEncontrado));
        } else {
            usuarioListarView.cargarDatos(new ArrayList<>());
        }
    }
    private void listar(){
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        usuarioListarView.cargarDatos(usuarios);
    }

    public void actualizarIdiomaEnVistas() {
        usuarioCrearView.cambiarIdioma();
        usuarioEliminarView.cambiarIdioma();
        usuarioModificarView.cambiarIdioma();
        usuarioListarView.cambiarIdioma();
    }
}
