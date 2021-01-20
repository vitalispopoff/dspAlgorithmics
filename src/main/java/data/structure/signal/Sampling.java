package data.structure.signal;

public interface Sampling {

	static Sampling instanceOf(int i){

		return new Sample(i);
	}

	int getValue();
	void setValue(int v);

	void setNext(Sampling s);
	Sampling getNext();


	static Sampling min(Sampling s1, Sampling s2){

		return (s2.getValue() - s1.getValue() > 0 ? s1 : s2 );
	}

	static Sampling max(Sampling s1, Sampling s2){

		return (s2.getValue() - s1.getValue() > 0 ? s2 : s1 );
	}

}
