import java.net.*;
import java.io.*;
import java.util.*;

public class SSMailroom {
    private ServerSocket server;
    private Socket client;
    private Scanner in;
    private PrintWriter out;
    private ConcurrentLinkedQueue<Packet> actions;

    public SSMailroom() {
        actions = ConcurrentLinkedQueue<Packet>();
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
                if (in.next().equals(Packet.START)) {
                    receivePacket();
                    sendPacket();
                }
            }
        }
    }

    public void receivePacket() {
        int actionID;
        int actorID; 
        LinkedList<Double> extras;
        if (in.hasNextInt()) {
            actionID = in.nextInt();
        }
        if (in.hasNextInt()) {
            actorID = in.nextInt();
        }
        
        switch(actionID) {
            case Packet.UPDATE: {
                extras = new LinkedList<Double>();
                for (int i = 0; i < 6; i++) {
                    extras.add(in.nextDouble());
                }
                break;
            }
            case Packet.CREATE: {
                extras = new LinkedList<Double>();
                for (int i = 0; i < 7; i++) {
                    extras.add(in.nextDouble());
                }
                break;
            }
            case Packet.KILL: {
                break;
            }
            case Packet.PORT {
                extras = new LinkedList<Double>();
                for (int i = 0; i < 2; i++) {
                    extras.add(in.nextDouble());
                }
                break;
            }
            default{
                break;
            }
        }
        if (!in.next().equals(Packet.STOP))
            return;
        Packet send = new Packet(actionID, actorID);
        send.setExtras(extras);
        outbox.add(send);
    }

    public void sendPacket() {
        int actionID;
        int actorID;
        if (!inbox.isEmpty()) {
            Packet packet = inbox.poll();
            LinkedList<Double> extras = packet.getExtras();
            
            actionID = packet.getActionID();
            actorID = packet.getActorID();

            //validate packet extras
            switch (actionID) {
                case Packet.MOVE: {
                    if (extras == null) {
                        throw new NullPointerException();
                    }
                    if (extras.size() != 2 || actorID < 0)
                        return;
                    break;
                }
                case Packet.CREATE: {
                    if (extras == null) {
                        throw new NullPointerException();
                    }
                    if (extras.size() != 4)
                        return;
                    break;
                }
                case Packet.PORT: {
                    if (extras == null) {
                        throw new NullPointerException();
                    }
                    if (extras.size() != 2 || actorID < 0)
                        return;
                }
                case Packet.KILL: {
                    extras = null;
                }
                default: {
                    break;
                }
            }

            out.println(Packet.START);
            out.println(actionID);
            out.println(actorID)
            for (double val : packet.getExtras()) {
                out.println(val);
            }
            out.println(Packet.STOP);
            out.flush();
        }
    }
   
}
