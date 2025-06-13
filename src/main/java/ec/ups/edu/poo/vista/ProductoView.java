package ec.ups.edu.poo.vista;
import javax.swing.*;

public class ProductoView extends JFrame {

    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JPanel panelPrincipal;
    private JButton aceptarButton;
    private JButton rechazarButton;

    public ProductoView(){
        setContentPane(panelPrincipal);
        setTitle("Datos del Producto");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args){
        new ProductoView();
    }
}
