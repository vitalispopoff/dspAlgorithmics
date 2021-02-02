package data.structure.signal;

public interface Channeling {

	static Channeling instanceOf(){

		return new Channel();
	}

	int size();

	Sampleable get(int i);

	void addSample(Sampleable s);

}