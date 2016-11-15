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
 * La clase ClienteTCP se encarga de establecer y tratar las conexiones del cliente con el Servidor.
 */
public class ClienteTCP {
    private String host;
    private final int PORT = 2036;
    private PrintWriter outPrinter;
    private BufferedReader inReader;
    private Socket socket;
    private String userName;

    /** Nos permite acceder al nombre del host
     * 
     * @return Host al que nos hemos conectado.
     */
    public String getHost() {
        return host;
    }

    /** Nos permite acceder al nombre de Usuario que tenemos puesto
     * 
     * @return Nombre de usuario que tenemos puesto.
     */
    public String getUserName() {
        return userName;
    }
    
    
    
    
    /** Configura e inicia conexión TCP con el host indicado. Además inicializa buffers TCP
     *  de entrada y salida
     * @param h Host con el que conectar.
     * @return Si la conexión ha sido existosa.
     */
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
    
    /** Da acceso al socket del cliente.
     * 
     * @return Socket del cliente.
     */
    
    public Socket getSocket() {
        return socket;
    }
    
    /** Configura el nombre de usuario del cliente
     * 
     * @param name Nombre de usuario a utilizar.
     * @return Si el nombre de usuario ha podido ser utilizado.
     */
    public boolean setUserName(String name) {
        try {
            outPrinter.println("LOGIN " + name);
            String respuesta = inReader.readLine();
            if ("OK".equals(respuesta)) {
                userName = name;
                System.out.println("Usando nombre: " + name);
                return true;
            }
            
        } catch (IOException io) {
            System.err.println(io);
            System.err.println("Error de conexión al intentar poner usuario");
        }
       return false;
    }
    
    /** Envia un mensaje de chat público al servidor
     * 
     * @param message Mensaje a enviar
     */
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
    
    /** Intenta actualizar los mensajes recibidos del servidor.
     * 
     * @param g Interfaz gráfica a la que añadir el mensaje.
     */
    void receiveMessage(GUI g) {
        try {
            outPrinter.println("UPDATE");
            String message = inReader.readLine();
            if ("IDDLE".equals(message)) return;
            
            
            int pos = message.indexOf(' ');
            String toAdd = message.substring(pos + 1) + '\n';
            g.addMessage(toAdd);
        }
        catch (IOException io) {
            System.err.println(io);
            System.err.println("Error con socket del cliente");
        }
    }
    
    
    
}
