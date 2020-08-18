//	@formatter:off

package algorithms.processors;

public abstract class ConvolutionMachine {

	public static int[] convolutionMachine(int[] signal, int[] impulseResponse){

		int[]
			reversedImpulse = reverseSignal(impulseResponse),
			result = new int[signal.length + impulseResponse.length - 1];

		for (int i = 0; i < result.length ; i++){

			int
				cache = 0;

			for(int j = 0; j < impulseResponse.length && j <= i ; j++){

				int
					signalIndex = i - j;

				if (signalIndex >= 0 && signalIndex < signal.length)
					cache += (signal[i - j] * impulseResponse[j]);
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

		return result;
	}
}

//	@formatter:on