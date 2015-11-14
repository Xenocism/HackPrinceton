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
            if(in.hasNext()) {
                char actionID = in.next().charAt(0);
                int actorID = in.nextInt();
                double extra1 = in.nextDouble();
                double extra2 = in.nextDouble();

                System.out.println(actionID + 1);
                System.out.println("" + actorID + 1);
                System.out.println("" + extra1 + 1);
                System.out.println("" + extra2 + 1);

                out.print(actionID);
                out.print(actorID);
                out.print(extra1);
                out.print(extra2);
                out.flush();
            }
        }
    }
}
