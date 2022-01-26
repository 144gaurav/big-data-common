import job.BigDataJobFactory;

public class Driver {
    public static void main(String[] args) {
        BigDataJobFactory bigDataJobFactory = new BigDataJobFactory();
        bigDataJobFactory.getSparkJob().run();
    }
}
