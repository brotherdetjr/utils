package brotherdetjr.utils;

import java.io.InputStream;
import java.util.function.Function;

import static java.lang.Thread.currentThread;
import static java.util.Objects.requireNonNull;

public class Utils {

	private Utils() {
		throw new AssertionError();
	}

	/**
	 * Throws {@link NullPointerException} if any of the args is null.
	 * @param objects args to check.
	 */
	@SuppressWarnings("ResultOfMethodCallIgnored")
	public static void checkNotNull(Object ... objects) {
		for (Object object : objects) {
			requireNonNull(object);
		}
	}

	/**
	 * Returns value of func if func(clazz) != null otherwise it goes up the hierarchy.
	 * @param clazz specifies a class to start a search with.
	 * @param func specifies a function to check a value for.
	 * @param <V> specifies value type.
	 * @return the first non-null value of func(clazz) or null if there's no non-null value
	 * for the given class or for any of its superclasses.
	 */
	public static <V> V searchInHierarchy(Class<?> clazz, Function<Class<?>, V> func) {
		while (clazz != null) {
			V value = func.apply(clazz);
			if (value != null) {
				return value;
			}
			clazz = clazz.getSuperclass();
		}
		return null;
	}

	/**
	 * Returns resource's {@link InputStream}.
	 * @param name specifies the name of the resource to give {@link InputStream} for.
	 * @return resource's {@link InputStream}.
	 */
	public static InputStream resourceAsStream(String name) {
		return currentThread().getContextClassLoader().getResourceAsStream(name);
	}
}
