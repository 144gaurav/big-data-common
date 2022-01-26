package job;

import writer.Writer;

import java.util.function.Consumer;

public interface WriteOnlySparkJob<T,R> extends Consumer<T> {
    Writer<R> writer();
    R apply(T t);

    default void accept(T t){
        writer().accept(apply(t));
    }
}
