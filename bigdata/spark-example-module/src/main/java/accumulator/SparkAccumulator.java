package accumulator;

import java.io.Serializable;

public interface SparkAccumulator<T> extends Serializable {

    T value();

    SparkAccumulator<T> merge(SparkAccumulator<T> other);
}
