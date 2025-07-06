package ec.edu.ups.vista;

import javax.swing.*;
import java.awt.*;

public class JDesktopPane extends javax.swing.JDesktopPane {

    public JDesktopPane() {
        super();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        // Fondo blanco
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Parte del carrito
        g2d.setStroke(new BasicStroke(6));
        g2d.setColor(new Color(0, 102, 153)); // Azul fuerte

        // Manija del carrito
        g2d.drawLine(centerX - 100, centerY - 50, centerX - 70, centerY - 20);

        // Estructura superior
        g2d.setColor(Color.GRAY);
        g2d.drawRect(centerX - 70, centerY - 20, 100, 60);

        // Líneas verticales (rejilla)
        for (int i = 1; i < 4; i++) {
            g2d.drawLine(centerX - 70 + i * 25, centerY - 20, centerX - 70 + i * 25, centerY + 40);
        }

        // Líneas horizontales
        g2d.drawLine(centerX - 70, centerY, centerX + 30, centerY);
        g2d.drawLine(centerX - 70, centerY + 20, centerX + 30, centerY + 20);

        // Ruedas
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillOval(centerX - 60, centerY + 55, 20, 20);
        g2d.fillOval(centerX + 20, centerY + 55, 20, 20);

        // Líneas de movimiento
        g2d.setColor(new Color(0, 153, 204));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(centerX - 130, centerY - 10, centerX - 90, centerY - 10);
        g2d.drawLine(centerX - 130, centerY + 5, centerX - 100, centerY + 5);
        g2d.drawLine(centerX - 130, centerY + 20, centerX - 110, centerY + 20);

        // Texto debajo
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.setColor(Color.BLACK);
        String texto = "SUPERMERCADOS EL AHORRO";
        int textoWidth = g2d.getFontMetrics().stringWidth(texto);
        g2d.drawString(texto, centerX - textoWidth / 2, centerY + 100);
    }
}
