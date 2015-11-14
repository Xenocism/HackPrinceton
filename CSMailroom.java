import java.net.*;
import java.io.*;
import java.util.*;

public class CSMailroom extends Mailroom{
    private Socket socket;
    private PrintWriter out;
    private Scanner in;
    
    public CSMailroom() {

    }

    public void initSocket(String host) {
        //initialize socket
        try{
            socket = new Socket(host, 4321);
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: " + host);
            System.exit(1);
        } catch (IOException e) {
            System.out.println("No I/O Client");
            System.exit(1);
        }

        //Connect socket to server socket
        try{
            out = new PrintWriter(socket.getOutputStream(), 
                true);
            in = new Scanner(socket.getInputStream());
        } catch  (IOException e) {
            System.out.println("No I/O Client");
            System.exit(1);
        }
    }

    public void sendPacket(Packet packet) {
        out.print(Packet.START);
        out.print(packet.getActionID());
        out.flush();
    }

    public void receive() {
        String line = in.next();
        System.out.println("Text Recieved: " + line + " " + 2);

    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        CSMailroom testClient = new CSMailroom();
        testClient.initSocket("10.9.241.225");

        in.nextInt();
        testClient.transmit();
        testClient.receive();
    }
}