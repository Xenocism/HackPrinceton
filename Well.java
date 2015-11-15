public class Well {

    private RedBlackBST<Integer, Terrain> hole;
    private double xcum;
    private double ycum;
    private int mass;

    public Well() {
        this.hole = new RedBlackBST<Integer, Terrain>();
        this.xcum = 0.0;
        this.ycum = 0.0;
        this.mass = 0;
    }

    public double xcenter() {
        return (xcum / hole.size());
    }

    public double ycenter() {
        return (ycum / hole.size());
    }

    public void add(Terrain t) {
        hole.put(t.getID(), t);
        xcum += t.getX();
        ycum += t.getY();
        mass++;
    }

    public void delete(int id) {
        Terrain t = hole.get(id);
        xcum -= t.getX();
        ycum -= t.getY();
        mass--;
        hole.delete(id);
    }

    public boolean contains(int id) {
        return hole.contains(id);
    }

}