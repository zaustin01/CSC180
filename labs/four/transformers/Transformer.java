package labs.four.transformers;

public interface Transformer<K, T> {
	public T transform(K element);
}
