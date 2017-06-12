package brotherdetjr.utils.futures;

import org.slf4j.Logger;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static com.google.common.base.Throwables.getStackTraceAsString;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.slf4j.LoggerFactory.getLogger;

public class Safely {

	private static final Logger log = getLogger(Safely.class);

	private Safely() {
		throw new AssertionError();
	}

	public static <A> BiConsumer<Throwable, A> noFailureExpectedHere() {
		return (ex, x) -> log.error("No failure is expected here, however it has occurred. Argument: {}. Cause: {}",
			x, getStackTraceAsString(ex));
	}

	public static <A, B> FutureFunction<Optional<A>, Optional<B>> immediately(
		Function<A, B> func, BiConsumer<Throwable, A> failureHandler) {
		return safely(x -> completedFuture(func.apply(x)), failureHandler);
	}

	public static <A, B> FutureFunction<Optional<A>, Optional<B>> safely(
		Function<A, CompletableFuture<B>> func, BiConsumer<Throwable, A> failureHandler) {
		return x -> {
			if (x.isPresent()) {
				try {
					return func.apply(x.get()).handle((value, ex) -> {
						if (ex == null) {
							return ofNullable(value);
						} else {
							failureHandler.accept(ex, x.get());
							return empty();
						}
					});
				} catch (Exception ex) {
					failureHandler.accept(ex, x.get());
					return completedFuture(empty());
				}
			} else {
				return completedFuture(empty());
			}
		};
	}

}
