package data.structure.signal;

public interface Channeling {

	static Channeling newInstance(byte[] source, int blockAlign, int numberOfChannels){

		return new Channels(source, blockAlign, numberOfChannels);
	}

	DataPreviewStructure getChannel(int index);

	byte[] getSource(int bitsPerSample);

}