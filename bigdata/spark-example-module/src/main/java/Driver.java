import job.BigDataJobFactory;
import spark.LongRunningSparkServiceFactory;

public class Driver {
    public static void main(String[] args) {
//        BigDataJobFactory bigDataJobFactory = new BigDataJobFactory();
//        bigDataJobFactory.getJob("address",null).run();
        LongRunningSparkServiceFactory.longRunningSparkService().execute();
    }
}
