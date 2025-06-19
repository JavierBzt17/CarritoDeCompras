package ec.edu.ups.poo.vista;

import javax.swing.*;

public class CarritoDeComprasView extends JFrame {
    private JPanel panelPrincipal;
    private JTextField textField1;
    private JTextField textField3;
    private JLabel lblProducto;
    private JButton agregarProductoButton;
    private JButton listaDeProductosButton;
    private JButton eliminarProductoButton;
    private JLabel lblCantidad;
    private JCheckBox siCheckBox;
    private JCheckBox noCheckBox;
    private JLabel lblPregunta;

    public CarritoDeComprasView(){
        setContentPane(panelPrincipal);
      setTitle("Carrito de Compras:");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setSize(600, 500);
      setLocationRelativeTo(null);
      setVisible(true);
    }


    public static void main(String[] args){
        new CarritoDeComprasView();
    }
}
