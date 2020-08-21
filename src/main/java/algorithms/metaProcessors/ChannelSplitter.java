//	@formatter:off

package algorithms.metaProcessors;

import data.WaveHeader;

public abstract class ChannelSplitter {

	public static int[][] splitChannels(WaveHeader header, int[] signal){

		int
			numberOfInputs = header.numberOfChannels,
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