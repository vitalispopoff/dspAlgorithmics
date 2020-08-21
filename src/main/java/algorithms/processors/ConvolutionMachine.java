//	@formatter:off

package algorithms.processors;

public abstract class ConvolutionMachine {

	public static int[] convolutionMachine(int[] signal, int[] impulse){

		int[]
			reversedImpulse = reverseSignal(impulse);

		return correlationMachine(signal, reversedImpulse);
	}

	public static int[] correlationMachine(int[] signal, int[] impulse){

		int
			resultLength = 	signal.length + impulse.length - 1;

		int[]
			result = new int[resultLength];

		for (int i = 0; i < result.length ; i++){

			int
				cache = 0;

			for(int j = 0; j < impulse.length && j <= i ; j++){

				int
					signalIndex = i - j;

				if (signalIndex >= 0 && signalIndex < signal.length){

					int
						sample = signal[signalIndex] * impulse[j];

					cache += sample;
				}
			}

			result[i] = cache;
		}

		return result;
	}

	private static int[] reverseSignal(int[] signal) {

		int
			length = signal.length;

		int[]
			result = new int[length];

		for (int i = 0; i < length; i++)

			result[i] = signal[length - 1 - i];

		return result;
	}
}

//	@formatter:on