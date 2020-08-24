//	@formatter:off

package algorithms.generators;

import java.util.Random;

public interface Generators {

	static int[] generateNoise(int length, int bitDepth){

		int[]
			result = new int[length];

		Random
			random = new Random();

		for (int index = 1; index < length - 1; index++)	// first and last bits of signal should be 0

			result[index] = getSignedGaussianInt(bitDepth);

		return result;
	}



	static int getSignedGaussianInt(int bitDepth){

		Random
			random = new Random();

		int
			bits = 1 << bitDepth,
			result = 0;

		for (int i = 0; i < 12; i++)

			result += random.nextInt(bits);

		return (result / 12) - (bits >> 1);
	}
}

//	@formatter:on