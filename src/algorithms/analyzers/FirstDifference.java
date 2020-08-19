//	@formatter:off

package algorithms.analyzers;

import data.Wave;

public class FirstDifference {

	public Wave
		wave;

	public int[][]
		channelSignals;

	public FirstDifference(Wave wave){

		this.wave = wave;
	}

	void setChannelSignals(){

		int[][]
			input = wave.getChannelSignals();

		int
			channelNumber = input.length,
			signalLength = input[0].length;

		channelSignals = new int[channelNumber][signalLength];

		for(int i = 0 ; i < channelNumber ; i++ ){

			int[]
				signal = input[i];

			channelSignals[i][0] = signal[0];

			for(int j = 1 ; j < signalLength ; j++){

				int
					sample = signal[j] - signal[j - 1];

				channelSignals[i][j] = sample;
			}
		}
	}
}

//	@formatter:on