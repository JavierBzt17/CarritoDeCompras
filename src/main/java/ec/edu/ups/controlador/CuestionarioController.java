package ec.edu.ups.controlador;

import ec.edu.ups.dao.CuestionarioDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Cuestionario;
import ec.edu.ups.modelo.Respuesta;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.preguntas.RecuperarContraseñaView;
import ec.edu.ups.vista.preguntas.CuestionarioView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
public class CuestionarioController {

    private final CuestionarioView cuestionarioView;
    private final RecuperarContraseñaView recuperarView;
    private final CuestionarioDAO cuestionarioDAO;
    private Cuestionario cuestionario;
    private List<Respuesta> preguntasAleatorias;
    private List<Respuesta> respuestasCorrectas;
    private final MensajeInternacionalizacionHandler mi;
    private UsuarioDAO usuarioDAO;
    private boolean usuarioYaRegistrado;
    private Usuario usuario;


    public CuestionarioController(CuestionarioView vista, CuestionarioDAO dao,
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

    public CuestionarioController( RecuperarContraseñaView  recuperarView, CuestionarioDAO dao,
                                  String username, String contrasenia, MensajeInternacionalizacionHandler mi){//Constructor para recuperar contraseña de usuario
        this.mi = mi;
        this.cuestionarioDAO = dao;
        this.cuestionarioView = null;
        this.cuestionario = cuestionarioDAO.buscarPorUsername(username);
        this.recuperarView = recuperarView;
        this.respuestasCorrectas = new ArrayList<>();

        if (cuestionario == null) {
            recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.noPreguntas"));
            recuperarView.dispose();
            return;
        }

        this.preguntasAleatorias = cuestionario.getRespuestas();

        for (int i = 0; i < preguntasAleatorias.size(); i++) {
            String etiqueta = mi.get("cuestionario.pregunta");
            recuperarView.getCbxPreguntas().addItem(etiqueta + " " + (i + 1));
        }

        if (!preguntasAleatorias.isEmpty()) {
            recuperarView.getLblPregunta().setText(preguntasAleatorias.get(0).getEnunciado());
        }

        configurarEventosRecuperar(contrasenia);

    }

    public CuestionarioController(CuestionarioView vista, CuestionarioDAO cuestionarioDAO,
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
        cuestionarioView.getCbxPreguntas().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                preguntasCuestionario();
            }
        });

        cuestionarioView.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardar();
            }
        });

        cuestionarioView.getBtnTerminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalizar();
            }
        });
        cuestionarioView.getBtnEmpezar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarCuestionario();
            }
        });
    }

    private void configurarEventosRecuperar(String contrasenia){
        recuperarView.getCbxPreguntas().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                preguntasRecuperar();
            }
        });

        recuperarView.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarRespuestasRecuperar();
            }
        });

        recuperarView.getBtnTerminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalizarRecuperar(contrasenia);
            }
        });
    }

    private void preguntasRecuperar(){
        int index = recuperarView.getCbxPreguntas().getSelectedIndex();
        if (index >= 0 && index < preguntasAleatorias.size()) {
            Respuesta r = preguntasAleatorias.get(index);
            recuperarView.getLblPregunta().setText(r.getEnunciado());

            if (respuestasCorrectas.contains(r)) {
                recuperarView.getTxtRespuesta().setText(r.getRespuesta());
            } else {
                recuperarView.getTxtRespuesta().setText("");
            }
        }
    }

    private void guardarRespuestasRecuperar() {
        int index = recuperarView.getCbxPreguntas().getSelectedIndex();
        if (index < 0 || index >= preguntasAleatorias.size()) {
            recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.preguntaInvalida"));
            return;
        }

        Respuesta r = preguntasAleatorias.get(index);
        String respuestaUsuario = recuperarView.getTxtRespuesta().getText().trim();

        if (respuestaUsuario.isEmpty()) {
            recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.respuestaVacia"));
            return;
        }

        if (respuestaUsuario.equalsIgnoreCase(r.getRespuesta())) {
            if (!respuestasCorrectas.contains(r)) {
                respuestasCorrectas.add(r);
                recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.correcta"));
                r.setRespuesta(respuestaUsuario);
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

    private void preguntasCuestionario(){
        int index = cuestionarioView.getCbxPreguntas().getSelectedIndex();
        if (index >= 0) {
            Respuesta r = preguntasAleatorias.get(index);
            cuestionarioView.getLblPreguntasR().setText(r.getEnunciado());

            Respuesta respondido = cuestionario.buscarRespuestaPorId(r.getId());
            if (respondido != null) {
                cuestionarioView.getTxtRespuesta().setText(respondido.getRespuesta());
            } else {
                cuestionarioView.getTxtRespuesta().setText("");
            }
        }
    }

    private void guardar(){
        int index = cuestionarioView.getCbxPreguntas().getSelectedIndex();
        if (index < 0) return;

        String texto = cuestionarioView.getTxtRespuesta().getText().trim();
        if (texto.isEmpty()) {
            cuestionarioView.mostrarMensaje(mi.get("cuestionario.guardar.vacia"));
            return;
        }

        Respuesta seleccionada = preguntasAleatorias.get(index);
        seleccionada.setRespuesta(texto);

        if (cuestionario.buscarRespuestaPorId(seleccionada.getId()) == null) {
            cuestionario.agregarRespuesta(seleccionada);
        }

        cuestionarioView.mostrarMensaje(mi.get("cuestionario.guardar.ok"));
    }

    private void finalizar() {
        if (preguntasAleatorias == null || preguntasAleatorias.isEmpty()) {
            cuestionarioView.mostrarMensaje("Error: No se han cargado preguntas.");
            return;
        }

        if (cuestionario == null) {
            // Esta parte ya no se usa más si el Cuestionario siempre se crea en el constructor
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
                cuestionarioView.mostrarMensaje("Los campos estan vacíos");
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

        // Guardar el cuestionario, ya sea usuario nuevo o ya registrado
        cuestionarioDAO.guardar(cuestionario);

        cuestionarioView.mostrarMensaje(mi.get("cuestionario.finalizar.ok"));
        cuestionarioView.dispose();
    }



    private void iniciarCuestionario(){
        String username = cuestionarioView.getTxtUsuario().getText().trim();
        if (usuarioDAO.buscarPorUsername(username) != null) {
            cuestionarioView.mostrarMensaje(mi.get("login.mensaje.error_usuario_existente"));
            return;
        }
        cuestionario = new Cuestionario(username);
        cuestionario.aplicarIdioma(mi);
        cargarComboPreguntas();
        cuestionarioView.habilitarPreguntas(true);
    }

    private void cargarComboPreguntas() {
        if (preguntasAleatorias != null && !preguntasAleatorias.isEmpty()) return;

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

        cuestionarioView.getCbxPreguntas().removeAllItems();
        for (Respuesta r : preguntasAleatorias) {
            cuestionarioView.getCbxPreguntas().addItem(r.getEnunciado());
        }
    }

    private void setearCamposVista(Usuario usuario){
        cuestionarioView.getTxtUsuario().setText(usuario.getUsuario());
        cuestionarioView.getTxtContrasena().setText(usuario.getContrasena());
        cuestionarioView.getTxtNombre().setText(usuario.getNombre());
        cuestionarioView.getTxtTelefono().setText(usuario.getTelefono());
        cuestionarioView.getTxtCorreo().setText(usuario.getCorreo());

        GregorianCalendar fecha = usuario.getFecha();
        cuestionarioView.getSpnDia().setValue(fecha.get(Calendar.DAY_OF_MONTH));
        cuestionarioView.getSpnMes().setValue(fecha.get(Calendar.MONTH) + 1); // Recuerda que enero = 0
        cuestionarioView.getSpnAno().setValue(fecha.get(Calendar.YEAR));

        cuestionarioView.getTxtUsuario().setEnabled(false);
        cuestionarioView.getTxtContrasena().setEnabled(false);
        cuestionarioView.getTxtNombre().setEnabled(false);
        cuestionarioView.getTxtTelefono().setEnabled(false);
        cuestionarioView.getSpnDia().setEnabled(false);
        cuestionarioView.getSpnMes().setEnabled(false);
        cuestionarioView.getSpnAno().setEnabled(false);
        cuestionarioView.getTxtCorreo().setEnabled(false);

        cuestionarioView.getBtnEmpezar().setEnabled(false);

        cargarComboPreguntas();
        cuestionarioView.habilitarPreguntas(true);
    }
}
