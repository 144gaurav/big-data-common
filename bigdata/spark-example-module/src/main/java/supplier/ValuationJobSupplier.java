package supplier;

import accumulator.KeyValueAccumulator;
import accumulator.RunMetadataAccumulator;
import accumulator.RunMetadataPortfolioAccumulator;
import job.SparkJobFactory;
import lombok.Builder;
import service.SparkJobContext;

import static accumulator.RunMetadataPortfolioAccumulator.PORTFOLIO_ACCUMULATOR_NAME;


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
        RunMetadataPortfolioAccumulator acc = sparkJobContext.getAccumulatorHolder().getAccumulator(PORTFOLIO_ACCUMULATOR_NAME, RunMetadataPortfolioAccumulator.class);
        RunMetadataAccumulator racc =  acc.value().get("empname");
        System.out.println("Aggregated emp name : " + racc.getSet());

    }

    @Override
    void onError(String task, Exception e) {
        System.out.println("Database query");

    }
}
