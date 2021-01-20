package data.structure.signal;

public interface Stripable {

	static Stripable instanceOf(){

		return new Strip();
	}

	int size();

	Sampleable get(int i);

	void addSample(Sampleable s);

}