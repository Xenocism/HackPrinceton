import java.util.*;
public class Packet {
    public static final String START = "start";
    public static final String STOP = "stop";
    public static final char MOVE = 1;
    public static final char CREATE = 2;
    public static final char KILL = 3;
    public static final char PORT = 4;
    public static final char UPDATE = 5;

    private char actionID;
    private int actorID;
    private LinkedList<Double> extras;

    public Packet(char actionID, int actorID) {
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

    public char getActionID() {
        return actionID;
    }

    public int getActorID() {
        return actorID;
    }

}