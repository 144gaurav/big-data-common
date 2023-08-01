package spark;

import job.BigDataJobFactory;
import service.ValuationExecutor;
import spark.consumer.WorkerConsumer;
import supplier.ValuationJobSupplier;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class LongRunningSparkServiceFactory {

    public static LongRunningSparkService longRunningSparkService(){
        ValuationJobSupplier valuationJobSupplier = getSparkJobSupplier();
        WorkerConsumer consumer = new WorkerConsumer<>(ValuationExecutor.getInstance(),valuationJobSupplier);


        LongRunningSparkService longRunningSparkService =  LongRunningSparkService.builder()
                .stopServiceAFterHours(Optional.of(12))
                .loopInterval(Optional.of(10000))
                .inputConsumer(consumer)
                .inputStreamSupplier( () -> getStreamOfStrings())
                .build();
        longRunningSparkService.setListner(new SparkLongRunningListner());
        return longRunningSparkService;
    }

    private static ValuationJobSupplier getSparkJobSupplier(){
        return ValuationJobSupplier.builder()
                .sparkJobFactory(new BigDataJobFactory())
                .build();
    }

    private static Stream<String> getStreamOfStrings(){
        List<String> list = Collections.singletonList("address");
        return list.stream();
    }
}
