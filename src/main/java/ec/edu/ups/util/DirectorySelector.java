package ec.edu.ups.util;

import javax.swing.JFileChooser;
import java.io.File;

/**
 * Clase de utilidad para mostrar un diálogo y seleccionar un directorio.
 */
public class DirectorySelector {

    /**
     * Abre una ventana para que el usuario seleccione una carpeta.
     * @return La ruta absoluta de la carpeta seleccionada, o null si el usuario cancela.
     */
    public static String selectDirectory() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Seleccione la carpeta para guardar los datos");

        // Configura el chooser para que solo se puedan seleccionar carpetas
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        // Deshabilita la opción "Todos los archivos" para evitar confusión
        chooser.setAcceptAllFileFilterUsed(false);

        // Muestra el diálogo y espera la acción del usuario
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            // Si el usuario presiona "Aceptar", devuelve la ruta de la carpeta
            return chooser.getSelectedFile().getAbsolutePath();
        } else {
            // Si el usuario cancela, devuelve null
            System.out.println("Selección de directorio cancelada.");
            return null;
        }
    }
}