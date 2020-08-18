package algorithms.processors;

public abstract class ChannelSplitter {

	public static int[][] splitChannels(Wave wave){

		int
			signalLength = wave.dataBlockLength / wave.sampleFrameSize;

		int[][]
			channels = new int[wave.numberOfChannels][signalLength];

		for(int i = 0 ; i < wave.dataBlockLength ; i++ ){

			int
				channel = i % wave.numberOfChannels,
				index = i / wave.numberOfChannels;

			channels[channel][index] = wave.signal[i];
		}

		return channels;
	}





}
