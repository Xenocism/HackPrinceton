public class CSGame {

    public static void main(String[] args) {

        CSEngine engine = new CSEngine();
        CSMailroom mail = new CSMailroom(engine);
        GameScreen game = new GameScreen();
        CSMailroomRunner run = new CSMailroomRunner(mail);

        game.setEngine(engine);
        engine.setGameScreen(game);

        game.init();
        engine.init();
        run.start();

        while (true) {
            engine.run();
            game.run();
        }
    }
}