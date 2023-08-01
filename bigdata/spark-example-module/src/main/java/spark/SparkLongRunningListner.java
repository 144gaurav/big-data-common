package spark;

import service.ValuationExecutor;
import spark.consumer.IntervalStreamSupplierConsumerListner;

import java.util.concurrent.TimeUnit;

public class SparkLongRunningListner implements IntervalStreamSupplierConsumerListner {
    @Override
    public void onStart() {
        System.out.println("onStart");
    }

    @Override
    public void onClose() {
        System.out.println("onClose");
        ValuationExecutor.getInstance().close(30, TimeUnit.SECONDS);
    }

    @Override
    public void onStartLoop() {
        System.out.println("onStartLoop");
    }

    @Override
    public void onEndLoop() {
        System.out.println("onEndLoop");

    }
}
