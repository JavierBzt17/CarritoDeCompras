package ec.edu.ups.controlador;

import ec.edu.ups.dao.CuestionarioDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Cuestionario;
import ec.edu.ups.modelo.Respuesta;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.preguntas.RecuperarContraseñaView;
import ec.edu.ups.vista.preguntas.PreguntasView;

import java.util.*;

public class CuestionarioController {

    private final PreguntasView cuestionarioView;
    private final RecuperarContraseñaView recuperarView;
    private final CuestionarioDAO cuestionarioDAO;
    private Cuestionario cuestionario;
    private List<Respuesta> preguntasAleatorias;
    private List<Respuesta> respuestasCorrectas;
    private final MensajeInternacionalizacionHandler mi;
    private UsuarioDAO usuarioDAO;
    private boolean usuarioYaRegistrado;
    private Usuario usuario;
    private int indicePreguntaActual = 0;
    private boolean cuestionarioIniciado = false;

    // Para recuperación
    private int indicePreguntaRecuperar = 0;

    public CuestionarioController(PreguntasView vista, CuestionarioDAO dao,
                                  UsuarioDAO usuarioDAO, MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        this.cuestionarioView = vista;
        this.cuestionarioDAO = dao;
        this.usuarioDAO = usuarioDAO;
        this.recuperarView = null;
        this.cuestionario = null;

        cargarComboPreguntas();
        configurarEventosCuestionario();
    }

    public CuestionarioController(RecuperarContraseñaView recuperarView, CuestionarioDAO dao,
                                  String username, String contrasenia, MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        this.cuestionarioDAO = dao;
        this.cuestionarioView = null;
        this.recuperarView = recuperarView;
        this.cuestionario = cuestionarioDAO.buscarPorUsername(username);
        this.respuestasCorrectas = new ArrayList<>();

        if (cuestionario == null || cuestionario.getRespuestas().size() < 3) {
            recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.noPreguntas"));
            recuperarView.dispose();
            return;
        }

        List<Respuesta> userSecurityQuestions = new ArrayList<>(cuestionario.getRespuestas());
        Collections.shuffle(userSecurityQuestions);

        this.preguntasAleatorias = new ArrayList<>();
        for (int i = 0; i < Math.min(3, userSecurityQuestions.size()); i++) {
            this.preguntasAleatorias.add(userSecurityQuestions.get(i));
        }

        if (!preguntasAleatorias.isEmpty()) {
            indicePreguntaRecuperar = 0;
            mostrarPreguntaRecuperarActual();
        }

        configurarEventosRecuperar(contrasenia);
    }

    public CuestionarioController(PreguntasView vista, CuestionarioDAO cuestionarioDAO,
                                  UsuarioDAO usuarioDAO, Usuario usuario,
                                  MensajeInternacionalizacionHandler mi, boolean usuarioYaRegistrado) {
        this.mi = mi;
        this.cuestionarioView = vista;
        this.cuestionarioDAO = cuestionarioDAO;
        this.usuarioDAO = usuarioDAO;
        this.recuperarView = null;
        this.usuarioYaRegistrado = usuarioYaRegistrado;
        this.usuario = usuario;

        if (usuarioYaRegistrado) {
            this.cuestionario = cuestionarioDAO.buscarPorUsername(usuario.getUsuario());
            if (this.cuestionario == null) {
                this.cuestionario = new Cuestionario(usuario.getUsuario());
            }
        } else {
            this.cuestionario = new Cuestionario(usuario.getUsuario());
        }

        this.cuestionario.aplicarIdioma(mi);
        setearCamposVista(usuario);
        configurarEventosCuestionario();
    }

    private void configurarEventosCuestionario() {
        cuestionarioView.getBtnGuardar().addActionListener(e -> guardar());

        cuestionarioView.getBtnTerminar().addActionListener(e -> finalizar());

        cuestionarioView.getBtnGenerar().addActionListener(e -> {
            if (!cuestionarioIniciado) {
                iniciarCuestionario();
                cuestionarioView.getBtnGenerar().setText("Siguiente");
                cuestionarioIniciado = true;
            } else {
                guardar();
                siguientePregunta();
            }
        });
    }

    private void configurarEventosRecuperar(String contrasena) {
        // No hay JComboBox, por eso no se usa listener para cbxPreguntas

        recuperarView.getBtnGuardar().addActionListener(e -> guardarRespuestasRecuperar());

        recuperarView.getBtnTerminar().addActionListener(e -> finalizarRecuperar(contrasena));

        // Usamos btnGenerar para pasar a la siguiente pregunta
        recuperarView.getBtnGenerar().addActionListener(e -> siguientePreguntaRecuperar());
    }

    private void mostrarPreguntaRecuperarActual() {
        if (indicePreguntaRecuperar >= 0 && indicePreguntaRecuperar < preguntasAleatorias.size()) {
            Respuesta r = preguntasAleatorias.get(indicePreguntaRecuperar);
            recuperarView.getLblPregunta().setText(r.getEnunciado());
            recuperarView.getTxtRespuesta().setText("");
        }
    }

    private void guardarRespuestasRecuperar() {
        if (indicePreguntaRecuperar < 0 || indicePreguntaRecuperar >= preguntasAleatorias.size()) {
            recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.preguntaInvalida"));
            return;
        }

        Respuesta preguntaActual = preguntasAleatorias.get(indicePreguntaRecuperar);
        String respuestaUsuario = recuperarView.getTxtRespuesta().getText().trim();

        if (respuestaUsuario.isEmpty()) {
            recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.respuestaVacia"));
            return;
        }

        if (respuestaUsuario.equalsIgnoreCase(preguntaActual.getRespuesta())) {
            Respuesta correcta = new Respuesta(preguntaActual.getId(), preguntaActual.getEnunciado(), respuestaUsuario);

            if (!respuestasCorrectas.contains(correcta)) {
                respuestasCorrectas.add(correcta);
                recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.correcta"));
            } else {
                recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.yaRespondida"));
            }
        } else {
            recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.incorrecta"));
        }
    }

    private void siguientePreguntaRecuperar() {
        if (indicePreguntaRecuperar < preguntasAleatorias.size() - 1) {
            indicePreguntaRecuperar++;
            mostrarPreguntaRecuperarActual();
        } else {
            recuperarView.mostrarMensaje("No hay más preguntas.");
        }
    }

    private void finalizarRecuperar(String contrasena) {
        if (respuestasCorrectas.size() >= 3) {
            recuperarView.mostrarMensaje(String.format(mi.get("cuestionario.recuperar.recuperada"), contrasena));
            recuperarView.dispose();
        } else {
            recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.minimo"));
        }
    }

    private void guardar() {
        String enunciado = cuestionarioView.getTxtPreguntas().getText().trim();
        String respuesta = cuestionarioView.getTxtRespuesta().getText().trim();

        if (enunciado.isEmpty() || respuesta.isEmpty()) {
            cuestionarioView.mostrarMensaje(mi.get("cuestionario.guardar.vacia"));
            return;
        }

        Respuesta seleccionada = null;
        for (Respuesta r : preguntasAleatorias) {
            if (r.getEnunciado().equals(enunciado)) {
                seleccionada = r;
                break;
            }
        }

        if (seleccionada == null) {
            cuestionarioView.mostrarMensaje("Error: la pregunta no es válida.");
            return;
        }

        Respuesta respuestaParaGuardar = new Respuesta(seleccionada.getId(), seleccionada.getEnunciado(), respuesta);
        cuestionario.agregarRespuesta(respuestaParaGuardar);
        cuestionarioView.mostrarMensaje(mi.get("cuestionario.guardar.ok"));
    }

    private void finalizar() {
        if (preguntasAleatorias == null || preguntasAleatorias.isEmpty()) {
            cuestionarioView.mostrarMensaje("Error: No se han cargado preguntas.");
            return;
        }

        if (cuestionario.getRespuestas().size() < 3) {
            cuestionarioView.mostrarMensaje(mi.get("cuestionario.finalizar.minimo"));
            return;
        }

        if (!usuarioYaRegistrado) {
            String username = cuestionarioView.getTxtUsuario().getText().trim();
            String contrasenia = cuestionarioView.getTxtContrasena().getText().trim();
            String nombre = cuestionarioView.getTxtNombre().getText().trim();
            String celular = cuestionarioView.getTxtTelefono().getText().trim();
            String correo = cuestionarioView.getTxtCorreo().getText().trim();
            int dia = (int) cuestionarioView.getSpnDia().getValue();
            int mes = (int) cuestionarioView.getSpnMes().getValue();
            int anio = (int) cuestionarioView.getSpnAno().getValue();

            if (username.isEmpty() || contrasenia.isEmpty() || nombre.isEmpty() || celular.isEmpty() || correo.isEmpty()) {
                cuestionarioView.mostrarMensaje("Los campos están vacíos");
                return;
            }

            if (!celular.matches("\\d{7,15}")) {
                cuestionarioView.mostrarMensaje("Número de celular inválido.");
                return;
            }

            if (!correo.matches("^[\\w\\.-]+@[\\w\\.-]+\\.[a-zA-Z]{2,}$")) {
                cuestionarioView.mostrarMensaje("Correo inválido.");
                return;
            }

            if (dia < 1 || dia > 31 || mes < 1 || mes > 12 || anio < 1) {
                cuestionarioView.mostrarMensaje("Fecha de nacimiento inválida.");
                return;
            }

            if (usuarioDAO.buscarPorUsername(username) != null) {
                cuestionarioView.mostrarMensaje(mi.get("login.mensaje.error_usuario_existente"));
                return;
            }

            GregorianCalendar fechaNacimiento = new GregorianCalendar(anio, mes - 1, dia);
            Usuario nuevoUsuario = new Usuario(username, contrasenia, Rol.USUARIO, nombre, celular, fechaNacimiento, correo);
            usuarioDAO.crear(nuevoUsuario);
        }

        cuestionarioDAO.guardar(cuestionario);
        cuestionarioView.mostrarMensaje(mi.get("cuestionario.finalizar.ok"));
        cuestionarioView.dispose();
    }

    private void siguientePregunta() {
        if (cuestionario == null || preguntasAleatorias == null) {
            cuestionarioView.mostrarMensaje("Primero debe generar el cuestionario.");
            return;
        }

        indicePreguntaActual++;
        if (indicePreguntaActual >= preguntasAleatorias.size()) {
            cuestionarioView.mostrarMensaje("Ya no hay más preguntas.");
            indicePreguntaActual = preguntasAleatorias.size() - 1;
        } else {
            mostrarPreguntaActual();
        }
    }

    private void iniciarCuestionario() {
        String username = cuestionarioView.getTxtUsuario().getText().trim();
        if (usuarioDAO.buscarPorUsername(username) != null) {
            cuestionarioView.mostrarMensaje(mi.get("login.mensaje.error_usuario_existente"));
            return;
        }

        cuestionario = new Cuestionario(username);
        cuestionario.aplicarIdioma(mi);

        cargarComboPreguntas();
        indicePreguntaActual = 0;
        mostrarPreguntaActual();

        cuestionarioView.habilitarPreguntas(true);
    }

    private void mostrarPreguntaActual() {
        if (preguntasAleatorias != null && !preguntasAleatorias.isEmpty() && indicePreguntaActual < preguntasAleatorias.size()) {
            Respuesta r = preguntasAleatorias.get(indicePreguntaActual);
            cuestionarioView.getTxtPreguntas().setText(r.getEnunciado());
            cuestionarioView.getLblPreguntasR().setText(r.getEnunciado());

            Respuesta respondido = cuestionario.buscarRespuestaPorId(r.getId());
            if (respondido != null) {
                cuestionarioView.getTxtRespuesta().setText(respondido.getRespuesta());
            } else {
                cuestionarioView.getTxtRespuesta().setText("");
            }
        } else {
            cuestionarioView.mostrarMensaje("No hay más preguntas disponibles.");
        }
    }

    private void cargarComboPreguntas() {
        preguntasAleatorias = new ArrayList<>();
        Cuestionario temporal = new Cuestionario("");
        temporal.aplicarIdioma(mi);
        List<Respuesta> todasLasPreguntas = temporal.preguntasPorDefecto();

        boolean[] usadas = new boolean[todasLasPreguntas.size()];
        int cantidadDeseada = 6;
        int cantidadActual = 0;
        Random random = new Random();

        while (cantidadActual < cantidadDeseada) {
            int indice = random.nextInt(todasLasPreguntas.size());
            if (!usadas[indice]) {
                preguntasAleatorias.add(todasLasPreguntas.get(indice));
                usadas[indice] = true;
                cantidadActual++;
            }
        }

        if (!preguntasAleatorias.isEmpty()) {
            cuestionarioView.getTxtPreguntas().setText(preguntasAleatorias.get(0).getEnunciado());
            cuestionarioView.getLblPreguntasR().setText(preguntasAleatorias.get(0).getEnunciado());
        }
    }

    private void setearCamposVista(Usuario usuario) {
        cuestionarioView.getTxtUsuario().setText(usuario.getUsuario());
        cuestionarioView.getTxtContrasena().setText(usuario.getContrasena());
        cuestionarioView.getTxtNombre().setText(usuario.getNombre());
        cuestionarioView.getTxtTelefono().setText(usuario.getTelefono());
        cuestionarioView.getTxtCorreo().setText(usuario.getCorreo());

        GregorianCalendar fecha = usuario.getFecha();
        cuestionarioView.getSpnDia().setValue(fecha.get(Calendar.DAY_OF_MONTH));
        cuestionarioView.getSpnMes().setValue(fecha.get(Calendar.MONTH) + 1);
        cuestionarioView.getSpnAno().setValue(fecha.get(Calendar.YEAR));

        cuestionarioView.getTxtUsuario().setEnabled(false);
        cuestionarioView.getTxtContrasena().setEnabled(false);
        cuestionarioView.getTxtNombre().setEnabled(false);
        cuestionarioView.getTxtTelefono().setEnabled(false);
        cuestionarioView.getSpnDia().setEnabled(false);
        cuestionarioView.getSpnMes().setEnabled(false);
        cuestionarioView.getSpnAno().setEnabled(false);
        cuestionarioView.getTxtCorreo().setEnabled(false);
        cuestionarioView.getBtnGenerar().setEnabled(false);

        cargarComboPreguntas();
        cuestionarioView.habilitarPreguntas(true);
    }
}
