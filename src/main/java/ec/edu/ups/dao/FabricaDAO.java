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

    // El constructor es privado para forzar el uso del Singleton
    private FabricaDAO() {
        this.tipoPersistencia = Tipo.MEMORIA; // Por defecto, la persistencia es en memoria
        this.basePath = "";
    }

    /**
     * Obtiene la instancia única (Singleton) de la fábrica.
     * @return La instancia de FabricaDAO.
     */
    public static synchronized FabricaDAO getFabrica() {
        if (fabrica == null) {
            fabrica = new FabricaDAO();
        }
        return fabrica;
    }

    /**
     * Configura el tipo de persistencia que usará la fábrica.
     * @param tipo El tipo de persistencia (MEMORIA, TEXTO, BINARIO).
     */
    public void setPersistencia(Tipo tipo) {
        this.tipoPersistencia = tipo;
    }

    /**
     * Establece la ruta base para los archivos de persistencia (Texto o Binario).
     * @param path La ruta a la carpeta donde se guardarán los datos.
     */
    public void setBasePath(String path) {
        this.basePath = path;
    }

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
                // Se pasan las dependencias para que CarritoDAOTexto pueda interactuar con ellas
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