package data;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public class SampleSparkDataClass {
    public final Dataset<Row> data;

    public SampleSparkDataClass(Dataset<Row> data) {
        this.data = data;
    }
}
