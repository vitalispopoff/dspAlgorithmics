package data.structure.signal;

public class Sample_0 implements Sampling {


	int
		value = 0;

	Sampling
		next = this;


	public Sample_0(int v){

		value = v;
	}



	@Override
	public int getValue(){

		return value;
	}

	@Override
	public void setValue(int v){

		value = v;
	}



	@Override
	public Sampling getNext(){

		return next;
	}

	@Override
	public void setNext(Sampling s){

		next = s;
	}

}