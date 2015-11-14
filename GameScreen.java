// Dependancies: StdDraw.java

public class GameScreen {

    private Engine engine;

    public GameScreen() {}

    public void setEngine(Engine e) {
        this.engine = e;
    }

    public void run() {

        Player player = new Player();

        StdDraw.setCanvasSize(1000,500);
        StdDraw.setXscale(0, 1000);
        StdDraw.setYscale(0, 500);

        while (true) {
            for (Actor a : engine.getActors()) {
                StdDraw.picture(a.getX(), a.getY(), a.setImgName());
            }

        char next = StdDraw.nextKeyTyped();

        switch (next) {
            case 'w': engine.update(player, 0);
            case 'a': engine.update(player, 1);
            case 's': engine.update(player, 2);
            case 'd': engine.update(player, 3);
        }
        StdDraw.show(10);
        }
    }
}