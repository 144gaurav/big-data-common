package spark;

import spark.consumer.IntervalStreamSupplierConsumer;
import supplier.Supplier;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class IntervalStreamProcessor<T> extends IntervalStreamSupplierConsumer<T> {

    private final Consumer<T> inputConsumer;

    public IntervalStreamProcessor(Optional<Integer> loopInterval, Supplier<Stream<T>> inputStreamSupplier, Consumer<T> inputConsumer) {
        super(loopInterval, inputStreamSupplier);
        this.inputConsumer = inputConsumer;
    }


    @Override
    public void process(T tasks) {
        System.out.println("Interval consumer process task : " + tasks.toString());
        inputConsumer.accept(tasks);
    }
}
