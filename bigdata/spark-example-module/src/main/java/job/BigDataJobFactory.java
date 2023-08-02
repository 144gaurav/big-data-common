package job;

import data.SampleSparkDataClass;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import reader.BigDataFrameReader;
import service.SparkJobContext;

public class BigDataJobFactory implements SparkJobFactory<String, Dataset<Row>, SampleSparkDataClass>{

    @Override
    public SparkJob<Dataset<Row>, SampleSparkDataClass> getJob(String command, SparkJobContext sparkJobContext) {
        return new BigDataSparkJob(command,
                new BigDataFrameReader(command,sparkJobContext.getAccumulatorHolder()));
    }
}
