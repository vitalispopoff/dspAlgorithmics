//	@formatter:off

package algorithms.metaProcessors;

import data.Wave;

public abstract class ChannelSplitter {

	public static int[][] splitChannels(Wave wave, int[]signal){

		int
			numberOfChannels = wave.header.numberOfChannels,
			channelLength = signal.length / numberOfChannels;

		int[][]
			channels = new int[numberOfChannels][channelLength];

		for (int i = 0; i < signal.length ; i++){

			int
				channel = i % numberOfChannels,
				index = i / numberOfChannels;

			channels[channel][index] = signal[i];
		}

		return channels;
	}
}

//	@formatter:om