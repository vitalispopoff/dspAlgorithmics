package data.structure.signal;

public interface Signaling {

	static Signaling newInstance(byte[] source, int blockAlign, int numberOfChannels){

		return Signal.instanceOf(source, blockAlign, numberOfChannels);
	}

	Channeling getChannel(int index);

	byte[] getSource(int bitsPerSample);

}