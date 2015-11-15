import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SSMailroom {
    private ServerSocket server;
    private Socket client;
    private Scanner in;
    private PrintWriter out;
    private SSEngine engine;
    private ConcurrentLinkedQueue<Packet> inbox;
    private ConcurrentLinkedQueue<Packet> outbox;

    public SSMailroom(SSEngine engine) {
        this.engine = engine;
        inbox = engine.getOutbox();
        outbox = engine.getInbox();
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
        System.out.println("We have connected");
    }

    public void receivePacket() {
       System.out.println("Packet receive started");
       if (in.hasNext()) {
          System.out.println("Found beginning");
          if (!in.next().equals(Packet.START)) {
             System.out.println("No packet start");
             return;
          }
       }
       System.out.println("Got past start");
        int actionID = 0;
        int actorID = -1; 
        LinkedList<Double> extras;
        if (in.hasNextInt()) {
            actionID = in.nextInt();
            System.out.println("Action ID " + actionID);
        }
        if (in.hasNextInt()) {
            actorID = in.nextInt();
        }
        
        System.out.println("Entering switch");
        switch(actionID) {
            case Packet.UPDATE: {
                extras = new LinkedList<Double>();
                for (int i = 0; i < 6; i++) {
                    extras.add(in.nextDouble());
                }
                break;
            }
            case Packet.CREATE: {
               System.out.println("Create packet");
                extras = new LinkedList<Double>();
                for (int i = 0; i < 7; i++) {
                    extras.add(in.nextDouble());
                }
                break;
            }
            case Packet.KILL: {
                extras = null;
                break;
            }
            case Packet.PORT: {
                extras = new LinkedList<Double>();
                for (int i = 0; i < 2; i++) {
                    extras.add(in.nextDouble());
                }
                break;
            }
            default: {
                extras = null;
                break;
            }
        }
        System.out.println("Switch exited");
        if (!in.next().equals(Packet.STOP))
            return;
        Packet send = new Packet(actionID, actorID);
        send.setExtras(extras);
        outbox.add(send);
        System.out.println("Receive finish");
    }

    public void sendPacket() {
       System.out.println("Send started");
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
            out.println(actorID);
            for (double val : packet.getExtras()) {
                out.println(val);
            }
            out.println(Packet.STOP);
            out.flush();
            System.out.println("Send finished");
        }
    }
   
}
