package ec.edu.ups.modelo;

import java.util.Objects;

public class Respuesta {
    private int id;
    private String enunciado;
    private String respuesta;

    public Respuesta(int id, String enunciado) {
        this.id = id;
        this.enunciado = enunciado;
        this.respuesta = null;
    }


    public Respuesta(int id, String enunciado, String respuesta) {
        this.id = id;
        this.enunciado = enunciado;
        this.respuesta = respuesta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Respuesta)) return false;
        Respuesta that = (Respuesta) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Respuesta{" +
                "id=" + id +
                ", enunciado='" + enunciado + '\'' +
                ", respuesta='" + (respuesta != null ? respuesta : "N/A") + '\'' +
                '}';
    }
}