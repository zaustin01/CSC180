package exercisefour;

import java.util.ArrayList;
import java.util.Collection;

import exerciseone.Transformer;

public class CollectionUtils {

	public static int cardinality(Object obj, Collection coll) {
		if (obj == null || coll == null) {
			throw new NullPointerException();
		}
		int count = 0;
		for (Object i : coll) {
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
