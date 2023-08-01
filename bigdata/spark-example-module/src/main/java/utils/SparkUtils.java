package utils;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

public class SparkUtils {
    public static SparkSession getSpark() {
        return spark;
    }

    public static SparkSession spark;
    public static JavaSparkContext jsc;

    static {
        spark = SparkSession.builder()
                .master("local[*]")
                .appName("Address Appliation")
                .getOrCreate();
        jsc = new JavaSparkContext(spark.sparkContext());
    }
}
