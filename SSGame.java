public class SSGame {
    public static main(String[] args) {

        SSEngine engine = new SSEngine();
        SSMailroom mail = new SSMailroom(engine);
        SSMailroomRunner run = new SSMailroomRunner();

        engine.init();
        //run.start();

        while (true) {
            engine.run();
        }
    }
}