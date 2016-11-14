package cliente;

import javax.swing.JFrame;
/**
 *
 * @author Alejandro Campoy Nieves.
 * @author David Criado Ramón.
 * 
 * La clase Cliente es la clase principal y se encarga de inicializar la interfaz
 * gráficar
 */
public class Cliente {
    private static final ClienteTCP cliente = new ClienteTCP();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GUINombre initialGUI = new GUINombre(cliente);
        initialGUI.setVisible(true);
        initialGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initialGUI.pack(); // Ajusta al tamaño
        initialGUI.setLocationRelativeTo(null); // Pone en el centro de la pantalla
        initialGUI.setVisible(true);
    }
    
}
