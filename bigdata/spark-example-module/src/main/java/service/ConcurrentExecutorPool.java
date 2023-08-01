package service;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;
import java.util.function.Function;

public class ConcurrentExecutorPool<T, W> {

    private final ThreadPoolExecutor threadPoolExecutor;
    private final ConcurrentExecutorPoolConfig config;
    private final ConcurrentSet<T> concurrentSet;
    private final ConcurrentExecutorPoolTaskListner<T, W> taskListner;
    private int DEFAULT_CONCURRENTSET_SIZE = 10000;
    private BlockingQueue<Runnable> DEFAULT_BLOCKING_QUEUE = new ArrayBlockingQueue<>(DEFAULT_CONCURRENTSET_SIZE);

    public ConcurrentExecutorPool( ConcurrentExecutorPoolConfig config) {
        this.threadPoolExecutor = initThreadPool(config);
        this.config = config;
        this.concurrentSet = new ConcurrentHashSet<>(DEFAULT_CONCURRENTSET_SIZE);
        this.taskListner = new DefaultTaskListner<>();
    }


    public void submit(final T task, final Function<T, W> job) {
        if (concurrentSet.add(task)) {
            CompletableFuture.supplyAsync(() -> job.apply(task), threadPoolExecutor)
                    .thenAccept(w -> {
                        System.out.println("Executor callback succeeded : " + task);
                        concurrentSet.remove(task);
                        taskListner.onSuccess(task, w);
                    }).handle((msg, ex) -> {
                        if (ex != null) {
                            System.out.println("executor task error : " + task);
                            concurrentSet.remove(task);
                            taskListner.onError(task, ex);
                            return "task Exception";
                        } else {
                            return msg;
                        }
                    });
        }
    }


    private ThreadPoolExecutor initThreadPool(final ConcurrentExecutorPoolConfig config){
        int corePoolSize = config.corePoolSize().orElse(Runtime.getRuntime().availableProcessors());
        ThreadPoolExecutor threadPoolExecutor1 = new ThreadPoolExecutor(
                corePoolSize,
                config.maxPoolSize().orElse(corePoolSize),
                config.keepAliveTime().orElse(60l),
                config.unit().orElse(TimeUnit.SECONDS),
                config.workQueue().orElse(DEFAULT_BLOCKING_QUEUE),
                config.threadFactory().orElse(new ThreadFactoryBuilder().setNameFormat("executor-service" + "%d").build())

        );
        return threadPoolExecutor1;
    }

    public void close(int time, TimeUnit timeUnit){
        shutdown(threadPoolExecutor,time,timeUnit);
        while (true){
            if(threadPoolExecutor.isTerminated()){
                System.out.println("Stop the spark context if needed");
                try {
                    Thread.sleep(60l);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void shutdown(ThreadPoolExecutor executorService, int timeOutSeconds, TimeUnit timeUnit){
        if(executorService !=null){
            executorService.shutdown();
            try {
                executorService.awaitTermination(timeOutSeconds,timeUnit);
            } catch (InterruptedException e) {
                System.out.println("Shutdown");
                Thread.interrupted();
            }
        }
    }

    private class DefaultTaskListner<T,W> implements ConcurrentExecutorPoolTaskListner<T,W>{

        @Override
        public void onSuccess(T task, W taskResult) {
            System.out.println("Task Completed");
        }

        @Override
        public void onError(T task, Throwable throwable) {
            System.out.println("Task failed");

        }
    }
}
