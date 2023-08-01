package utils;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

import java.io.IOException;

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

    public static Path getHdfsPath(String path){
        try {
            FileSystem hdfs = FileSystem.get(jsc.hadoopConfiguration());
           Path wd = hdfs.getWorkingDirectory();
           return new Path(wd,path);
        } catch (IOException e) {
            return new Path(path);
        }
    }
}
