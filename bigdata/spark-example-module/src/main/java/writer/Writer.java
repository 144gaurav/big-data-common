package writer;

import java.util.Objects;
import java.util.function.Consumer;

public interface Writer<T> extends Consumer<T> {
    void accept(T t);

//    @Override
//    default Writer<T> andThen(Consumer<? super T> after){
//        Objects.requireNonNull(after);
//        return (T t) -> { accept(t); after.accept(t);};
//    }
}
