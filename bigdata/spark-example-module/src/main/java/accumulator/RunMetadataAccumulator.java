package accumulator;

import lombok.Getter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class RunMetadataAccumulator<T> implements SparkAccumulator<Set<T>>{
    @Getter
    private final Set<T> set = Collections.synchronizedSet(new HashSet<>());

    public RunMetadataAccumulator(Set<T> set) {
        this.set.addAll(set);
    }

    @Override
    public Set<T> value() {
        synchronized (set){
            return Collections.unmodifiableSet(new HashSet<>(set));
        }
    }

    @Override
    public SparkAccumulator<Set<T>> merge(SparkAccumulator<Set<T>> other) {
        this.set.addAll(other.value());
        return this;
    }
}
