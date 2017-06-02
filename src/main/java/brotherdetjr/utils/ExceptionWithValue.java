package brotherdetjr.utils;

import lombok.Getter;

public class ExceptionWithValue extends RuntimeException {

    @Getter
	private final Object value;

    public ExceptionWithValue(Object value, Throwable cause) {
        super(cause);
        this.value = value;
    }

    public ExceptionWithValue(Object value) {
        this.value = value;
    }

    public ExceptionWithValue() {
        this.value = null;
    }
}
