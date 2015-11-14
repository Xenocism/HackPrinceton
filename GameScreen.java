// Dependancies: StdDraw.java

public class GameScreen {

    private CSEngine engine;
    private Iterable<Actor> iterable;
    private double mouseX;
    private double mouseY;
    private Player player;

    public GameScreen() {}

    public void setEngine(CSEngine e) {
        this.engine = e;
    }

    public void setActors(Iterable<Actor> x) {
        this.iterable = x;
    }

    public void init() {

        player = new Player(20.0, 20.0, "images\\fill.png");

        StdDraw.setCanvasSize(1000,500);
        StdDraw.setXscale(0, 1000);
        StdDraw.setYscale(0, 500);

        StdDraw.rectangle(500.0, 250.0, 500.0, 250.0);

        engine.giveActor(player, 0);
    }

    public void run() {

        StdDraw.clear();

        Double xshift = 500.0 - player.getX();
        Double yshift = 200.0 - player.getY();
        //System.out.println(player.getX() + "  " + player.getY());

        for (Actor a : iterable) {
            if (a instanceof Player) {
                StdDraw.picture(500, 200, a.getImgName());
            }
            else {
                StdDraw.picture((a.getX() + xshift), (a.getY() + yshift), a.getImgName());
            }
        }

        StdDraw.rectangle(500.0 + xshift, 250.0 + yshift, 500.0, 250.0);
        
        if (StdDraw.hasNextKeyTyped()) {
            char next = StdDraw.nextKeyTyped();
            switch (next) {
                case 'w': engine.sendEvent(player, 0); break;
                case 'a': engine.sendEvent(player, 1); break;
                case 's': engine.sendEvent(player, 2); break;
                case 'd': engine.sendEvent(player, 3); break;
                }
            }
        if (StdDraw.mousePressed()) {
            mouseX = StdDraw.mouseX();
            mouseY = StdDraw.mouseY();
            engine.sendevent(player, 4, mouseX, mouseY);
        }
        StdDraw.show(10);
    }
}