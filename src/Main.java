import java.util.*;

public class Main {

	public static int getSignedGaussianInt(int bitDepth){

		Random
			random = new Random();
		int
			result = 0;

		for (int i = 0; i < 12 ; i++)
			result += random.nextInt(bitDepth);

		return (result/12) - (bitDepth >> 1);
	}

	public static int[] generateNoise( int size, int bitDepth){

		int[]
			result = new int[size];
			Random random = new Random();

		for (int index = 0; index < size ; index++){

			result[index] = getSignedGaussianInt(bitDepth);
		}

		return result;
	}

	public static int[] convolutionMachine(int[] signal, int[] impulseResponse){

		int[]
//			reversedImpulse = reverseSignal(impulseResponse),
			result = new int[signal.length + impulseResponse.length - 1];

		for (int i = 0; i < result.length ; i++){

			int
				cache = 0;

			for(int j = 0; j < impulseResponse.length ; j++){

				int
					signalIndex = i - j;

				if (signalIndex >= 0 && signalIndex < signal.length)

					cache += signal[i - j] * impulseResponse[j];

			}

			result[i] = cache;
		}

		return result;
	}

	private static int[] reverseSignal(int[] signal) {

		int[]
			result = new int[signal.length];

		for(int i = 0; i < signal.length; i++)
			result[i] = signal[signal.length - 1 - i];

		System.out.println(Arrays.toString(result));
		return result;
	}


//	--------------------------------------------------------------------------------------------------------------------

	public static void main(String[] args) {

		int
			impulseLength = 3,
			signalLength = 6,
			bits = (int) Math.pow(2, 16);
		int[]
			impulse = generateNoise(impulseLength, bits),
			signal= generateNoise(signalLength, bits),
			result = new int[impulseLength + signalLength -1];

		int[]
			imp = {-1, 1},
			sig = {1, 2, 3};

		System.out.println(Arrays.toString(convolutionMachine(sig, imp)));




	}
}