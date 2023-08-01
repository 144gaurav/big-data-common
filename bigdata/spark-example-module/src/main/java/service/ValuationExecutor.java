package service;

import org.spark_project.guava.util.concurrent.ThreadFactoryBuilder;

import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class ValuationExecutor extends ConcurrentExecutorPool<String,SparkJobContext>{
    public static ValuationExecutor getInstance(){
        return Holder.instance;
    }

    private static class Holder{
        public static ValuationExecutor instance = new ValuationExecutor(concurrentExecutorPoolConfig());
    }

    public ValuationExecutor(ConcurrentExecutorPoolConfig config) {
        super(config);
    }

    private static ConcurrentExecutorPoolConfig concurrentExecutorPoolConfig(){
        return new ConcurrentExecutorPoolConfig() {
            @Override
            public Optional<Integer> corePoolSize() {
                return Optional.of(10);
            }

            @Override
            public Optional<Integer> maxPoolSize() {
                return Optional.of(10);
            }

            @Override
            public Optional<Long> keepAliveTime() {
                return Optional.of(90l);
            }

            @Override
            public Optional<TimeUnit> unit() {
                return Optional.of(TimeUnit.SECONDS);
            }

            @Override
            public Optional<BlockingQueue<Runnable>> workQueue() {
                return Optional.of(new ArrayBlockingQueue<>(50000));
            }

            @Override
            public Optional<ThreadFactory> threadFactory() {
                return Optional.of(new ThreadFactoryBuilder().setNameFormat("newThread" + "%d").build());
            }

            @Override
            public Optional<Integer> ConcurrentSetSize() {
                return Optional.of(50000);
            }
        };
    }
}
