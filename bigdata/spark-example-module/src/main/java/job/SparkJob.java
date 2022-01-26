package job;

import reader.Reader;
import supplier.Supplier;
import writer.Writer;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Consumer;

public interface SparkJob<T,R> extends Runnable, Supplier<R>, WriteOnlySparkJob<T , R> , Serializable {

    Reader<T> reader();
    Writer<R> writer();
    R apply(T t);
    default R get(){
        return apply(reader().get());
    }

    default  void run(){
        try{
            reader().andThen(this::apply).andConsume(writer());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
