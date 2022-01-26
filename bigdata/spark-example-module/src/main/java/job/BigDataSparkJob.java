package job;

import computer.ProcessDataFrame;
import data.SampleSparkDataClass;
import org.apache.spark.sql.DataFrameWriter;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import reader.BigDataFrameReader;
import reader.Reader;
import writer.BigDataFrameWriter;
import writer.Writer;

public class BigDataSparkJob implements SparkJob<Dataset<Row>, SampleSparkDataClass>{

    @Override
    public Reader<Dataset<Row>> reader(){
        return new BigDataFrameReader();
    }

    @Override
    public Writer<SampleSparkDataClass> writer(){
    return new BigDataFrameWriter();
    }

    @Override
    public SampleSparkDataClass apply(Dataset<Row> inputDf){
        return new SampleSparkDataClass(new ProcessDataFrame().get(inputDf));
    }

}
