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
        out.print(Packet.MOVE);
        out.print(3);
        out.print(1.0);
        out.print(2.0);
        out.flush();
    }

    public void receivePacket() {
        char actionID = in.next().charAt(0);
        int actorID = in.nextInt();
        double extra1 = in.nextDouble();
        double extra2 = in.nextDouble();

        System.out.println(actionID + 2);
        System.out.println("" + actorID + 2);
        System.out.println("" + extra1 + 2);
        System.out.println("" + extra2 + 2);
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