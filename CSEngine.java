
import java.util.TreeMap;
import java.util.Stack;

public class CSEngine {
    private static final int UP     = 0;
    private static final int LEFT   = 1;
    private static final int DOWN   = 2;
    private static final int RIGHT  = 3;

    private static final int[] xBounds  = new int[]{0, 1000};
    private static final int[] yBounds  = new int[]{0, 500};

    private static final int impulse = 5;

    private RedBlackBST<Integer, Actor> actorTree;
    private Stack<Actor> actors;
    private CSMailroom mailroom;
    private GameScreen screen;
    
    private int currID;

    // Constructor, init and setters

    public CSEngine() {
        this.actors     = new Stack<Actor>();
        this.actorTree  = new RedBlackBST<Integer, Actor>();
        this.currID     = 0;
    }

    public void setGameScreen(GameScreen screen) {
        this.screen = screen;
    }

    public void setCSMailroom(CSMailroom mailroom) {
        this.mailroom = mailroom;
    }

    // called after mailroom and gamescreen are set, returns true
    // if all checks out, false if not
    public boolean init() {
        if (screen == null)     return false;
        if (mailroom == null)   return false;
        if (actors == null)     return false;
        if (actorTree == null)  return false;
        // ping server
        return true;
    }

    //********************  Event and mail control

    // handle general event given an actor and an id
    public void event(Actor a, int id) {
        if (a == null) throw new java.lang.IllegalArgumentException("Null Actor to Event (move)");
        // switch(id) {
        //     case UP:    break;
        //     case LEFT:  break;
        //     case DOWN:  break;
        //     case RIGHT: break;
        //     default:    break;
        // }
        receiveMail(a, id);
    }

    // handle mouse click event given an actor
    public void event(Actor a, int id, double x, double y) {
        if (a == null) throw new java.lang.IllegalArgumentException("Null Actor to Event (mouse)");
        switch(id) {
            default: break;
        }
    }

    // given information, sends mail to mailroom
    public void sendMail(Actor a, int id) {
        if (a == null) throw new java.lang.NullPointerException();
        switch(id) {
            case UP:    break;
            case LEFT:  break;
            case DOWN:  break;
            case RIGHT: break;
            default:    break;
        }
    }

    // given mail information, acts accordingly (called from mailroom)
    public void receiveMail(Actor a, int id) {
        if (a == null) throw new java.lang.IllegalArgumentException("Null Actor to receiveMail");
        switch(id) {
            case UP:    moveUp(a);      break;
            case LEFT:  moveDown(a);    break;
            case DOWN:  moveRight(a);   break;
            case RIGHT: moveLeft(a);    break;
            default:    break; 
        }
    }

    // returns all active actors this engine controls
    public Iterable<Actor> getActors() {
        return actors;
    }   

    //******************************** simple update call

    // simple update call to all actors
    public void run() {
        for (Actor a : actors) {
            a.update();
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

    //************************** Actor give/kill calls

    public void giveActor(Actor a, int id) {
        if (a == null) throw new java.lang.IllegalArgumentException("Null Actor to giveActor");
        actorTree.put(new Integer(id), a);
        actors.push(a);
        screen.setActors(actors);
    }

    public void killActor(int id) {
        actorTree.delete(id);
        Iterable<Integer> keys = actorTree.keys();
        actors = new Stack<Actor>();
        for (int i : keys) {
            actors.push(actorTree.get(i));
        }
        screen.setActors(actors);
    }
}
