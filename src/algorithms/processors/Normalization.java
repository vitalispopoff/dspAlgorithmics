package algorithms.processors;

import static algorithms.analyzers.Peaks.*;

public abstract class Normalization {

	public static int[] normalize(int[] signal, int bitDepth){

		int[]
				limits = getLimits(signal, bitDepth),
				result = new int[signal.length];

		int
				peak = Math.abs(limits[0]) > limits[1]
				? limits[1]
				: limits[0],
				limit = peak == limits[1]
						? limits[3]
						: limits[2];

		double
				normalization = ((double) limit) / ((double) peak);

		for (int i = 0; i < signal.length; i++)
			result[i] = (int) ((double) signal[i] * normalization);

		return result;
	}
}