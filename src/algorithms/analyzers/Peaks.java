package algorithms.analyzers;

public class Peaks {

	public static int[] getLimits(int[] signal, int bitDepth){

		int
				max = ((int) (Math.pow(2, bitDepth))) >> 1;

		int[]
				result = {max - 1, -max, -max, max - 1};

		for (int sample : signal) {
			if (result[0] > sample) result[0] = sample;
			if (result[1] < sample) result[1] = sample;
		}

		return result;
	}
}
