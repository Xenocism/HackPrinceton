import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CSMailroom {
    private Socket socket;
    private PrintWriter out;
    private Scanner in;
    //private CSEngine engine;
    //private ConcurrentLinkedQueue<Packet> inbox;
    //private ConcurrentLinkedQueue<Packet> outbox;
    
    public CSMailroom() {
        //this.engine = engine;
        //inbox = engine.getOutbox();
        //outbox = engine.getInbox();
    }

    public void initSocket(String host) {
        //initialize socket
        try{
            socket = new Socket(host, 4321);
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: " + host);
            System.exit(1);
        } catch (IOException e) {
            System.out.println("No I/O Client socket");
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
/*
    public void sendPacket() {
        if (!inbox.isEmpty()) {
            char actionID = packet.getActionID();
            out.print(Packet.START);
            out.print(actionID);

            switch (actionID) {
                case Packet.MOVE: {
                    out.print(packet.getActorID());
                    for (Object val : packet.getExtras()) {
                        out.print(val);
                    }
                    break;
                }
                case Packet.CREATE: {
                    for (Object val : packet.getExtras()) {
                        out.print(val);
                    }
                    break;
                }
                case Packet.PORT: {
                    out.print(packet.getActorID());
                    for (Object val : packet.getExtras()) {
                        out.print(val);
                    }
                    break;
                }
                default: {
                    break;
                }
            }
            out.print(Packet.STOP);
            out.flush();
        }
    }
*/
    public void transmit(String line) {
        out.println(line);
    }

    public void receive() {
        String line = in.next();
        System.out.println("Text Recieved: " + line + " " + 2);
    }

/*
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        CSMailroom testClient = new CSMailroom();
        testClient.initSocket("45.79.129.43");

        while (true) {
            if (in.hasNext()) {
                String line = in.next();
                testClient.transmit(line);
                testClient.receive();
            }
        }
    }
    */
}