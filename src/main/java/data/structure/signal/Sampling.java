package data.structure.signal;

public interface Sampling {


	static Sampling newInstance(int i){

		return new Sample(i);
	}


	int getValue();

	void setValue(int v);


	void setNext(Sampling s);

	Sampling getNext();

}