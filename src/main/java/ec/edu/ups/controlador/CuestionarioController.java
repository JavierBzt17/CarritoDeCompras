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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class CuestionarioController {

    private final PreguntasView cuestionarioView;
    private final RecuperarContraseñaView recuperarView;
    private final CuestionarioDAO cuestionarioDAO;
    private Cuestionario cuestionario; // This is the user's stored Cuestionario with their answers
    private List<Respuesta> preguntasAleatorias; // These are the *default* questions for selection or display
    private List<Respuesta> respuestasCorrectas; // This list holds the user's *correctly provided answers for recovery attempt*
    private final MensajeInternacionalizacionHandler mi;
    private UsuarioDAO usuarioDAO;
    private boolean usuarioYaRegistrado;
    private Usuario usuario;
    private int indicePreguntaActual = 0;

    public CuestionarioController(PreguntasView vista, CuestionarioDAO dao,
                                  UsuarioDAO usuarioDAO, MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        this.cuestionarioView = vista;
        this.cuestionarioDAO = dao;
        this.usuarioDAO = usuarioDAO;
        this.recuperarView = null;
        this.cuestionario = null; // Cuestionario for a new user will be created later

        cargarComboPreguntas(); // Load default questions for selection
        configurarEventosCuestionario();
    }

    public CuestionarioController(RecuperarContraseñaView recuperarView, CuestionarioDAO dao,
                                  String username, String contrasenia, MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        this.cuestionarioDAO = dao;
        this.cuestionarioView = null;
        this.cuestionario = cuestionarioDAO.buscarPorUsername(username); // Load the user's actual questionnaire
        this.recuperarView = recuperarView;
        this.respuestasCorrectas = new ArrayList<>(); // Initialize for tracking correctly answered questions in this session

        if (cuestionario == null || cuestionario.getRespuestas().size() < 3) { // Ensure user has at least 3 questions set
            recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.noPreguntas")); // Message if not enough answers
            recuperarView.dispose();
            return;
        }

        // --- IMPORTANT CHANGE FOR RECOVERY FLOW ---
        // For recovery, 'preguntasAleatorias' should be the user's *actual* stored security questions,
        // from which we'll pick 3 random ones to ask.
        List<Respuesta> userSecurityQuestions = new ArrayList<>(cuestionario.getRespuestas());
        Collections.shuffle(userSecurityQuestions); // Randomize the order

        // Select up to 3 questions from the user's stored security questions
        this.preguntasAleatorias = new ArrayList<>();
        for (int i = 0; i < Math.min(3, userSecurityQuestions.size()); i++) {
            this.preguntasAleatorias.add(userSecurityQuestions.get(i));
        }
        // --- END IMPORTANT CHANGE ---

        // Populate the ComboBox with these selected random questions
        for (int i = 0; i < preguntasAleatorias.size(); i++) {
            String etiqueta = mi.get("cuestionario.pregunta");
            recuperarView.getCbxPreguntas().addItem(etiqueta + " " + (i + 1));
        }

        if (!preguntasAleatorias.isEmpty()) {
            recuperarView.getLblPregunta().setText(preguntasAleatorias.get(0).getEnunciado());
            // Optionally, clear the answer field if it's the first question
            recuperarView.getTxtRespuesta().setText("");
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

        cuestionarioView.getBtnGenerar().addActionListener(e -> iniciarCuestionario());
    }

    private void configurarEventosRecuperar(String contrasena) {
        recuperarView.getCbxPreguntas().addActionListener(e -> preguntasRecuperar());

        recuperarView.getBtnGuardar().addActionListener(e -> guardarRespuestasRecuperar());

        recuperarView.getBtnTerminar().addActionListener(e -> finalizarRecuperar(contrasena));

        // This listener should only be added if cuestionarioView is active (not null)
        // and if it's actually tied to a button in that view.
        // It seems to be a leftover from the main questionnaire logic.
        // If your RecuperarContraseñaView has a "next question" button,
        // you'd add a listener to *its* button for a similar purpose.
        /*
        if (cuestionarioView != null) {
            cuestionarioView.getBtnGenerar().addActionListener(e -> siguientePregunta());
        }
        */
    }

    private void preguntasRecuperar() {
        int index = recuperarView.getCbxPreguntas().getSelectedIndex();
        if (index >= 0 && index < preguntasAleatorias.size()) {
            Respuesta r = preguntasAleatorias.get(index); // This 'r' is one of the randomly selected user security questions

            recuperarView.getLblPregunta().setText(r.getEnunciado());

            // Check if this specific question has already been answered correctly in this recovery session
            boolean alreadyAnsweredCorrectly = false;
            for (Respuesta correctR : respuestasCorrectas) {
                if (correctR.equals(r)) { // Uses the overridden equals() based on ID
                    recuperarView.getTxtRespuesta().setText(correctR.getRespuesta()); // Show the user's correctly provided answer
                    alreadyAnsweredCorrectly = true;
                    break;
                }
            }
            if (!alreadyAnsweredCorrectly) {
                recuperarView.getTxtRespuesta().setText(""); // Clear the text field if not answered correctly yet
            }
        }
    }

    private void guardarRespuestasRecuperar() {
        int index = recuperarView.getCbxPreguntas().getSelectedIndex();
        if (index < 0 || index >= preguntasAleatorias.size()) {
            recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.preguntaInvalida"));
            return;
        }

        // 'userQuestion' is one of the questions randomly picked from the user's *stored* security questions.
        // Its `getRespuesta()` will return the user's *actual stored answer* that we need to verify against.
        Respuesta userQuestion = preguntasAleatorias.get(index);
        String respuestaUsuario = recuperarView.getTxtRespuesta().getText().trim();

        if (respuestaUsuario.isEmpty()) {
            recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.respuestaVacia"));
            return;
        }

        // Compare the user's input with their actual stored answer for this question
        if (respuestaUsuario.equalsIgnoreCase(userQuestion.getRespuesta())) {
            // Create a new Respuesta object to track this correctly answered question
            // The important part is that it has the correct ID and the user's correct response
            Respuesta answeredCorrectly = new Respuesta(userQuestion.getId(), userQuestion.getEnunciado(), respuestaUsuario);

            // Check if this question (by ID) has already been added to our `respuestasCorrectas` list
            if (!respuestasCorrectas.contains(answeredCorrectly)) { // Uses equals() on ID
                respuestasCorrectas.add(answeredCorrectly); // Add the correctly answered question
                recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.correcta"));
                // *** DO NOT MODIFY userQuestion.setRespuesta(respuestaUsuario); HERE! ***
                // This was the source of the problem. We are in recovery, not setting answers.
            } else {
                recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.yaRespondida"));
            }
        } else {
            recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.incorrecta"));
        }
    }

    private void finalizarRecuperar(String contrasenia) {
        if (respuestasCorrectas.size() >= 3) {
            recuperarView.mostrarMensaje(String.format(mi.get("cuestionario.recuperar.recuperada"), contrasenia));
            recuperarView.dispose();
        } else {
            recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.minimo"));
        }
    }

    // --- Main Questionnaire Logic (No changes needed for this specific problem) ---

    private void guardar() {
        String enunciado = cuestionarioView.getTxtPreguntas().getText().trim();
        String respuesta = cuestionarioView.getTxtRespuesta().getText().trim();

        if (enunciado.isEmpty() || respuesta.isEmpty()) {
            cuestionarioView.mostrarMensaje(mi.get("cuestionario.guardar.vacia"));
            return;
        }

        Respuesta seleccionada = null;
        for (Respuesta r : preguntasAleatorias) {
            if (r.getEnunciado().equals(enunciado)) { // Assuming enunciados are unique enough for this check
                seleccionada = r;
                break;
            }
        }

        if (seleccionada == null) {
            cuestionarioView.mostrarMensaje("Error: la pregunta no es válida.");
            return;
        }

        // Create a new Respuesta object with the user's input for saving
        Respuesta respuestaParaGuardar = new Respuesta(seleccionada.getId(), seleccionada.getEnunciado(), respuesta);

        // Add or update the response in the main Cuestionario object
        // The Cuestionario class's agregarRespuesta method handles if it's an add or update based on ID
        cuestionario.agregarRespuesta(respuestaParaGuardar);
        System.out.println("Respuesta agregada/actualizada en cuestionario: " + respuestaParaGuardar.getEnunciado());


        cuestionarioView.mostrarMensaje(mi.get("cuestionario.guardar.ok"));
    }

    private void finalizar() {
        if (preguntasAleatorias == null || preguntasAleatorias.isEmpty()) {
            cuestionarioView.mostrarMensaje("Error: No se han cargado preguntas.");
            return;
        }

        System.out.println("Cantidad de respuestas guardadas: " + cuestionario.getRespuestas().size());
        for (Respuesta r : cuestionario.getRespuestas()) {
            System.out.println(" - Pregunta: " + r.getEnunciado() + " | Respuesta: " + r.getRespuesta());
        }

        if (cuestionario.getRespuestas().size() < 3) { // This check now uses the corrected Cuestionario's responses
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

        cargarComboPreguntas(); // This loads *default* questions
        indicePreguntaActual = 0;
        mostrarPreguntaActual();

        cuestionarioView.habilitarPreguntas(true);
    }

    private void mostrarPreguntaActual() {
        if (preguntasAleatorias != null && !preguntasAleatorias.isEmpty() && indicePreguntaActual < preguntasAleatorias.size()) {
            Respuesta r = preguntasAleatorias.get(indicePreguntaActual);
            cuestionarioView.getTxtPreguntas().setText(r.getEnunciado());
            cuestionarioView.getLblPreguntasR().setText(r.getEnunciado());

            // Check if the user has already answered this question in their Cuestionario
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

        Cuestionario temporal = new Cuestionario(""); // Used to get default questions
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

        cuestionarioView.getBtnGenerar().setEnabled(false); // Can't generate new questionnaire if already registered

        cargarComboPreguntas(); // Load default questions for display/interaction
        cuestionarioView.habilitarPreguntas(true);
    }
}