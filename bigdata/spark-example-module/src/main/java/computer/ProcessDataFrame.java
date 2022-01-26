package computer;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.*;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.types.DataTypes;
import utils.SparkUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ProcessDataFrame {
    private static Broadcast<Map<String, String>> map;
    public Dataset<Row> get(Dataset<Row> inputDf){
        SparkUtils.getSpark().sqlContext().udf().register("FETCH_CITY_NAME", fetch_city_name, DataTypes.StringType);
        SparkUtils.getSpark().sqlContext().udf().register("FETCH_STATE_NAME", fetch_state_name, DataTypes.StringType);
Dataset<Row> finalDf = inputDf
        .withColumn("city", fetchCityNameCall())
        .withColumn("state", call_fetch_state_name());
        return finalDf;
    }

    static {
       initialiseMap();
    }
    static UDF1<String, String> fetch_state_name = (city) -> {
        return map.getValue().get(city);
    };

    public static void initialiseMap()  {
        JavaSparkContext javaSparkContext = JavaSparkContext.fromSparkContext(SparkUtils.getSpark().sparkContext());
        javaSparkContext.setLogLevel("WARN");
        try {
            map = javaSparkContext.broadcast(getBroadCastMapFromFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Column fetchCityNameCall() {
        return functions.callUDF("FETCH_CITY_NAME", functions.col("address"));
    }

    public static Column call_fetch_state_name() {
        return functions.callUDF("FETCH_STATE_NAME", functions.col("city"));
    }


    public static UDF1<String, String> fetch_city_name = (address) -> {
        List<String> addressList = Arrays.asList(address.split(","));
        for (String add : addressList) {
            Optional<String> state = Optional.ofNullable(map.getValue().get(add.trim()));
            if (state.isPresent()) {
                return add.trim();
            }
        }
        return null;
    };

    static Map<String, String> getBroadCastMapFromFile() throws IOException {
        Map<String, String> map
                = new HashMap<String, String>();
        File file = new File("C:/SparkWorkspace/CommonBigdataCoding/target/classes/city.csv");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = null;

        while ((line = br.readLine()) != null) {
            String parts[] = line.split(",");
            String city = parts[0].trim();
            String state = parts[1].trim();
            map.put(city, state);
        }
        return map;
    }
}
