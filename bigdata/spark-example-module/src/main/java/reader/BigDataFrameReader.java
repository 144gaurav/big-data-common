package reader;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import utils.SparkUtils;

import java.io.Serializable;

public class BigDataFrameReader implements Reader<Dataset<Row>> , Serializable {

    @Override
    public Dataset<Row> get(){
        Dataset<Row> addressDf = SparkUtils.getSpark().read()
                .format("csv")
                .option("header", "true")
                .option("delimiter", "|")
                .load("C:/SparkWorkspace/CommonBigdataCoding/target/classes/address.csv");
        return addressDf;
    }
}
