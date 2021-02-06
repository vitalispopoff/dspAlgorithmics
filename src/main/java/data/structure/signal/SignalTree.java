package data.structure.signal;

public interface SignalTree {


	static SignalTree newInstance(){

		return new SignalKnot_0();
	}

	int size();


	Sampling getSampling(int i);


	void addSampling(Sampling s);

	default void addSampling(Integer v){

		addSampling(Sampling.newInstance(v));
	}

}