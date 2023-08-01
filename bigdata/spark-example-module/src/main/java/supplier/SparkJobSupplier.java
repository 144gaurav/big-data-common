package supplier;

import accumulator.RunMetadataPortfolioAccumulator;
import job.SparkJob;
import job.SparkJobFactory;
import lombok.AllArgsConstructor;
import service.SparkJobContext;
import utils.SparkUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Function;

@AllArgsConstructor
public abstract class SparkJobSupplier<T> implements Function<T, SparkJobContext> {

    private final SparkJobFactory<T, ?, ?> sparkJobFactory;
    @Override
    public SparkJobContext apply(T task) {
        SparkJobContext valuationJobContext = getJobContext();
        SparkJob<?,?> valuationjob = sparkJobFactory.getJob(task,valuationJobContext);

        try{
                onStart(task,valuationJobContext);
            Instant start = Instant.now();
            valuationjob.run();
            Instant finish = Instant.now();
            System.out.println("spark execution time " + Duration.between(start,finish));
            onSuccess(task,valuationJobContext);
        }catch (Exception e){
            onError(task,e);
        }finally {
            closeSparkContext(valuationJobContext);
        }
        return valuationJobContext;
    }

    abstract void onStart(T task, SparkJobContext sparkJobContext);

    abstract void onSuccess(T task, SparkJobContext sparkJobContext);

    abstract void onError(T task, Exception e);

    protected SparkJobContext getJobContext(){
        SparkJobContext valuationJobContext = new SparkJobContext(SparkUtils.jsc.sc());
        valuationJobContext.registerAccumulator(RunMetadataPortfolioAccumulator.PORTFOLIO_ACCUMULATOR_NAME, new RunMetadataPortfolioAccumulator());
        return valuationJobContext;
    }


    protected void closeSparkContext(SparkJobContext sparkJobContext){
        try {
            sparkJobContext.cleanContext();
        }catch (Exception e){
            System.out.println("Error Occured");
        }
    }
}
