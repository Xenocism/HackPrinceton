/*
    Basic actor file, has position, velocity, accel data and image name

*/
public class Actor {

    private static final double CUTOFF = 1;
    /*****************************   Variables   ***********************************/
    private int id; // id of this Actor

    private double x;   // x coord of this actor on the screen
    private double vx;  // current x velocity of this Actor 
    private double ax;  // current x acceleration of this Actor

    private double y;  // y coord of this actor on the screen
    private double vy; // current y velocity of this Actor
    private double ay; // current y acceleration of this Actor

    private double maxV; // maximum velocity of this actor

    private String imgName; // image associated with this actor

    /*****************************   Constructors *********************************/

    // empty constructor
    public Actor(int id) {
        this.id = id;
        this.x = 0;
        this.y = 0;
        this.imgName = null;
    }

    // full constructor
    public Actor(int id, double x, double y, String imgName) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.maxV = 5;
        this.imgName = imgName;
    }

    /************************ Position/Velocity/Accel set/gets ********************/
    // set x coord of this Actor
    public void setX(double x) {
        this.x = x;
    }

    // set y coord of this Actor
    public void setY(double y) {
        this.y = y;
    }

    // set x velocity of this Actor
    public void setVX(double vx) {
        this.vx = vx;
    }

    // set y velocity of this Actor
    public void setVY(double vy) {
        this.vy = vy;
    }

    // set x accel of this Actor
    public void setAX(double ax) {
        this.ax = ax;
    }

    // set y accel of this Actor
    public void setAY(double ay) {
        this.ay = ay;
    }

    public void setMaxV(double maxV) {
        this.maxV = maxV;
    }

    //***************** getters

    // returns the x location of this Actor
    public double getX() {
        return x;
    }

        // returns the y location of this Actor
    public double getY() {
        return y;
    }   

    public double getVX() {
        return vx;
    }

        // returns the y location of this Actor
    public double getVY() {
        return vy;
    }

    public double getAX() {
        return ax;
    }

        // returns the y location of this Actor
    public double getAY() {
        return ay;
    }

    // returns maximum velocity 
    public double getMaxV() {
        return maxV;
    }

    // set the filepath for this Actor's image
    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    // returns the filepath for this Actor's Image
    public String getImgName() {
        return this.imgName;
    }

    public int getID() {
        return id;
    }

    public void update() {
        x += vx;
        vx += ax;

        if (vx > maxV) 
            vx = maxV;
        if (vx < (maxV * -1))
            vx = -maxV;
        if (Math.abs(vx) < CUTOFF)
            vx = 0;

        y += vy;
        vy += ay;

        if (vy > maxV) 
            vy = maxV;
        if (vy < (maxV * -1))
            vy = -maxV;
        if (Math.abs(vy) < CUTOFF)
            vy = 0;
    }
}