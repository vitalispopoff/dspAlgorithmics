package data.structure.signal;

import data.structure.header.WaveHeader;

public interface Channeling {



	static Channeling newInstance(AudioData audioData, WaveHeader header){

		return null;
	}

	static Channeling newInstance(byte[] source, int blockAlign, int numberOfChannels){

		return new Channels(source, blockAlign, numberOfChannels);
	}



	default int size(){

		return 1;
	}



	SamplePyramid getChannel(int index);

	byte[] getSource(int bitsPerSample);
}