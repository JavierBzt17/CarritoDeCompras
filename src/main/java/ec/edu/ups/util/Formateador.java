package ec.edu.ups.util;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

/**
 * La clase **Formateador** proporciona métodos estáticos para formatear
 * cantidades de dinero y fechas según una configuración regional (Locale) específica.
 */
public class Formateador {

    /**
     * Formatea una cantidad numérica como una cadena de moneda según la configuración regional dada.
     * Por ejemplo, para 1234.56 y un Locale de EE. UU., podría devolver "$1,234.56".
     *
     * @param cantidad La cantidad numérica a formatear.
     * @param locale La configuración regional que determina el formato de la moneda (símbolo, separadores).
     * @return Una cadena que representa la cantidad formateada como moneda.
     */
    public static String formatearMoneda(double cantidad, Locale locale) {
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(locale);
        return formatoMoneda.format(cantidad);
    }

    /**
     * Formatea un objeto Date como una cadena de fecha según la configuración regional dada.
     * El formato de fecha utilizado es de tipo MEDIUM (por ejemplo, "Jan 12, 2023" para un Locale de EE. UU.).
     *
     * @param fecha El objeto Date a formatear.
     * @param locale La configuración regional que determina el formato de la fecha.
     * @return Una cadena que representa la fecha formateada.
     */
    public static String formatearFecha(Date fecha, Locale locale) {
        DateFormat formato = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        return formato.format(fecha);
    }

}