public class CSGame {

    public static void main(String[] args) {

        CSMailroom mail = new CSMailroom();
        CSEngine engine = new CSEngine();
        GameScreen game = new GameScreen();

        game.setEngine(engine);
        engine.setGameScreen(game);
        //mail.setCSEngine(engine);

        game.init();
        engine.init();
        //mailroom.run();

        while (true) {
            engine.run();
            game.run();
        }
    }
}