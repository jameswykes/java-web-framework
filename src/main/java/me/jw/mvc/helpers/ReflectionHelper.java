package me.jw.mvc.helpers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReflectionHelper { 
	@SuppressWarnings("unchecked")
	public static <T> List<Field> getDeclaredFields (List<Field> fields, Class<T> type) { 
		for (Field f : type.getDeclaredFields ()) { 
			if (!f.getName().equals("this$0")) { 
				fields.add (f); 
			} 
		} 
		if (type.getSuperclass() != null && type.getSuperclass() != Object.class) { 
			return getDeclaredFields (fields, (Class<T>) type.getSuperclass()); 
		} 
		return fields; 
	} 

	public static <T> Field getField (String property, Class<T> type) { 
		Field field = null; 
		for (Field f : getDeclaredFields(new ArrayList<Field> (), type)) { 
			if (f.getName ().equals (property)) {
				field = f; 
				break; 
			} 
		} 
		return field; 
	} 

	public static <T> HashMap<String, String> toStringMap (T model, Class<T> type) { 
		HashMap<String, String> output = new HashMap<String, String> (); 

		List<Field> temp = new ArrayList<Field> (); 
		List<Field> fields = ReflectionHelper.getDeclaredFields (temp, type); 

		for (Field field : fields) { 
			try { 
				field.setAccessible(true);
				output.put(field.getName(), (String) field.get(model)); 
			} catch (Exception ex) { 
				ex.printStackTrace(); 
			} 
		} 

		return output; 
	} 
} 
