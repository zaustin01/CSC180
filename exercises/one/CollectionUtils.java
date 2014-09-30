package week1;

import java.util.ArrayList;
import java.util.Collection;

public class CollectionUtils {
	public static int Cardinality(Object obj, Collection coll){
		if (obj == null || coll == null){
			throw new NullPointerException();
		}
		int count = 0;
		for (Object i : coll){
			if (i.equals(obj)){
				count++;
			}
		}
		return count;
	}
	
	public static void main(String[] args) {
		Collection<String> coll = null;
		
		//test 1, using a null collection and a string literal
		try{
			System.out.println(CollectionUtils.Cardinality("Zack", coll));
		} catch (NullPointerException e){
			System.out.println("The collection and/or object was null.");
		}
		
		
		coll = new ArrayList<String>();
		
		//test 2, the collection has been instantiated but the object is null
		try{
			System.out.println(CollectionUtils.Cardinality(null, coll));
		} catch (NullPointerException e){
			System.out.println("The collection and/or object was null.");
		}
		
		//test 3, the collection and object are both valid, but there are no instances of the object in the collection
		System.out.println(CollectionUtils.Cardinality("Zack", coll));
		
		coll.add("Zack");
		coll.add("Josh");
		coll.add("Gus");
		coll.add("Gus");
		
		//test 4, the collection and object are both valid, and there is at least one instance of the object in the collection
		System.out.println(CollectionUtils.Cardinality("Zack", coll));
		
		//test 5, the collection and object are both valid, but the object is not of the type contained in the collection
		System.out.println(CollectionUtils.Cardinality(coll, coll));

	}

}
