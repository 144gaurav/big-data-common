package accumulator;

public class RunMetadataPortfolioAccumulator extends KeyValueAccumulator<String, RunMetadataAccumulator<String>>{

    public static final String PORTFOLIO_ACCUMULATOR_NAME = RunMetadataPortfolioAccumulator.class.getName() + "_PORTFOLIO";

    @Override
    public String toString() {
        return "RunMetadataPortfolioAccumulator{} " + super.accs.keySet().size();
    }
}
