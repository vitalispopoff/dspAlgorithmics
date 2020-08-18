//	@formatter:off

package algorithms.processors.metaProcessors;

import data.Wave;

public abstract class ChannelSplitter {

	public static int[][] splitChannels(Wave wave){

		int
			numberOfChannels = wave.header.numberOfChannels,
			dataBlockLength = wave.header.dataBlockLength,
			sampleFrameSize = wave.header.sampleFrameSize,
			signalLength = dataBlockLength / sampleFrameSize;

		int[][]
			channels = new int[numberOfChannels][signalLength];

		for(int i = 0; i < dataBlockLength ; i++ ){

			int
				channel = i % numberOfChannels,
				index = i / numberOfChannels;

			channels[channel][index] = wave.signal[i];
		}

		return channels;
	}
}

//	@formatter:om