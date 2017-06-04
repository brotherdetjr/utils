package brotherdetjr.utils.vertx;

import io.vertx.core.Vertx;

import java.util.concurrent.Executor;

import static io.vertx.core.Vertx.vertx;

public class VertxUtils {

	private VertxUtils() {
		throw new AssertionError();
	}

	/**
	 * Returns {@link Executor} instance for given {@link Vertx} instance.
	 * @param vertx specifies {@link Vertx} instance.
	 * @return {@link Executor} instance.
	 */
	public static Executor vertxExecutor(Vertx vertx) {
		return command -> vertx.getOrCreateContext().runOnContext(event -> command.run());
	}

	/**
	 * Returns {@link Executor} instance for the default {@link Vertx} instance ({@link Vertx#vertx()}).
	 * @return {@link Executor} instance.
	 */
	public static Executor vertxExecutor() {
		return vertxExecutor(vertx());
	}

}
