import java.net.*;
import java.io.*;
import java.util.*;
public class SSMailroomRunner extends Thread {
    private SSMailroom room;

    public SSMailroomRunner(SSMailroom mail) {
        room = mail;
    }   

    public void run() {
        Scanner in = new Scanner(System.in);
        room.initSocket();
        String line;
        while (true) {
            room.listenSocket();
        }
    }
}