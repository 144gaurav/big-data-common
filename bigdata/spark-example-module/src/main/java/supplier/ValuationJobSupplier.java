package supplier;

import job.SparkJobFactory;
import lombok.Builder;
import service.SparkJobContext;


public class ValuationJobSupplier extends SparkJobSupplier<String>{
    @Builder
    public ValuationJobSupplier(SparkJobFactory<String, ?, ?> sparkJobFactory) {
        super(sparkJobFactory);
    }

    @Override
    void onStart(String task, SparkJobContext sparkJobContext) {
        System.out.println("Database query");
    }

    @Override
    void onSuccess(String task, SparkJobContext sparkJobContext) {
        System.out.println("Database query");

    }

    @Override
    void onError(String task, Exception e) {
        System.out.println("Database query");

    }
}
