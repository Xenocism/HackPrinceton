import java.net.*;
import java.io.*;
import java.util.*;

public class SSMailroom {
    private ServerSocket server;
    private Socket client;
    private Scanner in;
    private PrintWriter out;

    public SSMailroom() {

    }

    public void initSocket() {
        try{
            server = new ServerSocket(4321); 
        } catch (IOException e) {
            System.out.println("Could not listen on port 4321");
            System.exit(-1);
        }

        try{
            client = server.accept();
        } catch (IOException e) {
            System.out.println("Accept failed: 4321");
            System.exit(-1);
        }

        try{
            in = new Scanner(client.getInputStream());
            out = new PrintWriter(client.getOutputStream(), 
                true);
        } catch (IOException e) {
            System.out.println("No I/O client");
            System.exit(-1);
        }
    }

    public void listenSocket() {
        String line;
        while (true) {
            if (in.hasNext()) {
                line = in.next();
                System.out.println(line + " " + 1);
            //Send data back to client
                out.println(line);
            }
        }
    }

    public static void main(String args[]) {
        SSMailroom testServer = new SSMailroom();
        testServer.initSocket();
        testServer.listenSocket();
    }
}
