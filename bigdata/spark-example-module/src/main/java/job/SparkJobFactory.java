package job;

import service.SparkJobContext;

public interface SparkJobFactory<C, T, R> {
    SparkJob<T, R> getJob(C command, SparkJobContext sparkJobContext);
}
