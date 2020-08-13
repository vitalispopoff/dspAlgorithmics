package algorithms.generators;

import java.util.Random;

public class Generators {

	public static int[] generateNoise( int size, int bitDepth){

		int[]
				result = new int[size];
		Random
				random = new Random();

		for (int index = 0; index < size ; index++){
			result[index] = getSignedGaussianInt(bitDepth);
		}

		return result;
	}

	private static int getSignedGaussianInt(int bitDepth){

		Random
				random = new Random();
		int
				bits = (int) Math.pow(2, bitDepth),
				result = 0;

		for (int i = 0; i < 12 ; i++)
			result += random.nextInt(bits);

		return (result/12) - (bits >> 1);
	}
}