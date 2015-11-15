import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CSMailroom {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private CSEngine engine;
    private ConcurrentLinkedQueue<Packet> inbox;
    private ConcurrentLinkedQueue<Packet> outbox;
    
    public CSMailroom(CSEngine engine) {
        this.engine = engine;
        inbox = engine.getOutbox();
        outbox = engine.getInbox();
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
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch  (IOException e) {
            System.out.println("No I/O Client");
            System.exit(1);
        }
    }

    /*
    public void sendPacket() {
        out.println(Packet.MOVE);
        out.println(3);
        out.println(1.0);
        out.println(2.0);
        out.flush();
    }
    */

    // public void receivePacket() {
    //     if (in.hasNextInt()) {
    //         int actionID = in.nextInt();
    //         int actorID = in.nextInt();
    //         double extra1 = in.nextDouble();
    //         double extra2 = in.nextDouble();

    //         System.out.println(actionID);
    //         System.out.println("" + actorID);
    //         System.out.println("" + extra1);
    //         System.out.println("" + extra2);
    //     }
    // }

    public void sendPacket() {
        int actionID;
        int actorID;
        if (!inbox.isEmpty()) {
            //System.out.println("have to send");
            Packet packet = inbox.poll();
            LinkedList<Double> extras = packet.getExtras();

            actionID = packet.getActionID();
            actorID = packet.getActorID();

            //validate packet extras
            switch (actionID) {
                case Packet.MOVE: {
                    //System.out.println("move out");
                    if (packet.getExtras() == null) {
                        throw new NullPointerException("Packets cannot have a null Extras (for now)");
                    }
                    if (extras.size() != 2 || actorID < 0)
                        return;
                    break;
                }
                case Packet.CREATE: {
                    if (packet.getExtras() == null) {
                        throw new NullPointerException("Packets cannot have a null Extras (for now)");
                    }
                    if (extras.size() != 7)
                        return;
                    break;
                }
                case Packet.PORT: {
                    if (packet.getExtras() == null) {
                        throw new NullPointerException("Packets cannot have a null Extras (for now)");
                    }
                    if (extras.size() != 2 || actorID < 0)
                        return;
                }
                case Packet.KILL: {
                    if (extras != null || actorID < 0)
                        return;
                }
                default: {
                    break;
                }
            }

            out.println(Packet.START);
            out.println(actionID);
            out.println(actorID);
            for (double val : packet.getExtras()) {
                out.println(val);
            }
            out.println(Packet.STOP);
            // out.flush();
            //System.out.println("Sent");
        }
    }


    public void receivePacket() throws IOException {
        if (in.ready()) {
            //System.out.println("receiving");
            int actionID;
            int actorID;
            LinkedList<Double> extras = new LinkedList<Double>();
            String s = in.readLine();
            //System.out.println(s);
            if (s.equals(Packet.START)) {
                //System.out.println("got a start");
                actionID = Integer.parseInt(bfRead());
                //System.out.println(actionID);
                switch(actionID) {
                    case Packet.UPDATE: {
                        actorID = Integer.parseInt(bfRead());
                        for (int i = 0; i < 6; i++) {
                            Double toAdd = Double.parseDouble(bfRead());
                            extras.add(toAdd);
                        }
                        break;
                    }
                    case Packet.CREATE: {
                        actorID = Integer.parseInt(bfRead());
                        for (int i = 0; i < 7; i++) {
                            extras.add(Double.parseDouble(bfRead()));
                        }
                        break;
                    }
                    case Packet.KILL: {
                        actorID = Integer.parseInt(bfRead());
                        break;
                    }
                    default: actorID = -1;
                }
                String str = in.readLine();
                if (s.equals(Packet.STOP))
                    System.out.println("not a stop, it's a " + str);
                if (actorID != -1) {
                    //System.out.println("gave him stuff");
                    Packet send = new Packet(actionID, actorID);
                    send.setExtras(extras);
                    outbox.add(send);
                }
            }
        }
    }

    private String bfRead() throws IOException {
        String s = in.readLine();
        //System.out.println(s);
        return s;
    }
    
    // public void transmit(String line) {
    //     out.println(line);
    // }

    // public void receive() {
    //     String line = in.readLine();
    //     System.out.println("Text Recieved: " + line + " " + 2);
    // }

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