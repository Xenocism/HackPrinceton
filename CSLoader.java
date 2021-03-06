// Goes through the game file heirarchy and loads actor information
// into the engine
import java.io.*;
import java.util.Scanner;
public class CSLoader {
    private static final String build = "build";
    private static final String player = "build/player.txt";
    private File buildFolder;
    private File playerFile;

    CSEngine engine;

    public CSLoader(CSEngine engine) throws IOException {
        this.engine = engine;
        this.buildFolder = new File(build);
        this.playerFile = new File(player);
        Scanner readMe = new Scanner(playerFile);
        while (readMe.hasNext()) {
            String nextline = readMe.next();
            System.out.println(readMe.next());
        }
    }

    // test main
    public static void main(String[] args) throws IOException {
        CSEngine engine = new CSEngine();
        CSLoader loader = new CSLoader(engine);
    }
}