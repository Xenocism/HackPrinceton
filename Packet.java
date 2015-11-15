import java.util.*;
public class Packet {
    public static final String START = "start";
    public static final String STOP = "stop";
    public static final int MOVE = 1;
    public static final int CREATE = 2;
    public static final int KILL = 77;
    public static final int PORT = 4;
    public static final int UPDATE = 5;

    private int actionID;
    private int actorID;
    private LinkedList<Double> extras;

    public Packet(int actionID, int actorID) {
        if (actionID < 0 || actionID > Character.MAX_VALUE) 
            throw new NullPointerException("Action ID isn't valid");
        this.actionID = actionID;
        this.actorID = actorID;
        extras = null;
    }

    public void setExtras(LinkedList<Double> extras) {
        this.extras = extras;
    }

    public LinkedList<Double> getExtras() {
        return extras;
    }

    public int getActionID() {
        return actionID;
    }

    public int getActorID() {
        return actorID;
    }

}