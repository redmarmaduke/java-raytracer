package raytracer;

/**
 * Mutable class
 * Allows for passing mutable arguments to functions
 * 
 * @author Manuel Nunes
 *
 * @param <T> 
 */

public class Mutable<T> {
	private T value;

	public Mutable(T value) {
		this.value = value;
	}

	public void set(T value) {
		this.value = value;
	}

	public T get() {
		return value;
	}

	public static <T> Mutable<T> valueOf(T value) {
		return new Mutable<T>(value);
	}
}
