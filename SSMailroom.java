import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SSMailroom {
    private ServerSocket server;
    private Socket client;
    private BufferedReader in;
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
           in = new BufferedReader(new InputStreamReader(
                                      client.getInputStream()));
           out = new PrintWriter(client.getOutputStream(), 
                                 true);
        } catch (IOException e) {
            System.out.println("No I/O client");
            System.exit(-1);
        }
        System.out.println("We have connected");
    }

   private String read() throws IOException {
      String line = in.readLine();
//      System.out.println(line);
      return line;
   }

    public void receivePacket() throws IOException{
//       System.out.println("Packet receive started");
       if (in.ready()) {
//          System.out.println("Found beginning");
          if (!read().equals(Packet.START)) {
//             System.out.println("No packet start");
             return;
          }
       
//          System.out.println("***************************************Got past start");
          int actionID = 0;
          int actorID = -1; 
          LinkedList<Double> extras;
          //line = in.readLine();

          String line = read();
       

          actionID = Integer.parseInt(line);
//          System.out.println("                            Action ID " + actionID);
       
          line = read();
          actorID = Integer.parseInt(line);
         
//          System.out.println("Entering switch");
          switch(actionID) {
             case Packet.MOVE: {
                extras = new LinkedList<Double>();
                for (int i = 0; i < 2; i++) {
                   extras.add(Double.parseDouble(read()));
                }
                break;
             }
             case Packet.CREATE: {
//                System.out.println("Create packet");
                extras = new LinkedList<Double>();
                for (int i = 0; i < 7; i++) {
                   extras.add(Double.parseDouble(read()));
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
                   extras.add(Double.parseDouble(read()));
                }
                break;
             }
             default: {
                extras = null;
                break;
             }
          }
//          System.out.println("Switch exited");
       
          if (!read().equals(Packet.STOP)) {
//             System.out.println("Didn't see Stop");
             return;
          }
       
          Packet send = new Packet(actionID, actorID);
          send.setExtras(extras);
          outbox.add(send);
       }
//       System.out.println("                             Receive finish");
    }

    public void sendPacket() {
//       System.out.println("Send started");
        int actionID;
        int actorID;
        if (!inbox.isEmpty()) {
//           System.out.println("              Actually sending");
            Packet packet = inbox.poll();
            LinkedList<Double> extras = packet.getExtras();
            
            actionID = packet.getActionID();
            actorID = packet.getActorID();

            //validate packet extras
            switch (actionID) {
                case Packet.UPDATE: {
                    if (extras == null) {
                        throw new NullPointerException();
                    }
                    if (extras.size() != 6 || actorID < 0)
                        return;
                    break;
                }
                case Packet.CREATE: {
                    if (extras == null) {
                        throw new NullPointerException();
                    }
                    if (extras.size() != 7)
                        return;
//                    System.out.println("Sending a create packet");
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
//                System.out.println(val);
            }
            out.println(Packet.STOP);
//            System.out.println("**************************Actual send finished");
        }
//        System.out.println("Send finish");
    }
   
}
