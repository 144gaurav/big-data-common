import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.*;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.types.DataTypes;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class BroadcastMap {
    static Broadcast<Map<String, String>> map;

    public static void main(String[] args) throws IOException {

        SparkSession spark = SparkSession.builder()
                .master("local[*]")
                .appName("Address Appliation")
                .getOrCreate();
        Dataset<Row> addressDf = spark.read()
                .format("csv")
                .option("header", "true")
                .option("delimiter", "|")
                .load("C:/SparkWorkspace/CommonBigdataCoding/target/classes/address.csv");

        String path = new BroadcastMap().getClass().getResource("/address.csv").toString();
        System.out.println(path);
        JavaSparkContext javaSparkContext = JavaSparkContext.fromSparkContext(spark.sparkContext());
        javaSparkContext.setLogLevel("WARN");
        map = javaSparkContext.broadcast(getBroadCastMapFromFile());
        spark.sqlContext().udf().register("FETCH_CITY_NAME", fetch_city_name, DataTypes.StringType);
        spark.sqlContext().udf().register("FETCH_STATE_NAME", fetch_state_name, DataTypes.StringType);
        Dataset<Row> finalDf = addressDf
                .withColumn("city", fetchCityNameCall())
                .withColumn("state", call_fetch_state_name());

        map.getValue().entrySet()
                .stream().forEach(x -> {
            System.out.println("{"+x.getKey() + " -> " + x.getValue() + " } ");
        });
        addressDf.show(false);
        finalDf.show(false);
        finalDf.write().mode("overwrite").parquet("C:/SparkWorkspace/output");
    }

    public static Column fetchCityNameCall() {
        return functions.callUDF("FETCH_CITY_NAME", functions.col("address"));
    }

    public static Column call_fetch_state_name() {
        return functions.callUDF("FETCH_STATE_NAME", functions.col("city"));
    }

    static UDF1<String, String> fetch_state_name = (city) -> {
        return map.getValue().get(city);
    };

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
