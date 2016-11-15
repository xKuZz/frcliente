package cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
/**
 *
 * @author Alejandro Campoy Nieves.
 * @author David Criado Ramón.
 */
public class ClienteTCP {
    private String host;
    private final int PORT = 2036;
    private PrintWriter outPrinter;
    private BufferedReader inReader;
    private Socket socket;
    
    public boolean setHost(String h) {
        host = h;
        try {
            socket = new Socket(host, PORT);
            outPrinter = new PrintWriter(socket.getOutputStream(), true);
            inReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Conexión a " + host + ":" + Integer.toString(PORT) 
                    + " iniciada correctamente");
            return true;
        } catch (IOException io) {
            System.err.println(io);
            System.err.println("Error al conectar con el host");
            return false;
        }
    }
    
    public Socket getSocket() {
        return socket;
    }
    
    public boolean setUserName(String name) {
        try {
            outPrinter.println("LOGIN " + name);
            String respuesta = inReader.readLine();
            if ("OK".equals(respuesta)) {
                System.out.println("Usando nombre: " + name);
                return true;
            }
            
        } catch (IOException io) {
            System.err.println(io);
            System.err.println("Error de conexión al intentar poner usuario");
        }
       return false;
    }
    
    synchronized void  sendMessage(String message) {
        try {
            String toSend = "SEND " + message;
            outPrinter.println(toSend);
            
            String response = inReader.readLine();
            if (!"SENT".equals(response))
                System.err.println("Error al enviar mensaje");
        } catch (IOException ex) {
            System.err.println(ex);
            System.err.println("Excepcion al enviar mensaje");
        }
    }
    
    void receiveMessage(GUI g) {
        try {
            outPrinter.println("UPDATE");
            String message = inReader.readLine();
            if ("IDDLE".equals(message)) return;
            
            
            int pos = message.indexOf(' ');
            String toAdd = message.substring(pos + 1);
            g.addMessage(message);
        }
        catch (IOException io) {
            System.err.println(io);
            System.err.println("Error con socket del cliente");
        }
    }
    
    
    
}
