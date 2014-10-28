package labs.three.transformers;

public interface Transformer<K, T> {
	public T transform(K element);
}
