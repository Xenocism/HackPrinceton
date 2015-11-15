import java.util.TreeMap;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Iterator;

public class SSEngine {

    private int idcount;
    private static final int UP     = 0;
    private static final int LEFT   = 1;
    private static final int DOWN   = 2;
    private static final int RIGHT  = 3;

    private static final String[] images = {"images\\fill.png"};

    private static final int[] xBounds  = new int[]{0, 1000};
    private static final int[] yBounds  = new int[]{0, 500};

    private static final int impulse = 5;
    private static final double FRICTION = 0.995;

    private RedBlackBST<Integer, Actor> actorTree;

    private ConcurrentLinkedQueue<Packet> inbox;
    private ConcurrentLinkedQueue<Packet> outbox;

    private ConcurrentLinkedQueue test;

    // Constructor, init and setters

    public SSEngine() {
        this.actorTree  = new RedBlackBST<Integer, Actor>();
        this.inbox      = new ConcurrentLinkedQueue<Packet>();
        this.outbox     = new ConcurrentLinkedQueue<Packet>();
    }

    // called after mailroom and gamescreen are set, returns true
    // if all checks out, false if not
    public boolean init() {
        if (actorTree == null)  return false;
        if (inbox == null)      return false;
        if (outbox == null)     return false;
        return true;
    }

    public void setCSEngine(CSEngine e) {
        this.test = e.getInbox();
    }

    //********************************* Return in and Outbox

    public ConcurrentLinkedQueue<Packet> getInbox() {
        return inbox;
    }

    public ConcurrentLinkedQueue<Packet> getOutbox() {
        return outbox;
    }

    //********************************* Package Processing
    public void update(int id) {

        if (actorTree.get(id) == null) return;

        LinkedList<Double> extras = new LinkedList<Double>();

        extras.add(actorTree.get(id).getX());
        extras.add(actorTree.get(id).getY());
        extras.add(actorTree.get(id).getVX());
        extras.add(actorTree.get(id).getVY());
        extras.add(actorTree.get(id).getAX());
        extras.add(actorTree.get(id).getAY());
        Packet toReturn = new Packet(Packet.UPDATE, id);
        toReturn.setExtras(extras);
        outbox.add(toReturn);
        test.add(toReturn);
    }

    public void createpackage(int id) {

        if (actorTree.get(id) == null) return;

        LinkedList<Double> extras = new LinkedList<Double>();
        extras.add((double)id);
        extras.add(actorTree.get(id).getX());
        extras.add(actorTree.get(id).getY());
        extras.add(actorTree.get(id).getVX());
        extras.add(actorTree.get(id).getVY());
        extras.add(actorTree.get(id).getAX());
        extras.add(actorTree.get(id).getAY());
        Packet toReturn = new Packet(Packet.CREATE, id);
        toReturn.setExtras(extras);
        outbox.add(toReturn);
        test.add(toReturn);
        }

     private void unpackage() {
        Packet present  = inbox.poll();
        char actionId    = present.getActionID();
        int actorId     = present.getActorID();
        Actor actor     = actorTree.get(actorId);
        Iterator extras = present.getExtras().iterator();
        switch(actionId) {
            case Packet.MOVE: {
                System.out.println(actionId);   
                actor.setVX((Double) extras.next());
                actor.setVY((Double) extras.next());
            } break;

            case Packet.CREATE: {
                int index =  (int) ((double) extras.next());
                giveActor(index, (Double) extras.next(), (Double) extras.next(), (Double) extras.next(), 
                    (Double) extras.next(), (Double) extras.next(), (Double) extras.next(), images[index]);
            } break;
            case Packet.KILL: {
                killActor(actorId);
            } break;
            case Packet.PORT: {
                actor.setX((Double) extras.next());
                actor.setY((Double) extras.next());
                actor.setVX(0.0);
                actor.setVY(0.0);
                actor.setAX(0.0);
                actor.setAY(0.0);
            }
            default: break; 
        }
    }

    //********************************* Create / Delete Actors

    public void giveActor(int type, double x, double y, double vx, double vy, double ax, double ay, String pic) {
        if (type == 0) {
            Player a = new Player((idcount), x, y, pic);
            a.setVX(vx);
            a.setVY(vy);
            a.setAX(ax);
            a.setAY(ay);
            actorTree.put((idcount), a);
            createpackage(idcount);
            idcount++;
        }
    }

    public void killActor(int id) {
        actorTree.delete(id);
        Iterable<Integer> keys = actorTree.keys();
    }

    //******************************** simple update call

    // simple update call to all actors
    public void run() {
        if (!inbox.isEmpty()) {
            System.out.println("got mail ss");
            // handle incoming mail
            unpackage();
        }
        for (int i = 0; i < idcount; i++) {
            update(i);
        }
    }

    //********************************* Move actor calls

    // give Actor a an up impulse
    private void moveUp(Actor a) {
        if (a == null) throw new java.lang.IllegalArgumentException("Null Actor to moveUp");
        double currVY = a.getVY();
        currVY += impulse;
        a.setVY(currVY);
    }

    // give Actor a a down impulse
    private void moveDown(Actor a) {
        if (a == null) throw new java.lang.IllegalArgumentException("Null Actor to moveDown");
        double currVY = a.getVY();
        currVY -= impulse;
        a.setVY(currVY);
    }

    // give Actor a a left impulse
    private void moveLeft(Actor a) {
        if (a == null) throw new java.lang.IllegalArgumentException("Null Actor to moveLeft");
        double currVX = a.getVX();
        currVX -= impulse;
        a.setVX(currVX);
    }

    // give Actor a a right impulse
    private void moveRight(Actor a) {
        if (a == null) throw new java.lang.IllegalArgumentException("Null Actor to moveRight");
        double currVX = a.getVX();
        currVX += impulse;
        a.setVX(currVX);
    }

}