public class SSMailroomTest {
    public static void main(String args[]) {
        SSMailroom testClient = new SSMailroom();
        SSMailroomRunner test = new SSMailroomRunner(testClient);
        test.start();
    }  
}