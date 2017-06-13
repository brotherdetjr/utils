package brotherdetjr.utils.futures;

import org.slf4j.Logger;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import static org.slf4j.LoggerFactory.getLogger;

public class SafePipeline<T extends Phased> {
	private static final Logger log = getLogger(SafePipeline.class);

	private final FailureHandler<T> failureHandler;

	public SafePipeline(FailureHandler<T> failureHandler) {
		this.failureHandler = (ex, v) -> {
			log.error("Failed to {}. Context: {}.", v.phase(), v);
			failureHandler.accept(ex, v);
		};
	}

	public FutureFunction<Optional<T>, Optional<T>> safely(Function<T, CompletableFuture<T>> func) {
		return Safely.safely(func, failureHandler);
	}

	public FutureFunction<Optional<T>, Optional<T>> immediately(Function<T, T> func) {
		return Safely.immediately(func, failureHandler);
	}
}
