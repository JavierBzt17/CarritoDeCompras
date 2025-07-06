package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CuestionarioDAO;
import ec.edu.ups.modelo.Cuestionario;
import ec.edu.ups.modelo.Respuesta;


import java.util.List;
import java.util.ArrayList;

public class CuestionarioDAOMemoria implements CuestionarioDAO {

    private List<Cuestionario> cuestionarios;
    public CuestionarioDAOMemoria() {
        this.cuestionarios = new ArrayList<>();
        Cuestionario cuestionarioAdmin = new Cuestionario("admin");;
        List<Respuesta> preguntas = cuestionarioAdmin.preguntasPorDefecto();

        preguntas.get(0).setRespuesta("Negro");
        preguntas.get(1).setRespuesta("Ba");
        preguntas.get(2).setRespuesta("Pizza");

        cuestionarioAdmin.agregarRespuesta(preguntas.get(0));
        cuestionarioAdmin.agregarRespuesta(preguntas.get(1));
        cuestionarioAdmin.agregarRespuesta(preguntas.get(2));

        guardar(cuestionarioAdmin);
    }

    @Override
    public void guardar(Cuestionario cuestionario) {
        cuestionarios.add(cuestionario);
    }

    @Override
    public Cuestionario buscarPorUsername(String username) {
        for (Cuestionario cuestionario : cuestionarios) {
            if (cuestionario.getUsuario().equalsIgnoreCase(username)) {
                return cuestionario;
            }
        }
        return null;
    }
}
