package data.structure.signal;

public interface Audio {

	static Audio newInstance(byte[] source, int blockAlign, int numberOfChannels){

		return new AudioData(source, blockAlign, numberOfChannels);
	}

	SignalTree getChannel(int index);

	byte[] getSource(int bitsPerSample);

}