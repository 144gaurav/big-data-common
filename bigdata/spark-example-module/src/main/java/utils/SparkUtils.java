package utils;

import org.apache.spark.sql.SparkSession;

public class SparkUtils {
    public static SparkSession getSpark() {
        return spark;
    }

    public static SparkSession spark;

    static {
        spark = SparkSession.builder()
                .master("local[*]")
                .appName("Address Appliation")
                .getOrCreate();
    }
}
