package data.structure.signal;

public interface Signalable {


	byte[] getSource(int bitsPerSample);

	Channeling getStrip(int index);
}