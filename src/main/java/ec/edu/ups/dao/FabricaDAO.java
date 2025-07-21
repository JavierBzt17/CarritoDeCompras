package ec.edu.ups.dao;

import ec.edu.ups.dao.impl.*;

/**
 * FabricaDAO actualizada como un Singleton.
 * Se configura una vez al inicio y provee los DAOs correctos al resto de la app.
 */
public class FabricaDAO {

    private static FabricaDAO fabrica;
    public enum Tipo { MEMORIA, TEXTO, BINARIO }
    private Tipo tipoPersistencia;
    private String basePath;

    private FabricaDAO() {
        this.tipoPersistencia = Tipo.MEMORIA;
        this.basePath = "";
    }

    public static synchronized FabricaDAO getFabrica() {
        if (fabrica == null) {
            fabrica = new FabricaDAO();
        }
        return fabrica;
    }

    public void setPersistencia(Tipo tipo) { this.tipoPersistencia = tipo; }
    public void setBasePath(String path) { this.basePath = path; }

    public UsuarioDAO getUsuarioDAO() {
        switch (tipoPersistencia) {
            case TEXTO: return new UsuarioDAOTexto(basePath);
            case BINARIO: return new UsuarioDAOBinario(basePath);
            default: return new UsuarioDAOMemoria();
        }
    }

    public ProductoDAO getProductoDAO() {
        switch (tipoPersistencia) {
            case TEXTO: return new ProductoDAOTexto(basePath);
            case BINARIO: return new ProductoDAOBinario(basePath);
            default: return new ProductoDAOMemoria();
        }
    }

    public CarritoDAO getCarritoDAO() {
        switch (tipoPersistencia) {
            case TEXTO:
                return new CarritoDAOTexto(basePath, getUsuarioDAO(), getProductoDAO());
            case BINARIO:
                return new CarritoDAOBinario(basePath);
            default: // MEMORIA
                return new CarritoDAOMemoria();
        }
    }

    public CuestionarioDAO getCuestionarioDAO() {
        switch (tipoPersistencia) {
            case TEXTO:
                return new CuestionarioDAOTexto(basePath, getUsuarioDAO());
            case BINARIO:
                return new CuestionarioDAOBinario(basePath);
            default:
                return new CuestionarioDAOMemoria();
        }
    }
}