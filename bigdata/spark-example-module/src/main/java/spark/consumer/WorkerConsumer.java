package spark.consumer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import service.ConcurrentExecutorPool;

import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor
@Builder
public class WorkerConsumer <T, W> implements Consumer<T> {

    private final ConcurrentExecutorPool<T, W> executorService;
    private final Function<T, W> jobSupplier;
    @Override
    public void accept(T task) {

        System.out.println("Inside Worker Consumer Accept");
        executorService.submit(task,jobSupplier);
    }
}
