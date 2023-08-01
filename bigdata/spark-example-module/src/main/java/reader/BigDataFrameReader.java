package reader;

import lombok.RequiredArgsConstructor;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import utils.SparkUtils;

import java.io.Serializable;

@RequiredArgsConstructor
public class BigDataFrameReader implements Reader<Dataset<Row>> , Serializable {
    private final String fileName;
    @Override
    public Dataset<Row> get(){
        Dataset<Row> addressDf = SparkUtils.getSpark().read()
                .format("csv")
                .option("header", "true")
                .option("delimiter", "|")
                .load("C:\\Users\\144ga\\IdeaProjects\\big-data-common\\bigdata\\spark-example-module\\target\\classes\\"+fileName+".csv");
        return addressDf;
    }
}
