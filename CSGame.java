import java.io.IOException;
public class CSGame {

    public static void main(String[] args) throws IOException {

        CSEngine engine = new CSEngine();
        CSMailroom mail = new CSMailroom(engine);
        GameScreen game = new GameScreen();
        //CSMailroomRunner run = new CSMailroomRunner(mail);
        mail.initSocket("45.79.129.43");
        game.setEngine(engine);
        engine.setGameScreen(game);

        game.init();
        engine.init();
        //run.start();

        while (true) {
            mail.sendPacket();
            mail.receivePacket();
            engine.run();
            game.run();
        }
    }
}