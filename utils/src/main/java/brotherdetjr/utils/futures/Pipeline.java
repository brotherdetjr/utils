package brotherdetjr.utils.futures;

import org.slf4j.Logger;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static brotherdetjr.utils.futures.Safely.safely;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.slf4j.LoggerFactory.getLogger;

public class Pipeline<T extends Phased> {
	private static final Logger log = getLogger(Pipeline.class);
	private final CompletableFuture<Optional<T>> future;
	private final BiConsumer<Throwable, T> failureHandler;

	private Pipeline(CompletableFuture<Optional<T>> future, BiConsumer<Throwable, T> failureHandler) {
		this.future = future;
		this.failureHandler = (ex, v) -> {
			log.error("Failed to {}. Context: {}.", v.phase(), v);
			failureHandler.accept(ex, v);
		};
	}

	public static <T extends Phased> Pipeline<T> of(T initialValue) {
		return new Pipeline<>(completedFuture(Optional.of(initialValue)), (ex, v) -> {});
	}

	public Pipeline<T> then(Function<T, CompletableFuture<T>> func) {
		return new Pipeline<>(future.thenCompose(safely(func, failureHandler)), failureHandler);
	}

	public Pipeline<T> immediately(Function<T, T> func) {
		return new Pipeline<>(future.thenCompose(Safely.immediately(func, failureHandler)), failureHandler);
	}

	public Pipeline<T> onFail(BiConsumer<Throwable, T> failureHandler) {
		return new Pipeline<>(future, failureHandler);
	}

}
