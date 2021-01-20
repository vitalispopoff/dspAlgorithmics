package data.structure.signal;

public interface Sampleable {

	static Sampleable instanceOf(int i){

		return new Sample(i);
	}

	int getValue();
	void setValue(int v);

	void setNext(Sampleable s);
	Sampleable getNext();


	static Sampleable min(Sampleable s1, Sampleable s2){

		return (s2.getValue() - s1.getValue() > 0 ? s1 : s2 );
	}

	static Sampleable max(Sampleable s1, Sampleable s2){

		return (s2.getValue() - s1.getValue() > 0 ? s2 : s1 );
	}

}
