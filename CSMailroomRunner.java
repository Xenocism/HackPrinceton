import java.net.*;
import java.io.*;
import java.util.*;
public class CSMailroomRunner extends Thread {
    private CSMailroom room;

    public CSMailroomRunner(CSMailroom mail) {
        room = mail;
    }   

    public void run() {
        room.initSocket("45.79.129.43");
        while (true) {
            room.sendPacket();
            room.receivePacket();
        }
    }
}