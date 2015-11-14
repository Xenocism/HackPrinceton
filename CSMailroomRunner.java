import java.net.*;
import java.io.*;
import java.util.*;
public class CSMailroomRunner extends Thread {
    private CSMailroom room;

    public CSMailroomRunner(CSMailroom mail) {
        room = mail;
    }   

    public void run() {
        Scanner in = new Scanner(System.in);
        room.initSocket("45.79.129.43");

        while(true) {
            if (in.hasNext()) {
                String line = in.next();
                room.transmit(line);
                room.receive();
            }
        }
    }
}