package labs.two;

public interface Transformer<K, T> {
	public T transform(K element);
}
