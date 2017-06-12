package brotherdetjr.utils.futures;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface FailureHandler<T> extends BiConsumer<Throwable, T> {
}
