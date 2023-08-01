package service;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public interface ConcurrentExecutorPoolConfig {

    default Optional<Integer> corePoolSize() {
        return Optional.of(Runtime.getRuntime().availableProcessors());
    }

    default Optional<Integer> maxPoolSize() {
        return Optional.empty();
    }

    default Optional<Long> keepAliveTime() {
        return Optional.empty();
    }

    default Optional<TimeUnit> unit() {
        return Optional.empty();
    }

    default Optional<BlockingQueue<Runnable>> workQueue() {
        return Optional.empty();
    }

    default Optional<ThreadFactory> threadFactory() {
        return Optional.empty();
    }

    default Optional<Integer> ConcurrentSetSize() {
        return Optional.empty();
    }
}
