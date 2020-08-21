//	@formatter:off

package algorithms.processors;

import static algorithms.analyzers.Peaks.*;

public interface Normalization {

	static int[] normalize(int[] signal, int bitDepth){

		int[]
			limits = getLimits(signal, bitDepth);

		int
			peak = Math.max(Math.abs(limits[0]), limits[1]),
			limit =
				peak == limits[1]
				? limits[3]
				: Math.abs(limits[2]);

		double
			normalization = ((double) limit) / ((double) peak);

		for (int i = 0; i < signal.length; i++)
			signal[i] *= normalization;

		return signal;
	}
}

//	@formatter:on