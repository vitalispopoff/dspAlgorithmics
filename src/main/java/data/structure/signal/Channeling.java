package data.structure.signal;

import data.structure.header.WaveHeader;

public interface Channeling {

	static Channeling newInstance(byte[] source, int blockAlign, int numberOfChannels){

		return new Channels(source, blockAlign, numberOfChannels);
	}


	static Channeling newInstance(AudioData audioData, WaveHeader header){

		return null;
	}



	DataPreviewStructure getChannel(int index);

	byte[] getSource(int bitsPerSample);

}