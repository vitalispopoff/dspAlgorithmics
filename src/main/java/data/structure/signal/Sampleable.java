package data.structure.signal;

public interface Sampleable {


	static Sampleable instanceOf(int i){

		return new Sample(i);
	}


	int getValue();

	void setValue(int v);


	void setNext(Sampleable s);

	Sampleable getNext();

}