
import java.util.TreeMap;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Iterator;

// Client side game engine, takes care of physics, maps, literally the universe
public class CSEngine {
    private static final int UP     = 0;
    private static final int LEFT   = 1;
    private static final int DOWN   = 2;
    private static final int RIGHT  = 3;

    private static final String[] images = {"images\\mdkp.png"};

    private static final int IMPULSE = 5;

    private RedBlackBST<Integer, Actor> actorTree;
    private LinkedList<Actor> actors;

    private ConcurrentLinkedQueue<Packet> inbox;
    private ConcurrentLinkedQueue<Packet> outbox;

    private GameScreen screen;

    // Constructor, init and setters

    //********** TEMP METHOD****************

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
        Packet create = new Packet(Packet.CREATE, -1);
        LinkedList<Double> extras = new LinkedList<Double>();
        extras.add(0.0);
        extras.add(500.0);
        extras.add(200.0);
        extras.add(0.0);
        extras.add(0.0);
        extras.add(0.0);
        extras.add(0.0);
        create.setExtras(extras);
        outbox.add(create);
        return true;
    }

    //********************  Event and mail control

    // handle general event given an actor and an id
    public void makePackage(Actor a, int eid) {
        if (a == null) throw new java.lang.IllegalArgumentException("Null Actor to Event (move)");
        int aid = a.getID();
        Packet toSend = null;
        LinkedList<Double> extras = new LinkedList<Double>();
        switch(eid) {
            case UP: {
                toSend = new Packet(Packet.MOVE, aid);
                extras.add(a.getVX());
                extras.add(a.getVY() + IMPULSE);
            }   break;
            case LEFT: {
                toSend = new Packet(Packet.MOVE, aid);
                extras.add(a.getVX() - IMPULSE);
                extras.add(a.getVY());
            } break;
            case DOWN: {
                toSend = new Packet(Packet.MOVE, aid);
                extras.add(a.getVX());
                extras.add(a.getVY() - IMPULSE);
            } break;
            case RIGHT: {
                toSend = new Packet(Packet.MOVE, aid);
                extras.add(a.getVX() + IMPULSE);
                extras.add(a.getVY());
            } break;
            case Packet.KILL: {
                toSend = new Packet(Packet.KILL, aid);
            }
            default:    break;
        }
        if (toSend != null) {
            toSend.setExtras(extras);
            outbox.add(toSend);
        }
    }

    // handle mouse click event given an actor
    public void makePackage(Actor a, int eid, double x, double y) {
        if (a == null) throw new java.lang.IllegalArgumentException("Null Actor to Event (mouse)");
        int aid = a.getID();
        Packet toSend = new Packet(Packet.PORT, aid);
        LinkedList<Double> extras = new LinkedList<Double>();
        extras.add(x);
        extras.add(y);

        if (toSend != null) {
            toSend.setExtras(extras);
            outbox.add(toSend);
        }
    }

    // makepackage for creating an actor
    public void makePackage(int actorType, double x, double y, double vx, 
                            double vy, double ax, double ay) {
        Packet toSend = new Packet(Packet.CREATE, -1);
        LinkedList<Double> extras = new LinkedList<Double>();
        Double typeConv = new Double(actorType);
        extras.add(typeConv);
        extras.add(x);
        extras.add(y);
        extras.add(vx);
        extras.add(vy);
        extras.add(ax);
        extras.add(ay);
        toSend.setExtras(extras);
        outbox.add(toSend);
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
        if (actionId != 5)
            System.out.println("actionid: " + actionId);
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
                Player toAdd = new Player(actorId);
                int type = (int) (double) extras.next();
                toAdd.setX((Double) extras.next());
                toAdd.setY((Double)extras.next());
                toAdd.setVX((Double)extras.next());
                toAdd.setVY((Double)extras.next());
                toAdd.setAX((Double)extras.next());
                toAdd.setAY((Double)extras.next());
                toAdd.setImgName(images[type]);
                giveActor(toAdd, actorId);
            } break;
            case Packet.KILL:  {
                killActor(actorId);
            } break;
            default:    break; 
        }
    }
      
    //******************************** simple update call

    // simple update call to all actors
    public void run() {
        if (!inbox.isEmpty()) {
            // handle incoming mail
            unpackage();
        }
    }

    //************************** Actor give/kill calls

    // returns all active actors this engine controls
    public Iterable<Actor> getActors() {
        return actors;
    } 

    public void giveActor(Actor a, int id) {
        if (a == null) throw new java.lang.IllegalArgumentException("Null Actor to giveActor");
        System.out.println("Heyo");
        actorTree.put(new Integer(id), a);
        actors.push(a);
        screen.setActors(actors);
        screen.setPlayer((Player) a);
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
