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

    public void sendPacket() {
        out.println(Packet.MOVE);
        out.println(3);
        out.println(1.0);
        out.println(2.0);
        out.flush();
    }

    public void receivePacket() {
        if (in.hasNextInt()) {
            int actionID = in.nextInt();
            System.out.println(actionID);
        
        //if (in.hasNextInt()) {
            int actorID = in.nextInt();
            System.out.println("" + actorID);
        //}
        //if (in.hasNextDouble()) {
            double extra1 = in.nextDouble();
            System.out.println("" + extra1);
        //}
        //if (in.hasNextDouble()) {
            double extra2 = in.nextDouble();
            System.out.println("" + extra2);
        }
    }

/*
    public void sendPacket() {
        if (!inbox.isEmpty()) {
            char actionID = packet.getActionID();
            out.println(Packet.START);
            out.println(actionID);

            switch (actionID) {
                case Packet.MOVE: {
                    out.println(packet.getActorID());
                    for (Object val : packet.getExtras()) {
                        out.print(val);
                    }
                    break;
                }
                case Packet.CREATE: {
                    for (Object val : packet.getExtras()) {
                        out.println(val);
                    }
                    break;
                }
                case Packet.PORT: {
                    out.println(packet.getActorID());
                    for (Object val : packet.getExtras()) {
                        out.println(val);
                    }
                    break;
                }
                default: {
                    break;
                }
            }
            out.println(Packet.STOP);
            out.flush();
        }
    }
*/

/*
    public void receivePacket() {
        char actionID;
        int actorID;
        LinkedList<Double> extras = new LinkedList<Double>();
        if (in.hasNext()) {
            if (in.next().equals(Packet.START)) {
                actionID = in.next().charAt(0);
                switch(actionID) {
                    case Packet.UPDATE: {
                        actorID = in.nextInt();
                        for (int i = 0; i < 6; i++) {
                            extras.add(in.nextDouble());
                        }
                        break;
                    }
                    case Packet.CREATE: {
                        actorID = in.nextInt();
                        for (int i = 0; i < 4; i++) {
                            extras.add(in.nextDouble());
                        }
                        break;
                    }
                    case Packet.KILL: {
                        actorID = in.nextInt();
                        break;
                    }
                }
            }
            Packet send = new Packet(actionID, actorID);
            send.setExtras(extras);
            outbox.add(send);
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