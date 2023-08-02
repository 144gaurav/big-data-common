package reader;

import accumulator.AccumulatorHolder;
import accumulator.KeyValueAccumulator;
import accumulator.RunMetadataAccumulator;
import accumulator.RunMetadataPortfolioAccumulator;
import lombok.RequiredArgsConstructor;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.jboss.netty.util.internal.ConcurrentHashMap;
import utils.SparkUtils;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static accumulator.RunMetadataPortfolioAccumulator.PORTFOLIO_ACCUMULATOR_NAME;
import static org.apache.spark.sql.functions.col;

@RequiredArgsConstructor
public class BigDataFrameReader implements Reader<Dataset<Row>> , Serializable {
    private final String fileName;
    private final AccumulatorHolder<String> accumulatorHolder;
    @Override
    public Dataset<Row> get(){


        Dataset<Row> addressDf = SparkUtils.getSpark().read()
                .format("csv")
                .option("header", "true")
                .option("delimiter", "|")
                .load("bigdata/spark-example-module/src/main/resources/"+fileName+".csv");

        Set<String> names = addressDf.select(col("empname")).collectAsList().stream().map(row -> row.get(0).toString()).collect(Collectors.toSet());
        if(accumulatorHolder!=null){
            KeyValueAccumulator<String, RunMetadataAccumulator<String>> acc =  accumulatorHolder.getAccumulator(PORTFOLIO_ACCUMULATOR_NAME, RunMetadataPortfolioAccumulator.class);
            Map<String, RunMetadataAccumulator<String>> data = new ConcurrentHashMap<>();
            data.put("empname", new RunMetadataAccumulator<>(names));
            acc.add(data);
        }
        return addressDf;
    }
}
