package ec.edu.ups.dao;

public class FabricaDAO {

    public static UsuarioDAO getUsuarioDAO(String tipo) {
        if (tipo.equalsIgnoreCase("TEXTO")) {
            return new UsuarioDAOTexto();
        } else if (tipo.equalsIgnoreCase("BINARIO")) {
            return new UsuarioDAOBinario();
        }

        return null;
    }
}