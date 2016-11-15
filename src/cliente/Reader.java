package cliente;


/**
 *
 * @author Alejandro Campoy Nieves.
 * @author David Criado Ramón.
 */
public class Reader extends Thread {
    private ClienteTCP cliente;
    private GUI gui;
    Reader(ClienteTCP c, GUI g) {
        cliente = c;
        gui = g;
    }
    @Override
    public void run() {
        while (true) {
           try {
                Thread.sleep(1000);
                cliente.receiveMessage(gui);
            
           } catch (InterruptedException ex) {
                System.err.println(ex);
                System.err.println("Problemas temporales.");
            }
        }
    }
}
