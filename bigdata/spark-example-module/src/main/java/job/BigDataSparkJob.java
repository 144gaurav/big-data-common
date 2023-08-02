package job;

import computer.ProcessDataFrame;
import data.SampleSparkDataClass;
import lombok.RequiredArgsConstructor;
import org.apache.spark.sql.DataFrameWriter;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import reader.BigDataFrameReader;
import reader.Reader;
import writer.BigDataFrameWriter;
import writer.Writer;

@RequiredArgsConstructor
public class BigDataSparkJob implements SparkJob<Dataset<Row>, SampleSparkDataClass>{

    private final String fileName;
    private final Reader<Dataset<Row>> reader;
    @Override
    public Reader<Dataset<Row>> reader(){
        return reader;
    }

    @Override
    public Writer<SampleSparkDataClass> writer(){
    return new BigDataFrameWriter();
    }

    @Override
    public SampleSparkDataClass apply(Dataset<Row> inputDf){
        System.out.println("Inside apply method");
        inputDf.show();
        return new SampleSparkDataClass(new ProcessDataFrame().get(inputDf));
    }

}
