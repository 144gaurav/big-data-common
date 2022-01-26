package reader;

import supplier.Supplier;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Reader<T> extends Supplier<T> {
    @Override
    T get();

    default boolean isPresent(){
        return true;
    }

//    default <V> Reader<V> andThen(Function<T, V> after){
//        return Supplier.super.andThen(after::apply)::get;
//    }

//    default void andConsume(Consumer<T> after){
//        Objects.requireNonNull(after);
//        after.accept(this.get());
//    }


}
