package labs.four;

import labs.four.exceptions.UnknownClassException;

public class Padding {
	
	private char NPC = (char)31; //NPC stands for Non-Printable Character
	
	public String pad (Object obj, int len){
		if (obj == null){
			obj = "";
		}
		String s = obj.toString();
		if (s.length() > len){
			s = s.substring(0,len);
		} else if (s.length() < len) {
			StringBuilder sb = new StringBuilder(s);
			for (int i = 0; i < (len - s.length()); i++){
				sb.append(NPC);
			}
			s = sb.toString();
		}
		return s;
	}
	
	public Object unpad (String str){
		for (int i = 0; i <32 ; i++){
			str = str.replaceAll( String.valueOf((char)i), "");
		}
		Object toReturn = null;
		try {
			if (str.contains(".")){
				toReturn = Double.parseDouble(str);
			} else if (str.length() > 9){
				toReturn = Long.parseLong(str);
			} else {
				toReturn = Integer.parseInt(str);
			}
		} catch (NumberFormatException e){
			toReturn = str;
		}
		return toReturn;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T unpad (String str, Class<T> clazz){
		for (int i = 0; i <32 ; i++){
			str = str.replaceAll( String.valueOf((char)i), "");
		}
		Object toReturn = null;
		if (clazz.equals(Double.class)){
			toReturn = Double.parseDouble(str);
		} else if (clazz.equals(Long.class)){
			toReturn = Long.parseLong(str);
		} else if (clazz.equals(Integer.class)){
			toReturn = Integer.parseInt(str);
		} else if (clazz.equals(String.class)) {
			toReturn = str;
		} else {
			throw new UnknownClassException();
		}
		return (T) toReturn;
	}

}
