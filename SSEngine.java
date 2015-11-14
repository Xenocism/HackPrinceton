import java.util.TreeMap;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SSEngine {

    private int idcount;
    private static final int UP     = 0;
    private static final int LEFT   = 1;
    private static final int DOWN   = 2;
    private static final int RIGHT  = 3;

    private static final String[] images = {"images\\fill.png"}

    private static final int[] xBounds  = new int[]{0, 1000};
    private static final int[] yBounds  = new int[]{0, 500};

    private static final int impulse = 5;
    private static final double FRICTION = 0.995;

    private RedBlackBST<Integer, Actor> actorTree;

    private ConcurrentLinkedQueue<Packet> inbox;
    private ConcurrentLinkedQueue<Packet> outbox;

    // Constructor, init and setters

    public CSEngine() {
        this.actors     = new LinkedList<Actor>();
        this.actorTree  = new RedBlackBST<Integer, Actor>();
        this.inbox      = new ConcurrentLinkedQueue<Packet>();
        this.outbox     = new ConcurrentLinkedQueue<Packet>();
    }

    // called after mailroom and gamescreen are set, returns true
    // if all checks out, false if not
    public boolean init() {
        if (screen == null)     return false;
        if (actors == null)     return false;
        if (actorTree == null)  return false;
        if (inbox == null)      return false;
        if (outbox == null)     return false;
        // ping server
        return true;
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

        if (actortree.get(id) == null) return;

        LinkedList<Double> extras = new LinkedList<Double>();

        extras.add(actortree.get(id).getX());
        extras.add(actortree.get(id).getY());
        extras.add(actortree.get(id).getVX());
        extras.add(actortree.get(id).getVY());
        extras.add(actortree.get(id).getAX());
        extras.add(actortree.get(id).getAY());
        Packet toReturn = new Packet(5, id);
        toReturn.setExtras(extras);
        outbox.add(toReturn);
    }

    public void createpackage(int id) {
        if (actortree.get(id) == null) return;

        LinkedList<Double> extras = new LinkedList<Double>();

        extras.add(actortree.get(id).getX());
        extras.add(actortree.get(id).getY());
        extras.add(actortree.get(id).getVX());
        extras.add(actortree.get(id).getVY());
        extras.add(actortree.get(id).getAX());
        extras.add(actortree.get(id).getAY());
        Packet toReturn = new Packet(2, id);
        toReturn.setExtras(extras);
        outbox.add(toReturn);
        }

     private void unpackage() {
        Packet present  = inbox.poll();
        int actionId    = present.getActionID();
        int actorId     = present.getActorID();
        Actor actor     = actorTree.get(actorId);
        Iterator extras = present.getExtras().iterator();
        switch(actionId) {
            case Packet.MOVE: {
                actor.setVX(extras.next());
                actor.setVY(extras.next());
            } break;

            case Packet.CREATE: {
                int index = (int) extras.next();
                giveActor(index, extras.next(), extras.next(), extras.next(), extras.next(), extras.next(), extras.next(), images[index]);
            } break;
            case Packet.KILL: {
                killActor(actorId);
            } break;
            case Packet.PORT: {
                actor.setX((extras.next());
                actor.setY(extras.next());
                actor.setVX(0.0);
                actor.setVY(0.0;
                actor.setAX(0.0);
                actor.setAY(0.0;
            }
            default: break; 
        }
    }

    //********************************* Create / Delete Actors

    public void giveActor(int type, double x, double y, double vx, double vy, double ax, double ay, String pic) {
        switch (type) {
            case 1: {
                Player a = new Player((idcount + 1), x, y, pic);
                a.setVX(vx);
                a.setVY(vy);
                a.setAX(ax);
                a.setAY(ay);
            } break;
            default: break
        }
        actorTree.put(new Integer((idcount + 1), a);
        createpackage(idcount + 1);
        idcount++;

    }

    public void killActor(int id) {
        actorTree.delete(id);
        Iterable<Integer> keys = actorTree.keys();
    }

    //******************************** simple update call

    // simple update call to all actors
    public void run() {
        if (!inbox.isEmpty()) {
            // handle incoming mail
            unpackage();
        }
        for (int i = 0, i < idcount; i++) {
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