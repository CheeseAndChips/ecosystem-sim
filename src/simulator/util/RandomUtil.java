package simulator.util;

import java.util.Random;

public class RandomUtil {
	public static double generateDoubleInRange(double a, double b, Random rng) {
		return a + rng.nextDouble() * (b - a);
	}
}
