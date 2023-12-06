package telran.reflect;

import java.lang.reflect.Field;
import java.util.*;
public class SchemaProperties {
public static void displayFieldProperties(Object obj) throws Exception{
	Field idField = null;
	List<Field> indexFields = new ArrayList<>();
	for(Field field: obj.getClass().getDeclaredFields()) {
		if(field.isAnnotationPresent(Id.class)) {
			if (idField != null) {
				throw new IllegalStateException("Field Id must be one");
			}
			idField = field;
		}
		if(field.isAnnotationPresent(Index.class)) {
			indexFields.add(field);
		}
	}
	if (idField == null) {
		throw new IllegalStateException("No field Id found");
	}
	System.out.println("id field is " + idField.getName());
	System.out.println("index fields follow:");
	indexFields.forEach(field -> System.out.print(field.getName() + " "));
	System.out.println("\n");
}
}