package supplier;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Supplier<R> extends java.util.function.Supplier<R> {
    default <V> Supplier<V> andThen(Function<R, V> after){
        Objects.requireNonNull(after);
        return () -> {
            System.out.println("Inside Supplier andThen");
          return after.apply(this.get());
        };
    }

    default void andConsume(Consumer<R> after){
        Objects.requireNonNull(after);
            System.out.println("Inside Supplier andConsume");
            after.accept(this.get());
    }
 }
