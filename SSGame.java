import java.io.*;
public class SSGame {
    public static void main(String[] args) throws IOException{

        SSEngine engine = new SSEngine();
        SSMailroom mail = new SSMailroom(engine);
        //SSMailroomRunner run = new SSMailroomRunner(mail);

        engine.init();
        mail.initSocket();

        while (true) {
            engine.run();
            mail.receivePacket();
            mail.sendPacket();
        }
    }
}
