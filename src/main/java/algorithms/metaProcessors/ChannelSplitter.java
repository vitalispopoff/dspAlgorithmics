//	@formatter:off

package algorithms.metaProcessors;

import data.Wave;

public abstract class ChannelSplitter {

	public static int[][] splitChannels(Wave wave){

		int[]
			signal = {};

		int
			numberOfInputs = wave.header.numberOfChannels,
			outputLength = signal.length / numberOfInputs;

		int[][]
			outputs = new int[numberOfInputs][outputLength];

		for (int i = 0; i < signal.length ; i++){

			int
				channel = i % numberOfInputs,
				index = i / numberOfInputs;

			outputs[channel][index] = signal[i];
		}

		return outputs;
	}

	public static int[][] splitChannels(Wave wave, int[]signal){

		int
				numberOfInputs = wave.header.numberOfChannels,
				outputLength = signal.length / numberOfInputs;

		int[][]
				outputs = new int[numberOfInputs][outputLength];

		for (int i = 0; i < signal.length ; i++){

			int
					channel = i % numberOfInputs,
					index = i / numberOfInputs;

			outputs[channel][index] = signal[i];
		}

		return outputs;
	}
}

//	@formatter:om