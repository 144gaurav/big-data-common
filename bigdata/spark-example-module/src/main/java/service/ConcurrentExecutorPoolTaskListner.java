package service;

public interface ConcurrentExecutorPoolTaskListner <T,W>{

    public void onSuccess(T task, W taskResult);
    public void onError(T task, Throwable throwable);
}
