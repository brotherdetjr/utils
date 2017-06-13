package brotherdetjr.utils.futures;

public class PhasedImpl<T extends PhasedImpl<T>> implements Phased {
	private String phase;

	public PhasedImpl(String phase) {
		this.phase = phase;
	}

	@Override
	public String phase() {
		return phase;
	}

	@SuppressWarnings("unchecked")
	public T phase(String phase) {
		this.phase = phase;
		return (T) this;
	}
}
