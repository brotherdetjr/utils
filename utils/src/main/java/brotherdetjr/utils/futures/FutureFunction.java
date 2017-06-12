package brotherdetjr.utils.futures;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@FunctionalInterface
public interface FutureFunction<A, B> extends Function<A, CompletableFuture<B>> {
}
