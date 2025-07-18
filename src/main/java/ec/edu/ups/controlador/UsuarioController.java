package ec.edu.ups.controlador;

import ec.edu.ups.dao.CuestionarioDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Cuestionario;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.preguntas.RecuperarContraseñaView;
import ec.edu.ups.vista.usuario.*;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Controlador que gestiona la lógica de negocio para las operaciones de Usuario.
 * Esta clase opera en dos modos distintos, definidos por su constructor:
 * 1. Modo Login: Gestiona la autenticación, el registro y la recuperación de contraseña.
 * 2. Modo CRUD: Gestiona la creación, eliminación, modificación y listado de usuarios
 * desde el menú principal de la aplicación.
 *
 */
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

    /**
     * Constructor para el modo "Login".
     * Inicializa el controlador para manejar la lógica de la ventana de inicio de sesión.
     *
     * @param usuarioDAO Objeto de acceso a datos para usuarios.
     * @param loginView La vista de inicio de sesión.
     * @param cuestionarioDAO Objeto de acceso a datos para cuestionarios.
     * @param mi Manejador de internacionalización.
     */
    public UsuarioController(UsuarioDAO usuarioDAO, LoginView loginView, CuestionarioDAO cuestionarioDAO,
                             MensajeInternacionalizacionHandler mi) {
        this.usuarioDAO = usuarioDAO;
        this.loginView = loginView;
        this.usuario = null;
        this.cuestionarioDAO = cuestionarioDAO;
        this.mi = mi;
        configurarEventosLogin();
    }

    /**
     * Constructor para el modo "CRUD de Usuarios".
     * Inicializa el controlador con todas las vistas necesarias para la gestión de usuarios.
     *
     * @param usuarioDAO Objeto de acceso a datos para usuarios.
     * @param usuarioCrearView Vista para crear usuarios.
     * @param usuarioEliminarView Vista para eliminar usuarios.
     * @param usuarioModificarView Vista para modificar usuarios.
     * @param usuarioListarView Vista para listar usuarios.
     * @param mi Manejador de internacionalización.
     */
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

    /**
     * Configura los ActionListeners para los botones de la vista de Login.
     */
    private void configurarEventosLogin(){
        loginView.getBtnIniciarSesion().addActionListener(e -> autenticar());
        loginView.getBtnRegistrarse().addActionListener(e -> registrar());
        loginView.getBtnOlvide().addActionListener(e -> recuperar());
        loginView.getBtnSalir().addActionListener(e -> salir());
        loginView.getCbxIdioma().addActionListener(e -> cambiarIdioma());
    }

    /**
     * Configura los ActionListeners para los botones de las vistas de gestión de usuarios (CRUD).
     */
    private void configurarEventosUsuario(){
        usuarioCrearView.getBtnAceptar().addActionListener(e -> crear());
        usuarioEliminarView.getBtnEliminar().addActionListener(e -> eliminar());
        usuarioEliminarView.getBtnBuscar().addActionListener(e -> buscarEliminar());
        usuarioModificarView.getBtnBuscar().addActionListener(e -> buscarModificar());
        usuarioModificarView.getBtnEditar().addActionListener(e -> editar());
        usuarioListarView.getBtnListar().addActionListener(e -> listar());
        usuarioListarView.getBtnBuscar().addActionListener(e -> buscarUsuarioPorCedula());
    }

    /**
     * Gestiona la apertura de una ventana secundaria (registro, recuperación),
     * ocultando la ventana de login y asegurando que esta última reaparezca al cerrar la secundaria.
     *
     * @param ventanaSecundaria El JFrame de la ventana a mostrar.
     */
    private void gestionarAperturaVentanaSecundaria(JFrame ventanaSecundaria) {
        ventanaSecundaria.setVisible(true);
        loginView.setVisible(false);
        ventanaSecundaria.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventanaSecundaria.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                loginView.setVisible(true);
            }
        });
    }

    /**
     * Cambia el idioma de la aplicación según la selección en el ComboBox de la vista de login.
     */
    private void cambiarIdioma() {
        String[] clavesIdiomas = {"es", "en", "fr"};
        String[] paisesIdiomas = {"EC", "US", "FR"};
        int index = loginView.getCbxIdioma().getSelectedIndex();
        if (index >= 0 && index < clavesIdiomas.length) {
            mi.setLenguaje(clavesIdiomas[index], paisesIdiomas[index]);
            loginView.actualizarTextos();
        }
    }

    /**
     * Cierra la aplicación de forma segura.
     */
    private void salir(){
        loginView.dispose();
        System.exit(0);
    }

    /**
     * Procesa la autenticación de un usuario. Si es exitosa y el cuestionario de seguridad
     * está completo, cierra la ventana de login. Si no, abre la ventana para completar el cuestionario.
     */
    private void autenticar() {
        String cedula = loginView.getTxtCedula().getText().trim();
        String contrasenia = new String(loginView.getTxtContrasena().getPassword());

        usuario = usuarioDAO.autenticar(cedula, contrasenia);

        if (usuario == null) {
            loginView.mostrarMensaje(mi.get("login.mensaje.error_autenticacion"));
        } else {
            Cuestionario cuestionario = cuestionarioDAO.buscarPorCedula(cedula);
            if (cuestionario == null || cuestionario.getRespuestas().size() < 3) {
                loginView.mostrarMensaje(mi.get("login.mensaje.incompleto"));
                RegistrarUsuarioView cuestionarioView = new RegistrarUsuarioView(mi);
                new CuestionarioController(
                        cuestionarioView, cuestionarioDAO, usuarioDAO, usuario, mi, true);
                gestionarAperturaVentanaSecundaria(cuestionarioView);
            } else {
                loginView.dispose();
            }
        }
    }

    /**
     * Devuelve el usuario que ha sido autenticado exitosamente.
     * @return El objeto Usuario autenticado, o null si la autenticación falló.
     */
    public Usuario getUsuarioAutenticado() {
        return usuario;
    }

    /**
     * Inicia el flujo para registrar un nuevo usuario, abriendo la ventana de registro.
     */
    private void registrar() {
        boolean confirmado = loginView.mostrarMensajePregunta(mi.get("login.mensaje.pregunta_registro"));
        if (confirmado) {
            RegistrarUsuarioView cuestionarioView = new RegistrarUsuarioView(mi);
            new CuestionarioController(cuestionarioView, cuestionarioDAO, usuarioDAO, mi);
            gestionarAperturaVentanaSecundaria(cuestionarioView);
        } else {
            loginView.mostrarMensaje(mi.get("login.mensaje.creacion_cancelada"));
        }
    }

    /**
     * Inicia el flujo para recuperar una contraseña, validando que el usuario exista
     * y tenga preguntas de seguridad registradas antes de abrir la ventana de recuperación.
     */
    private void recuperar() {
        boolean confirmado = loginView.mostrarMensajePregunta(mi.get("login.mensaje.pregunta_recuperar"));
        if (confirmado) {
            String cedula = loginView.getTxtCedula().getText().trim();
            Usuario usuarioParaRecuperar = usuarioDAO.buscarPorCedula(cedula);

            if (usuarioParaRecuperar == null) {
                loginView.mostrarMensaje(mi.get("login.mensaje.usuario_no_encontrado"));
                return;
            }
            if (usuarioParaRecuperar.getRol() == Rol.ADMINISTRADOR) {
                loginView.mostrarMensaje(mi.get("login.mensaje.recuperacion_no_disponible_admin"));
                return;
            }

            Cuestionario cuestionario = cuestionarioDAO.buscarPorCedula(cedula);
            if (cuestionario == null || cuestionario.getRespuestas().isEmpty()) {
                loginView.mostrarMensaje(mi.get("login.mensaje.sin_preguntas"));
                return;
            }

            RecuperarContraseñaView recuperarView = new RecuperarContraseñaView(mi);
            new CuestionarioController(
                    recuperarView, cuestionarioDAO, cedula, usuarioParaRecuperar.getContrasena(), mi
            );
            gestionarAperturaVentanaSecundaria(recuperarView);
        } else {
            loginView.mostrarMensaje(mi.get("login.mensaje.recuperacion_cancelada"));
        }
    }

    /**
     * Crea un nuevo usuario en la capa de persistencia a partir de los datos
     * ingresados en la vista de creación.
     */
    private void crear() {
        boolean confirmado = usuarioCrearView.mostrarMensajePregunta(mi.get("usuario.mensaje.crear.pregunta"));
        if (!confirmado) {
            usuarioCrearView.mostrarMensaje(mi.get("usuario.mensaje.crear.cancelado"));
            return;
        }

        String cedula = usuarioCrearView.getTxtCedula().getText().trim();
        String contrasena = new String(usuarioCrearView.getTxtContrasena().getPassword());
        String nombre = usuarioCrearView.getTxtNombre().getText().trim();
        String telefono = usuarioCrearView.getTxtTelefono().getText().trim();
        String correo = usuarioCrearView.getTxtCorreo().getText().trim();
        int dia = (int) usuarioCrearView.getSpnDia().getValue();
        int mes = (int) usuarioCrearView.getSpnMes().getValue();
        int ano = (int) usuarioCrearView.getSpnAno().getValue();

        if (cedula.isEmpty() || contrasena.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || correo.isEmpty()) {
            usuarioCrearView.mostrarMensaje(mi.get("cuestionario.finalizar.error_datos_vacios"));
            return;
        }
        if (usuarioDAO.buscarPorCedula(cedula) != null) {
            usuarioCrearView.mostrarMensaje(mi.get("usuario.mensaje.crear.existe"));
            return;
        }

        GregorianCalendar fechaNacimiento = new GregorianCalendar(ano, mes - 1, dia);
        Usuario nuevoUsuario = new Usuario(cedula, contrasena, Rol.USUARIO, nombre, telefono, fechaNacimiento, correo);
        usuarioDAO.crear(nuevoUsuario);

        usuarioCrearView.mostrarMensaje(mi.get("usuario.mensaje.creado"));
        usuarioCrearView.limpiarCampos();
    }

    /**
     * Elimina un usuario de la capa de persistencia, previa confirmación.
     */
    private void eliminar() {
        String cedula = usuarioEliminarView.getTxtCedula().getText().trim();
        if (cedula.isEmpty()) {
            usuarioEliminarView.mostrarMensaje("Ingrese una cédula.");
            return;
        }

        boolean confirmado = usuarioEliminarView.mostrarMensajePregunta(mi.get("usuario.mensaje.eliminar.pregunta"));
        if (!confirmado) {
            usuarioEliminarView.mostrarMensaje(mi.get("usuario.mensaje.eliminar.cancelado"));
            return;
        }

        if (usuarioDAO.buscarPorCedula(cedula) == null) {
            usuarioEliminarView.mostrarMensaje("El usuario que usted busca no existe.");
            return;
        }

        usuarioDAO.eliminar(cedula);
        usuarioEliminarView.mostrarMensaje(mi.get("usuario.mensaje.eliminado"));
        usuarioEliminarView.limpiarCampos();
    }

    /**
     * Busca un usuario por cédula y, si lo encuentra, puebla los campos
     * de la vista de eliminación con sus datos.
     */
    private void buscarEliminar() {
        String cedula = usuarioEliminarView.getTxtCedula().getText().trim();
        if (cedula.isEmpty()) {
            usuarioEliminarView.mostrarMensaje("Ingrese la cédula que desea buscar.");
            return;
        }

        Usuario usuarioEncontrado = usuarioDAO.buscarPorCedula(cedula);
        if (usuarioEncontrado == null) {
            usuarioEliminarView.mostrarMensaje("Usuario no encontrado.");
            usuarioEliminarView.limpiarCampos();
            return;
        }

        usuarioEliminarView.getTxtContrasena().setText(usuarioEncontrado.getContrasena());
        usuarioEliminarView.getTxtNombre().setText(usuarioEncontrado.getNombre());
        usuarioEliminarView.getTxtTelefono().setText(usuarioEncontrado.getTelefono());
        usuarioEliminarView.getTxtCorreo().setText(usuarioEncontrado.getCorreo());
        GregorianCalendar fecha = usuarioEncontrado.getFecha();
        usuarioEliminarView.getSpnDia().setValue(fecha.get(Calendar.DAY_OF_MONTH));
        usuarioEliminarView.getSpnMes().setValue(fecha.get(Calendar.MONTH) + 1);
        usuarioEliminarView.getSpnAno().setValue(fecha.get(Calendar.YEAR));
    }

    /**
     * Busca un usuario por cédula y, si lo encuentra, puebla los campos
     * de la vista de modificación con sus datos para ser editados.
     */
    private void buscarModificar() {
        String cedula = usuarioModificarView.getTxtCedulaBuscar().getText().trim();
        Usuario usuarioEncontrado = usuarioDAO.buscarPorCedula(cedula);

        if (usuarioEncontrado != null) {
            usuarioModificarView.getTxtCedulaBuscar().setText(usuarioEncontrado.getCedula());
            usuarioModificarView.getTxtContrasena().setText(usuarioEncontrado.getContrasena());
            usuarioModificarView.getTxtNombre().setText(usuarioEncontrado.getNombre());
            usuarioModificarView.getTxtTelefono().setText(usuarioEncontrado.getTelefono());
            usuarioModificarView.getTxtCorreo().setText(usuarioEncontrado.getCorreo());
            GregorianCalendar fecha = usuarioEncontrado.getFecha();
            usuarioModificarView.getSpnDia().setValue(fecha.get(Calendar.DAY_OF_MONTH));
            usuarioModificarView.getSpnMes().setValue(fecha.get(Calendar.MONTH) + 1);
            usuarioModificarView.getSpnAno().setValue(fecha.get(Calendar.YEAR));
            usuarioModificarView.habilitarCampos(true);
        } else {
            usuarioModificarView.mostrarMensaje(mi.get("usuario.mensaje.no.encontrado"));
            usuarioModificarView.habilitarCampos(false);
        }
    }

    /**
     * Guarda los cambios de un usuario modificado en la capa de persistencia,
     * previa confirmación.
     */
    private void editar() {
        boolean confirmado = usuarioModificarView.mostrarMensajePregunta(mi.get("usuario.mensaje.editar.pregunta"));
        if (!confirmado) return;

        String cedulaOriginal = usuarioModificarView.getTxtCedulaBuscar().getText().trim();
        Usuario usuarioAEditar = usuarioDAO.buscarPorCedula(cedulaOriginal);

        if (usuarioAEditar == null) {
            usuarioModificarView.mostrarMensaje(mi.get("usuario.mensaje.no.encontrado"));
            return;
        }
        String cedulaNueva = usuarioModificarView.getTxtCedulaBuscar().getText().trim();
        String contrasena = new String(usuarioModificarView.getTxtContrasena().getPassword());
        String nombre = usuarioModificarView.getTxtNombre().getText().trim();
        String telefono = usuarioModificarView.getTxtTelefono().getText().trim();
        String correo = usuarioModificarView.getTxtCorreo().getText().trim();
        int dia = (int) usuarioModificarView.getSpnDia().getValue();
        int mes = (int) usuarioModificarView.getSpnMes().getValue();
        int anio = (int) usuarioModificarView.getSpnAno().getValue();
        if (!cedulaNueva.equals(cedulaOriginal) && usuarioDAO.buscarPorCedula(cedulaNueva) != null) {
            usuarioModificarView.mostrarMensaje(mi.get("usuario.mensaje.crear.existe"));
            return;
        }
        usuarioAEditar.setCedula(cedulaNueva);
        usuarioAEditar.setContrasena(contrasena);
        usuarioAEditar.setNombre(nombre);
        usuarioAEditar.setTelefono(telefono);
        usuarioAEditar.setCorreo(correo);
        usuarioAEditar.setFecha(new GregorianCalendar(anio, mes - 1, dia));
        usuarioDAO.actualizar(usuarioAEditar);
        usuarioModificarView.mostrarMensaje(mi.get("usuario.mensaje.modificado"));
        usuarioModificarView.limpiarCampos();
        usuarioModificarView.habilitarCampos(false);
    }

    /**
     * Busca un único usuario por su cédula y lo muestra en la tabla de la vista de listado.
     */
    private void buscarUsuarioPorCedula(){
        String cedula = usuarioListarView.getTxtCedula().getText();
        if (cedula.isEmpty()) {
            listar();
            return;
        }
        Usuario usuarioEncontrado = usuarioDAO.buscarPorCedula(cedula);
        if (usuarioEncontrado != null) {
            usuarioListarView.cargarDatos(List.of(usuarioEncontrado));
        } else {
            usuarioListarView.cargarDatos(new ArrayList<>());
        }
    }

    /**
     * Obtiene todos los usuarios de la capa de persistencia y los muestra en la tabla.
     */
    private void listar(){
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        usuarioListarView.cargarDatos(usuarios);
    }

    /**
     * Actualiza el idioma de todas las vistas de gestión de usuarios.
     */
    public void actualizarIdiomaEnVistas() {
        if(usuarioCrearView != null) usuarioCrearView.cambiarIdioma();
        if(usuarioEliminarView != null) usuarioEliminarView.cambiarIdioma();
        if(usuarioModificarView != null) usuarioModificarView.cambiarIdioma();
        if(usuarioListarView != null) usuarioListarView.cambiarIdioma();
    }

    /**
     * Establece el usuario autenticado. Usado principalmente para limpiar
     * la sesión al cerrar sesión, estableciendo el usuario interno a null.
     * @param o Objeto a establecer. Se espera que sea null para limpiar la sesión.
     */
    public void setUsuarioAutenticado(Object o) {
        this.usuario = null;
    }
}