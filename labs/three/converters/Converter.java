package labs.three.converters;

public interface Converter<T> {

	T parse(String fromString);
	
	String format(T fromT);
}
