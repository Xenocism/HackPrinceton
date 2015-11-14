import java.util.TreeMap;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SSEngine {

    private int idcount;

    public void update(int id) {

        LinkedList<Double> extras = new LinkedList<Double>();

        extras.add(actortree.get(id).getX());
        extras.add(actortree.get(id).getY());
        extras.add(actortree.get(id).getVX());
        extras.add(actortree.get(id).getVY());
        extras.add(actortree.get(id).getAX());
        extras.add(actortree.get(id).getAY());
        outbox.add(new Packet(5, id, extras));
    }

      public void giveActor(Actor a) {
        if (a == null) throw new java.lang.IllegalArgumentException("Null Actor to giveActor");
        actorTree.put(new Integer(idcount + 1), a);
        actors.push(a);
        idcount++;
    }
}