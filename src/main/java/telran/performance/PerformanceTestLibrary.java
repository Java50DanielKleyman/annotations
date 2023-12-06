package telran.performance;

import java.lang.reflect.Method;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.stream.IntStream;

public class PerformanceTestLibrary {
	public static void runTests(Object testObj) throws Exception {
		Class<?> clazz = testObj.getClass();
		runBeforeAllMethods(clazz);
		Arrays.stream(clazz.getDeclaredMethods()).filter(m -> m.isAnnotationPresent(PerformanceTest.class))
				.forEach(m -> {
					try {
						runPerformanceTest(m, testObj);
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
	}

	private static void runPerformanceTest(Method m, Object testObj) throws Exception {
		String title = getTitle(m);
		Instant start = Instant.now();
		PerformanceTest performanceTest = m.getAnnotation(PerformanceTest.class);
		int nRuns = performanceTest.nRuns();
		for (int i = 0; i < nRuns; i++) {
			m.invoke(testObj);
		}
		System.out.printf("test %s; running time: %d\n", title, ChronoUnit.MILLIS.between(start, Instant.now()));

	}

	private static String getTitle(Method method) {

		return method.isAnnotationPresent(DisplayName.class) ? method.getAnnotation(DisplayName.class).name()
				: method.getName();
	}

	private static void runBeforeAllMethods(Class<?> clazz) throws Exception {
		for (Method method : clazz.getDeclaredMethods()) {
			if (method.isAnnotationPresent(BeforeAll.class)) {
				method.setAccessible(true);
				method.invoke(null);
			}

		}

	}
}