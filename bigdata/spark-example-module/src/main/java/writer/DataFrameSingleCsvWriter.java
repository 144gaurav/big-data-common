package writer;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.hadoop.fs.Path;
import org.apache.spark.sql.DataFrameWriter;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import scala.collection.JavaConversions;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
@ToString
@Getter
public class DataFrameSingleCsvWriter implements Writer<Dataset<Row>>{
    private final String path;
    private final boolean hasHeader;
    private String delimiter;
    private SaveMode savemode;
    private int numPartition;
    private List<String> partitionKeys;

    @Builder
    public DataFrameSingleCsvWriter(String path, boolean hasHeader, String delimiter, SaveMode savemode, int numPartition, List<String> partitionKeys) {
        this.path = path;
        this.hasHeader = hasHeader;
        this.delimiter = delimiter;
        this.savemode = savemode;
        this.numPartition = numPartition;
        this.partitionKeys = partitionKeys;
    }


    @Override
    public void accept(Dataset<Row> rowDataset) {
        rowDataset.show(false);
                DataFrameWriter<Row> dataFrameWriter =
                        rowDataset.repartition(numPartition)
                                .write()
                                .mode(savemode)
                        .option("header",hasHeader)
                        .option("delimiter",delimiter);
        applyPartitioning(dataFrameWriter).csv(this.path);
    }

    private DataFrameWriter<Row> applyPartitioning(DataFrameWriter<Row> writerBuilder){
        return partitionKeys.isEmpty() ? writerBuilder : writerBuilder.partitionBy(JavaConversions.asScalaBuffer(partitionKeys));
    }

}
