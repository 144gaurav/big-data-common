package job;

public class BigDataJobFactory {
    public SparkJob getSparkJob(){
        return new BigDataSparkJob();
    }
}
