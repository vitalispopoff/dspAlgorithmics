package algorithms.analyzers;

import static java.lang.Integer.*;

public interface Peaks {

	static int[] getLimits(int[] signal, int bitDepth){

		int
			max = 1 << (bitDepth - 1);

		int[]
			result = {max - 1, -max, -max, max - 1};

		for (int sample : signal) {

			if (sample < result[0]) result[0] = sample;

			if (sample > result[1]) result[1] = sample;
		}
		
		return result;
	}

	static int getActualBitDepth(int[] signal){

		int
			infimum = MAX_VALUE,
			supremum = MIN_VALUE;

		for (int sample : signal){

			if (sample > supremum) supremum = sample;

			if (sample < infimum) infimum = sample;
		}

		int
			maximum = Math.max(Math.abs(infimum), supremum),
			msB = Integer.numberOfLeadingZeros(maximum);

		return 32 - msB;
	}
}