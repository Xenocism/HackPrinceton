public class CSGame {

    public static void main(String[] args) {

        CSEngine engine = new CSEngine();
        SSEngine ssEngine = new SSEngine(); // test change
        //CSMailroom mail = new CSMailroom(engine);
        GameScreen game = new GameScreen();
        //CSMailroomRunner run = new CSMailroomRunner();

        game.setEngine(engine);
        engine.setGameScreen(game);
        engine.setSSEngine(ssEngine); // test change
        ssEngine.setCSEngine(engine); // test change

        game.init();
        engine.init();
        ssEngine.init();
        //run.start();

        while (true) {
            engine.run();
            ssEngine.run();
            game.run();
        }
    }
}