package writer;

import data.SampleSparkDataClass;
import org.apache.spark.sql.DataFrameWriter;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;

import java.util.Arrays;

public class BigDataFrameWriter implements Writer<SampleSparkDataClass> {

    private static Writer<Dataset<Row>> writer = DataFrameSingleCsvWriter.
            builder()
            .savemode(SaveMode.Overwrite)
            .delimiter(";")
            .numPartition(1)
            .path("target/classes/output")
            //.partitionKeys(Arrays.asList("state","city"))
            .hasHeader(true)
            .build();

    @Override
    public void accept(SampleSparkDataClass sampleSparkDataClass) {
        writer.accept(sampleSparkDataClass.data);
        DataFrameWriter<Row> x = sampleSparkDataClass.data.repartition(1).write();
    }
}
