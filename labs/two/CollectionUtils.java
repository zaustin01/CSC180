package labs.two;

import java.util.ArrayList;
import java.util.Collection;


public class CollectionUtils {

	public static <E> int cardinality(Object obj, Collection<E> coll) {
		if (obj == null || coll == null) {
			throw new NullPointerException();
		}
		int count = 0;
		for (E i : coll) {
			if (i.equals(obj)) {
				count++;
			}
		}
		return count;
	}

	public static <K, T> Collection<T> transformList(Collection<K> coll,
			Transformer<K, T> trans) {
		Collection<T> list = new ArrayList<T>();

		for (K element : coll) {
			list.add(trans.transform(element));
		}
		return list;
	}
	
	public static <T> Collection<T> filter(Collection<T> coll, Predicate<T> predicate){
		Collection<T> filteredList = new ArrayList<T>();
		for (T item : coll){
			if(predicate.evaluate(item)){
				filteredList.add(item);
			}
		}
		return filteredList;
		
	}

}
