
import java.util.TreeMap;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Iterator;

public class CSEngine {
    private static final int UP     = 0;
    private static final int LEFT   = 1;
    private static final int DOWN   = 2;
    private static final int RIGHT  = 3;

    private static final int[] xBounds  = new int[]{0, 1000};
    private static final int[] yBounds  = new int[]{0, 500};

    private static final int impulse = 5;
    private static final double FRICTION = 0.995;

    private RedBlackBST<Integer, Actor> actorTree;
    private LinkedList<Actor> actors;

    private ConcurrentLinkedQueue<Packet> inbox;
    private ConcurrentLinkedQueue<Packet> outbox;

    private GameScreen screen;

    // Constructor, init and setters

    public CSEngine() {
        this.actors     = new LinkedList<Actor>();
        this.actorTree  = new RedBlackBST<Integer, Actor>();
        this.inbox      = new ConcurrentLinkedQueue<Packet>();
        this.outbox     = new ConcurrentLinkedQueue<Packet>();
    }

    public void setGameScreen(GameScreen screen) {
        this.screen = screen;
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

    //********************  Event and mail control

    // handle general event given an actor and an id
    public void makePackage(Actor a, int eid) {
        if (a == null) throw new java.lang.IllegalArgumentException("Null Actor to Event (move)");
        int id = a.getID();
        switch(eid) {
            case UP:    break;
            case LEFT:  break;
            case DOWN:  break;
            case RIGHT: break;
            default:    break;
        }
    }

    // handle mouse click event given an actor
    public void makePackage(Actor a, int eid, double x, double y) {
        if (a == null) throw new java.lang.IllegalArgumentException("Null Actor to Event (mouse)");
        switch(eid) {
            case UP: break;
            case LEFT: break;
            case DOWN: break;
            case RIGHT: break;
            default: break;
        }
    }

    public ConcurrentLinkedQueue<Packet> getInbox() {
        return inbox;
    }

    public ConcurrentLinkedQueue<Packet> getOutbox() {
        return outbox;
    }

    //****************************** Package handling

    private void unpackage() {
        Packet present  = inbox.poll();
        int actionId    = present.getActionID();
        int actorId     = present.getActorID();
        Actor actor     = actorTree.get(actorId);
        Iterator extras = present.getExtras().iterator();
        switch(actionId) {
            case Packet.UPDATE:    {
                actor.setX((Double) extras.next());
                actor.setY((Double) extras.next());
                actor.setVX((Double) extras.next());
                actor.setVY((Double) extras.next());
                actor.setAX((Double) extras.next());
                actor.setAY((Double) extras.next());
            } break;

            case Packet.CREATE: {
                giveActor(actor, actorId);
            }        break;
            case Packet.KILL:  {
                killActor(actorId);
            }         break;
            default:    break; 
        }
    }
      
    //******************************** simple update call

    // simple update call to all actors
    public void run() {
        if (!inbox.isEmpty()) {
            // handle incoming mail
            unpackage();
            // do stuff with the packet
        }
    }

    //********************************* Move actor calls

    // // give Actor a an up impulse
    // private void moveUp(Actor a) {
    //     if (a == null) throw new java.lang.IllegalArgumentException("Null Actor to moveUp");
    //     double currVY = a.getVY();
    //     currVY += impulse;
    //     a.setVY(currVY);
    // }

    // // give Actor a a down impulse
    // private void moveDown(Actor a) {
    //     if (a == null) throw new java.lang.IllegalArgumentException("Null Actor to moveDown");
    //     double currVY = a.getVY();
    //     currVY -= impulse;
    //     a.setVY(currVY);
    // }

    // // give Actor a a left impulse
    // private void moveLeft(Actor a) {
    //     if (a == null) throw new java.lang.IllegalArgumentException("Null Actor to moveLeft");
    //     double currVX = a.getVX();
    //     currVX -= impulse;
    //     a.setVX(currVX);
    // }

    // // give Actor a a right impulse
    // private void moveRight(Actor a) {
    //     if (a == null) throw new java.lang.IllegalArgumentException("Null Actor to moveRight");
    //     double currVX = a.getVX();
    //     currVX += impulse;
    //     a.setVX(currVX);
    // }

    //************************** Actor give/kill calls

    // returns all active actors this engine controls
    public Iterable<Actor> getActors() {
        return actors;
    } 

    public void giveActor(Actor a, int id) {
        if (a == null) throw new java.lang.IllegalArgumentException("Null Actor to giveActor");
        actorTree.put(new Integer(id), a);
        actors.push(a);
        screen.setActors(actors);
    }

    public void killActor(int id) {
        actorTree.delete(id);
        Iterable<Integer> keys = actorTree.keys();
        actors = new LinkedList<Actor>();
        for (int i : keys) {
            actors.add(actorTree.get(i));
        }
        screen.setActors(actors);
    }
}
