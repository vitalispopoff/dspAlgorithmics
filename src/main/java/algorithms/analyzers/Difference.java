//	@formatter:off

package algorithms.analyzers;

import data.Wave;

public interface Difference {

	static int[][] getDifference(Wave wave){

		int[][]
			input = wave.getChannelSignals();

		int
			channelNumber = input.length,
			signalLength = input[0].length;

		int[][]
			output = new int[channelNumber][signalLength];

		for (int i = 0 ; i < channelNumber ; i++ ){

			int[]
				signal = input[i];

			output[i][0] = signal[0];

			for (int j = 1 ; j < signalLength ; j++){

				int
					sample = signal[j] - signal[j - 1];

				output[i][j] = sample;
			}
		}

		return output;
	}
}

//	@formatter:on