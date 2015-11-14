public class CSMailroomTest {
    public static void main(String args[]) {
        CSMailroom testClient = new CSMailroom();
        CSMailroomRunner test = new CSMailroomRunner(testClient);
        test.start();
    }
}