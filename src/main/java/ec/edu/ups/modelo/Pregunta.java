package ec.edu.ups.modelo;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

public enum Pregunta {
    COLOR_FAVORITO("pregunta.color_favorito"),
    NOMBRE_MASCOTA("pregunta.nombre_mascota"),
    COMIDA_FAVORITA("pregunta.comida_favorita"),
    CIUDAD_NACIMIENTO("pregunta.ciudad_nacimiento"),
    CANTANTE_FAVORITO("pregunta.cantante_favorito"),
    NOMBRE_PRIMER_AMIGO("pregunta.nombre_primer_amigo"),
    PELICULA_FAVORITA("pregunta.pelicula_favorita"),
    NOMBRE_MADRE("pregunta.nombre_madre"),
    NOMBRE_PADRE("pregunta.nombre_padre"),
    NOMBRE_CARINO("pregunta.nombre_carino"),
    CANTIDAD_HERMANOS("pregunta.cantidad_hermanos"),
    NOMBRE_COLEGIO("pregunta.nombre_colegio");

    private String enunciado;
    private MensajeInternacionalizacionHandler mi;

    Pregunta(String enunciado) {
        this.enunciado = enunciado;
    }

    public void setMensajeIdioma(MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
    }

    public String getEnunciado() {
        if (mi != null) {
            return mi.get(enunciado);
        } else {
            return enunciado;
        }
    }

}
