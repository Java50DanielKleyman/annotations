package telran.reflect;

import java.lang.reflect.Field;

public class SchemaProperties {
	public static void displayFieldProperties(Object obj) throws Exception {
		Field[] fields = obj.getClass().getDeclaredFields();
		Field iddField = getIdField(fields);
		System.out.println("Person normal id: " + iddField.get(obj));
		for (int i = 1; i <= fields.length; i++) {
			if (fields[i - 1].isAnnotationPresent(Index.class)) {
				fields[i - 1].setAccessible(true);
				System.out.println("Person normal field " + i + ": " + fields[i - 1].get(obj));
			}
		}

	}

	private static Field getIdField(Field[] fields) throws Exception {
		Field idField = null;
		for (Field field : fields) {
			if (idField == null && field.isAnnotationPresent(Id.class)) {
				idField = field;
				idField.setAccessible(true);
			} else if (idField != null && field.isAnnotationPresent(Id.class)) {
				throw new IllegalStateException("Field Id must be one");
			}
		}
		if (idField == null) {
			throw new IllegalStateException("No field Id found");
		}
		return idField;
	}
}